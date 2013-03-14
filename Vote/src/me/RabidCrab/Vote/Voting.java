package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import me.RabidCrab.Vote.Timers.ExecuteCommandsTimer;
import me.RabidCrab.Vote.Timers.VoteFinishedTimer;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * The voting framework. Currently it just supports one vote at a time, but I
 * may extend the functionality at a later date if people want it badly enough
 * @author RabidCrab
 */
public class Voting
{
    private boolean IsVoting = false;
    private PlayerVote currentVote;
    private Timer voteTimer;
    private List<Player> voteYes;
    private List<Player> voteNo;
    private Vote plugin;
    private List<Player> loggedInPlayers;
    private CommandSender voteStarter;
    private ArrayList<String> arguments;
    private List<Command> unexecutedCommands = new ArrayList<Command>();
    private Timer commandsTimer = new Timer();
    
    public Voting(Vote vote)
    {
        voteTimer = new Timer();
        plugin = vote;
    }
    
    public ArrayList<String> getArguments()
    {
        return arguments;
    }
    
    private void setArguments(ArrayList<String> argumentList)
    {
        // We need to add the player name to the end of the arguments so it can be used for
        // various shenanigans. If the last item in the list isn't the player name, make it so
        if (argumentList != null)
            if (argumentList.size() > 0) 
            {
                if (argumentList.get(argumentList.size()-1).compareTo(voteStarter.getName()) != 0)
                    argumentList.add(voteStarter.getName());
            }
            else 
                argumentList.add(voteStarter.getName());
        
        arguments = argumentList;
    }
    
    /**
     * Begin a vote with the specified vote
     * @return True if successful
     */
    public boolean beginVote(CommandSender sender, PlayerVote vote, ArrayList<String> arguments)
    {
        // Make sure a vote isn't already going. This must be first so that setting the global arguments won't break a currently
        // running vote
        if (IsVoting)
        {
            sender.sendMessage(Vote.configuration.getVoteAlreadyInProgress());
            return false;
        }
        
        // Begin the declarations. The voteStarter needs to be declared before this.setArguments(arguments), otherwise shit
        // hits the fan due to the setArguments calling voteStarter to get the player name.
        currentVote = vote;
        voteStarter = sender;
        
        // It's important for this to be here so that any errors with parameters will be thrown
        this.setArguments(arguments);
        
        // If they didn't specify the right number of arguments, complain
        // I'm subtracting 1 from the argument size because the caller of the vote is passed when the beginVote is called
        if (arguments.size() - 1 != vote.getArgumentCount())
        {
            sender.sendMessage(vote.getInsufficientArgumentsError());
            return false;
        }
        
        // Verify the user has the rights to start this vote
        if (!Vote.permissions.has(sender, "vote.startvote." + vote.getVoteShortName()))
        {
            sender.sendMessage(Vote.configuration.getPlayerVoteStartNoPermission());
            return false;
        }
        
        // Check if the player can even be kicked. If they can't, don't even try
        if (vote.getVoteShortName().equalsIgnoreCase("kick"))
        {
            try
            {
                Player target = plugin.getServer().getPlayer(arguments.get(0));
                
                if (Vote.permissions.has(target, "vote.unkickable"))
                {
                    sender.sendMessage(Vote.configuration.getPlayerUnkickable());
                    return false;
                }
            }
            catch (Exception e)
            {
                sender.sendMessage(Vote.configuration.getPlayerNotFound());
                return false;
            }
        }
        
        // Check if the player can even be kicked. If they can't, don't even try
        if (vote.getVoteShortName().equalsIgnoreCase("ban"))
        {
            try
            {
                Player target = plugin.getServer().getPlayer(arguments.get(0).toString());
                
                if (Vote.permissions.has(target, "vote.unbannable"))
                {
                    sender.sendMessage(Vote.configuration.getPlayerUnbannable());
                    return false;
                }
            }
            catch (Exception e)
            {
                sender.sendMessage(Vote.configuration.getPlayerNotFound());
                return false;
            }
        }
        
        // Check and see if the cooldowns are ready to go
        Date now = new Date();
        Date lastFailedDate = new Date(currentVote.getLastFailedVote() + (currentVote.getCooldownMinutesToFailRevote() * 60000));
        Date lastSuccessfulDate = new Date(currentVote.getLastSuccessfulVote() + (currentVote.getCooldownMinutesToSuccessRevote() * 60000));
        
        // If they're still on cooldown, don't let the player start the vote
        if (now.compareTo(lastFailedDate) < 0 || now.compareTo(lastSuccessfulDate) < 0)
        {
            sender.sendMessage(currentVote.getVoteOnCooldownText());
            return false;
        }
        
        IsVoting = true;
        plugin.getServer().broadcastMessage(currentVote.getVoteStartText());
        
        // Create the list of voters and add the beginning voter to the list. Also prevents carried over data
        voteYes = new ArrayList<Player>(); 
        voteNo = new ArrayList<Player>(); 
        // Vote results shouldn't be skewed by a random player joining through the middle of the vote, but that doesn't mean the
        // player shouldn't be allowed to vote. This list will be added to if a player logs in and votes during a vote
        List<Player> allLoggedInPlayers = new ArrayList<Player>(Arrays.asList(plugin.getServer().getOnlinePlayers()));
        
        loggedInPlayers = new ArrayList<Player>();
        
        // Loop through the logged in players and add the ones who have voting rights
        for (Player currentPlayer : allLoggedInPlayers)
        {
            //sender.sendMessage("vote.voteno." + currentVote.getVoteShortName());
            
            if (canPlayerVoteYes(currentPlayer) || canPlayerVoteNo(currentPlayer))
            {
                //sender.sendMessage("Passed");
                loggedInPlayers.add(currentPlayer);
            }
            else 
            {
                //sender.sendMessage("Failed");
            }
        }
        
        // If it's the console, don't let them vote
        if (sender instanceof Player)
            playerVoteYes((Player)sender);

        // Begin the timer. Why can't I just do a callback method?
        voteTimer.schedule(new VoteFinishedTimer(this), vote.getTimeoutSeconds() * 1000);
        
        return true;
    }
    
    /**
     * A check to see if the player can vote yes
     * @return True if the player can vote yes
     */
    private boolean canPlayerVoteYes(CommandSender sender)
    {
        if (currentVote == null)
            return false;

        if (Vote.permissions.has(sender, "vote.voteyes." + currentVote.getVoteShortName()))
            return true;
        
        return false;
    }
    
    private boolean canPlayerVoteNo(Player player)
    {
        if (currentVote == null)
            return false;

        if (Vote.permissions.has(player, "vote.voteno." + currentVote.getVoteShortName()))
            return true;
        
        return false;
    }
    
    /**
     * Cancel the vote
     */
    public boolean cancelVote(CommandSender sender)
    {
        if (!Vote.permissions.has(sender, "vote.veto"))
        {
            sender.sendMessage(Vote.configuration.getPlayerVetoNoPermission());
            return false;
        }
        
        if (IsVoting)
        {
            IsVoting = false;
            
            if (voteTimer != null)
            {
                voteTimer.cancel();
                voteTimer = new Timer();
            }
            
            sender.sendMessage(Vote.configuration.getVoteCanceled());
            return true;
        }
        else
        {
            sender.sendMessage(Vote.configuration.getNoVoteInProgress());
        }
        
        // On second hand, no need to say it failed if there's none running
        return true;
    }

    /**
     * 
     * @return True if a vote is active
     */
    public boolean isVoting()
    {
        return this.IsVoting;
    }
    
    /**
     * 
     * @return The name of the vote with no prefixes
     */
    public String getVoteName()
    {
        return currentVote.getVoteShortName();
    }
    
    /**
     * On player yes-vote, add them to the list of yes-votes if they aren't there already
     * @return Yes if the vote casted successfully
     */
    public boolean playerVoteYes(Player player)
    {
        if (IsVoting)
        {
            if (!canPlayerVoteYes(player))
            {
                Vote.configuration.getPlayerVoteNoPermission();
                return false;
            }
            
            if (!voteYes.contains(player))
            {
                voteYes.add(player);
                
                // Check and see if they logged in during the vote. If not, add them since they voted
                if (!loggedInPlayers.contains(player))
                    loggedInPlayers.add(player);
                
                // Check and remove the player if they have a record in voteNo. This consequently changes the message sent
                if (voteNo.contains(player))
                {
                    voteNo.remove(player);
                    player.sendMessage(Vote.configuration.getPlayerVoteChanged());
                }
                else
                    player.sendMessage(Vote.configuration.getPlayerVoteCounted());
                
                // Check and see if the vote can be finished without counting more votes
                int currentVoteTally = voteResults();
                
                if (currentVoteTally == 1 || currentVoteTally == -1)
                    voteTimeOver();
            }
            else
            {
                player.sendMessage(Vote.configuration.getPlayerAlreadyVoted());
                return false;
            }
        }
        else
        {
            player.sendMessage(Vote.configuration.getNoVoteInProgress());
            return false;
        }
            
            
        return true;
    }
    
    /**
     * Gets the tally of the vote results
     * @return 1 means all of the conditions have been met for a successful vote. 0 means not all of the criteria
     * have been met or no vote is going on. -1 means that there's no chance of the results being successful
     * I bet you're bitching at me for not making an enum, but you know what? You're right.
     */
    private int voteResults()
    {
        if (IsVoting)
        {
            // Check the ratio right now and see if it wins. Also check and make sure the minimum votes have been met
            if ((voteYes.size() / loggedInPlayers.size()) * 100 > currentVote.getPercentToSucceed() && voteYes.size() >= currentVote.getMinimumVotes())
                return 1;
            
            // We need to determine if it's a complete fail of -1.
            // First, I get the size of the voteYes and add all of the players who have not voted yet as the best case scenario.
            // This integer is pretty much telling Moore to take his law and shove it up his ass.
            int potentialYesVotes = voteYes.size() + unvotedPlayers().size();
            
            // If the best-case minimum size cannot be met, or the best-case ratio isn't high enough, fail it
            if (potentialYesVotes < currentVote.getMinimumVotes() || (potentialYesVotes / loggedInPlayers.size()) * 100 < currentVote.getPercentToSucceed())
                return -1;
        }
        
        // Nothing was a pure win or fail, so it's fair game.
        // Either that or there's no vote going on
        return 0;
    }

    /**
     * @return A list of all players who have no voted yet
     */
    private List<Player> unvotedPlayers()
    {
        List<Player> unvoted = new ArrayList<Player>();
        
        for (Player player : loggedInPlayers)
        {
            if (!voteYes.contains(player) && !voteNo.contains(player))
                unvoted.add(player);
        }
        
        return unvoted;
    }

    /**
     * On player no-vote, do something. Currently doesn't matter if a user votes no
     * @return Yes if the vote casted successfully
     */
    public boolean playerVoteNo(Player player)
    {
        if (IsVoting)
        {
            if (!canPlayerVoteNo(player))
            {
                Vote.configuration.getPlayerVoteNoPermission();
                return false;
            }
            
            if (!voteNo.contains(player))
            {
                voteNo.add(player);
                
                // Check and see if they logged in during the vote. If not, add them since they voted
                if (!loggedInPlayers.contains(player))
                    loggedInPlayers.add(player);
                
                // Check and remove the player if they have a record in voteNo. This consequently changes the message sent
                if (voteYes.contains(player))
                {
                    voteYes.remove(player);
                    player.sendMessage(Vote.configuration.getPlayerVoteChanged());
                }
                else
                    player.sendMessage(Vote.configuration.getPlayerVoteCounted());
                
                // Check and see if the vote can be finished without counting more votes
                int currentVoteTally = voteResults();
                
                if (currentVoteTally == 1 || currentVoteTally == -1)
                    voteTimeOver();
            }
            else
            {
                player.sendMessage(Vote.configuration.getPlayerAlreadyVoted());
                return false;
            }
        }
        else
        {
            player.sendMessage(Vote.configuration.getNoVoteInProgress());
            return false;
        }
            
            
        return true;
    }
    
    /**
     * Called when the vote is over with and the tally needs to be taken
     */
    public void voteTimeOver()
    {
        if (IsVoting)
        {
            IsVoting = false;
            
            // First check to see if the minimum player count was met
            if ((voteYes.size() + voteNo.size()) < currentVote.getMinimumVotes())
            {
                plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
                voteFail();
                return;
            }

            // Next we check and make sure someone voted yes. This should be impossible, but I've seen weirder shenanigans happen
            if (voteYes.size() > 0)
            {
                // Now we get the full size of the player count.
                int maxPlayerCount = loggedInPlayers.size();
                
                // If the unvoted players are supposed to be ignored, just add up those who voted yes and no to figure out what to do
                if (currentVote.getIgnoreUnvotedPlayers())
                    maxPlayerCount = voteYes.size() + voteNo.size();
                
                // Next check the ratio
                if (((voteYes.size() / maxPlayerCount) * 100) < currentVote.getPercentToSucceed())
                {
                    plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
                    voteFail();
                    return;
                } 
            }
            else
            {
                voteFail();
                return;
            }
            
            // Finally it's time to execute the success commands
            voteSuccess();
        }
    }
    
    /**
     * called when vote succeeds
     */
    @SuppressWarnings("unchecked")
    private void voteSuccess()
    {
        if (currentVote.getVoteSuccessText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndSuccessText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteSuccessText());
        
        // Add up the time for the successful vote cooldown
        currentVote.setLastSuccessfulVote(new Date().getTime());
        currentVote.save();
        
        // Now create a new Command to put in the list of commands to run, and start the command timer if it hasn't been started already
        unexecutedCommands.add(new Command(currentVote.getVoteSuccessCommands(), voteStarter, (ArrayList<String>)arguments.clone()));
        
        commandsTimer.schedule(new ExecuteCommandsTimer(this), currentVote.getVoteSuccessCommandDelaySeconds() * 1000);
    }
    
    /**
     * on vote failure
     */
    @SuppressWarnings("unchecked")
    private void voteFail()
    {
        if (currentVote.getVoteFailText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndFailText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
        
        // Add up the time for the failed vote cooldown
        currentVote.setLastFailedVote(new Date().getTime());
        currentVote.save();
        
        if (voteStarter instanceof Player)
            Vote.getPlayerCommandExecutor().setCaller((Player)voteStarter);
          
        // Now create a new Command to put in the list of commands to run, and start the command timer if it hasn't been started already
        unexecutedCommands.add(new Command(currentVote.getVoteFailCommands(), voteStarter, (ArrayList<String>)arguments.clone()));
        
        commandsTimer.schedule(new ExecuteCommandsTimer(this), currentVote.getVoteFailCommandDelaySeconds() * 1000);
    }
    
    /**
     * If something is a console command, it gets executed differently from a player command
     */
    private boolean isConsoleCommand(String command)
    {
        if (command.equalsIgnoreCase("kickall") 
                || command.equalsIgnoreCase("stop") 
                || command.equalsIgnoreCase("save-all")
                || currentVote.isConsoleCommand())
            return true;
        
        return false;
    }
    
    /**
     * This executes any commands that a vote has requested to be run. It's designed like this because for player
     * consequences, they can just log out to avoid the ban or kick hammer. This will allow me to re-run commands if the player isn't there
     */
    public void executeCommands()
    {
        ConsoleCommandSender commandSender = plugin.getServer().getConsoleSender();
        List<Command> commandsToRemove = new ArrayList<Command>();
        
        // Go through the list of commands in each of the list of commands
        for (Command command : unexecutedCommands)
        {
            // So here's the bananas. We have 2 situations. 1 is when it's a player, and the other when it's the console.
            // When it's the console who starts the vote, the commands are always sent by the console
            if (command.getSender() instanceof Player)
                Vote.getPlayerCommandExecutor().setCaller((Player)command.getSender());
            
            // We need to check and see if there's any VERIFYPLAYERONLINE. If there is, we need to verify the player(s) are online before
            // we continue
            boolean playersOnlineCheckFailed = false;
            
            // Loop over all the commands to look for a VERIFYPLAYERONLINE
            for (String string : command.getCommands())
            {
                if (string.contains("VERIFYPLAYERONLINE"))
                {
                    boolean playerFound = false;
                    // Get the name from the verify player online command line
                    String playerTextName = string.replaceAll("VERIFYPLAYERONLINE", "").trim();
                    
                    // Loop through all of the arguments and add them to the command if it exists
                    for (int i = 0; i < arguments.size(); i++)
                        playerTextName = playerTextName.replaceAll("\\[\\%" + i + "\\]", command.getArguments().get(i));
                    
                    // Loop over all the online players to see if the target is online
                    for (Player playerName : plugin.getServer().getOnlinePlayers())
                    {
                        // If they're online, set the verified to true so that the commands can run
                        if (playerName.getName().equalsIgnoreCase(playerTextName))
                        {
                            playerFound = true;
                            break;
                        }
                    }
                    
                    // If we couldn't find the player, it failed
                    if (!playerFound)
                        playersOnlineCheckFailed = true;
                }
            }
            
            // It failed the check, so it needs to be re-queued for another run. It's easy enough to do because we only do something if the
            // player verification passed
            if (playersOnlineCheckFailed)
                continue;
            
            // Now we execute all the commands, except of course the verification of the online players
            for (String string : command.getCommands())
            {
                // Skip over it if it's a player verification
                if (string.contains("VERIFYPLAYERONLINE"))
                    continue;
                
                String commandToExecute = string;
                
                // Loop through all of the arguments and add them to the command if it exists
                for (int i = 0; i < arguments.size(); i++)
                    commandToExecute = commandToExecute.replaceAll("\\[\\%" + i + "\\]", command.getArguments().get(i));
                
                // If the console started the vote, there's no way we can pass the console as the sender
                if (isConsoleCommand(commandToExecute) || !(voteStarter instanceof Player))
                    plugin.getServer().dispatchCommand(commandSender, commandToExecute);
                else
                    plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), commandToExecute);
            }
            
            // Once everything is done running, then I'll wipe the commands out
            commandsToRemove.add(command);
        }
        
        // Clear out the ones that ran successfully
        for (Command command : commandsToRemove)
            unexecutedCommands.remove(command);
        
        // Re-run it if it isn't empty
        if (!unexecutedCommands.isEmpty())
            commandsTimer.schedule(new ExecuteCommandsTimer(this), 3000);
    }
}

