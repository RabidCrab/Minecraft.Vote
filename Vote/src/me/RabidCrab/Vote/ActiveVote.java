package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import me.RabidCrab.Vote.Exceptions.PlayerNotFoundException;
import me.RabidCrab.Vote.Timers.VoteFinishedTimer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The voting framework. It's designed to hold only one vote because it's substantially easier to code for
 * @author RabidCrab
 * 
 */
public class ActiveVote
{
    private static boolean isVoting = false;
    private static PlayerVote currentVote;
    private static Timer voteTimer = new Timer();
    private static List<Player> voteYes;
    private static List<Player> voteNo;
    private static List<Player> loggedInPlayers;
    private static CommandSender voteStarter;
    private static ArrayList<String> arguments;
    
    /**
     * @return List of arguments for the vote in progress or the vote recently completed. The last argument is the name of the player who started the vote
     */
    public static ArrayList<String> getArguments()
    {
        return arguments;
    }
    
    /**
     * When the arguments list is set, the calling players name needs to be added to the end before it can be valid
     */
    private static void setArguments(Plugin plugin, ArrayList<String> argumentList) throws PlayerNotFoundException
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
        
        // Now that we have the arguments partially setup, time to validate any player names by looping through the success and fail commands
        arguments = CommandParser.ParseArguments(plugin, currentVote.getVoteSuccessCommands(), arguments);
        arguments = CommandParser.ParseArguments(plugin, currentVote.getVoteFailCommands(), arguments);
    }
    
    /**
     * Begin a vote with the specified vote
     * @return True if successful
     */
    public static boolean beginVote(Vote plugin, CommandSender sender, PlayerVote vote, ArrayList<String> arguments)
    {
        // Make sure a vote isn't already going. This must be first so that setting the global arguments won't break a currently
        // running vote
        if (isVoting)
        {
            sender.sendMessage(Vote.configuration.getVoteAlreadyInProgress());
            return false;
        }
        
        // Begin the declarations. The voteStarter needs to be declared before this.setArguments(arguments), otherwise shit
        // hits the fan due to the setArguments calling voteStarter to get the player name.
        currentVote = vote;
        voteStarter = sender;
        
        // It's important for this to be here so that any errors with parameters will be thrown
        try
        {
            setArguments(plugin, arguments);
        } 
        catch (PlayerNotFoundException e1)
        {
            sender.sendMessage(Vote.configuration.getPlayerNotFound());
            return false;
        }
        
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
                // We still have to go by the player string name because that's what people use to target players. This functionality will remain, although
                // it is deprecated
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
        
        // Check if the player can even be banned. If they can't, don't even try
        if (vote.getVoteShortName().equalsIgnoreCase("ban"))
        {
            try
            {
                // We still have to go by the player string name because that's what people use to target players. This functionality will remain, although
                // it is deprecated
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
        
        isVoting = true;
        plugin.getServer().broadcastMessage(currentVote.getVoteStartText());
        
        // Create the list of voters and add the beginning voter to the list. Also prevents carried over data
        voteYes = new ArrayList<Player>(); 
        voteNo = new ArrayList<Player>(); 
        // Vote results shouldn't be skewed by a random player joining through the middle of the vote, but that doesn't mean the
        // player shouldn't be allowed to vote. This list will be added to if a player logs in and votes during a vote
        List<Player> allLoggedInPlayers = new ArrayList<Player>(plugin.getServer().getOnlinePlayers());
        
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
            playerVoteYes(plugin, (Player)sender);
        
        plugin.getServer().getScheduler().runTaskLater(plugin, new VoteFinishedTimer(plugin), vote.getTimeoutSeconds() * 20);
        // Begin the timer. Why can't I just do a callback method?
        //voteTimer.schedule(new VoteFinishedTimer(plugin), vote.getTimeoutSeconds() * 20);
        
        return true;
    }
    
    /**
     * @return True if the player can vote yes
     */
    private static boolean canPlayerVoteYes(CommandSender sender)
    {
        if (currentVote == null)
            return false;

        if (Vote.permissions.has(sender, "vote.voteyes." + currentVote.getVoteShortName()))
            return true;
        
        return false;
    }
    
    /**
     * @return True if the player can vote no
     */
    private static boolean canPlayerVoteNo(Player player)
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
    public static boolean cancelVote(CommandSender sender)
    {
        if (!Vote.permissions.has(sender, "vote.veto"))
        {
            sender.sendMessage(Vote.configuration.getPlayerVetoNoPermission());
            return false;
        }
        
        if (isVoting)
        {
            isVoting = false;
            
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
    public static boolean isVoting()
    {
        return isVoting;
    }
    
    /**
     * 
     * @return The name of the vote with no prefixes
     */
    public static String getVoteName()
    {
        return currentVote.getVoteShortName();
    }
    
    /**
     * On player yes-vote, add them to the list of yes-votes if they aren't there already
     * @return Yes if the vote casted successfully
     */
    public static boolean playerVoteYes(Plugin plugin, Player player)
    {
        if (isVoting)
        {
            if (!canPlayerVoteYes(player))
            {
                player.sendMessage(Vote.configuration.getPlayerVoteNoPermission());
                return false;
            }
            
            // Check both the yes votes and no votes to see if a player has already voted with that
            // IP address, if the server wants to IP check the player
            if (Vote.configuration.getCheckPlayerIPAddress())
            {
                for (Player yesVoter : voteYes)
                {
                    if (player.getAddress().toString().split(":")[0].compareToIgnoreCase(yesVoter.getAddress().toString().split(":")[0]) == 0
                        && player.getName().compareToIgnoreCase(yesVoter.getName()) != 0)
                    {
                        player.sendMessage(Vote.configuration.getPlayerIPAlreadyVoted());
                        return false;
                    }
                }
                for (Player noVoter : voteNo)
                {
                    if (player.getAddress().toString().split(":")[0].compareToIgnoreCase(noVoter.getAddress().toString().split(":")[0]) == 0
                            && player.getName().compareToIgnoreCase(noVoter.getName()) != 0)
                    {
                        player.sendMessage(Vote.configuration.getPlayerIPAlreadyVoted());
                        return false;
                    }
                }
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
                VoteStatus currentVoteTally = voteResults(false);
                
                if (currentVoteTally == VoteStatus.Success || currentVoteTally == VoteStatus.Fail)
                    voteTimeOver(plugin);
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
     * @return The current vote status
     */
    private static VoteStatus voteResults(boolean isVoteFinished)
    {
        if (isVoting)
        {
            // This is all to make sure no integer cutting goes on by converting everything to double
            double voteYesSize = voteYes.size();
            double voteNoSize = voteNo.size();
            double loggedInPlayersSize = loggedInPlayers.size();
            double unVotedPlayersSize = getUnvotedPlayers().size();
            
            // These are used for actual calculations
            double totalVotes = voteYesSize + voteNoSize;
            double currentYesPercentage = (voteYesSize / loggedInPlayersSize) * 100;
            double potentialYesVotes = voteYesSize + unVotedPlayersSize;
            
            /* Debugging
            voteStarter.sendMessage("Yes - " + voteYesSize);
            voteStarter.sendMessage("No - " + voteNoSize);
            voteStarter.sendMessage("Logged In - " + loggedInPlayersSize);
            voteStarter.sendMessage("Unvoted - " + unVotedPlayersSize);
            
            voteStarter.sendMessage("Total - " + totalVotes);
            voteStarter.sendMessage("YesPercent - " + currentYesPercentage);
            voteStarter.sendMessage("Potential Yes - " + potentialYesVotes);
            
            voteStarter.sendMessage("Minimum - " + currentVote.getMinimumVotes());
            */
            
            // First make sure they have the minimum votes to succeed in a vote, and that they have enough
            // unvoted people to vote and change the state from fail to succeed
            if (totalVotes < currentVote.getMinimumVotes())
                if ((totalVotes + unVotedPlayersSize) < currentVote.getMinimumVotes())
                {
                    //voteStarter.sendMessage("Fail minimum votes");
                    return VoteStatus.Fail;
                }
                else 
                {
                    //voteStarter.sendMessage("Inconclusive minimum votes");
                    return VoteStatus.Inconclusive;
                }
            
            // Check the ratio right now and see if it wins
            if (currentYesPercentage >= currentVote.getPercentToSucceed())
            {
                //voteStarter.sendMessage("Succeed vote percentage");
                return VoteStatus.Success;
            }
            
            // If they have IgnoreUnvotedPlayers and it's the ending time for the vote, we should only do the percent between yes and no votes.
            // Make sure to include a check for 0's so it doesn't screw up
            if (currentVote.getIgnoreUnvotedPlayers() && isVoteFinished)
            {
                // Make sure we have yes voters and more yes's than no's
                if (voteYesSize < 1 || voteYesSize <= voteNoSize)
                    return VoteStatus.Fail;
                
                // The check above guarantees that there's yes votes. So if there's 0 no votes, it's an automatic win
                if (voteNoSize < 1)
                    return VoteStatus.Success;
                
                // If there's no votes and yes votes, pull a tally and find out if there should be a win
                if ((voteNoSize / voteYesSize) * 100 < currentVote.getPercentToSucceed())
                    return VoteStatus.Fail;
                else 
                    return VoteStatus.Success;
            }
            
            // Now we do a final check and make sure the minimum standards for a success can be met. If it can't,
            // return a fail. The first check is adding up all the voted and unvoted players to make sure we can reach
            // the minimum votes.
            // The 2nd check is to make sure that the potential yes votes, which includes current yes votes, can override
            // the potential to succeed. We also make sure the unvoted players size is > 0 to make sure it won't fail
            // incorrectly
            if ((totalVotes + unVotedPlayersSize) < currentVote.getMinimumVotes()
                    || ((potentialYesVotes / unVotedPlayersSize) * 100 < currentVote.getPercentToSucceed()
                        && unVotedPlayersSize > 0))
            {
                //voteStarter.sendMessage("Fail vote minimum potential success");
                return VoteStatus.Fail;
            }
        }
        
        // Nothing was a pure win or fail, so it's fair game.
        // Either that or there's no vote going on
        //voteStarter.sendMessage("Blanket inconclusive");
        return VoteStatus.Inconclusive;
    }

    /**
     * @return A list of all players who have no voted yet
     */
    private static List<Player> getUnvotedPlayers()
    {
        List<Player> unvoted = new ArrayList<Player>();
        
        for (Player player : loggedInPlayers)
            if (!voteYes.contains(player) && !voteNo.contains(player))
                unvoted.add(player);
        
        return unvoted;
    }

    /**
     * On player no-vote, do something. Currently doesn't matter if a user votes no
     * @return Yes if the vote casted successfully
     */
    public static boolean playerVoteNo(Plugin plugin, Player player)
    {
        if (isVoting)
        {
            if (!canPlayerVoteNo(player))
            {
                Vote.configuration.getPlayerVoteNoPermission();
                return false;
            }
            
            // Check both the yes votes and no votes to see if a player has already voted with that
            // IP address, if the server wants to IP check the player
            if (Vote.configuration.getCheckPlayerIPAddress())
            {
                for (Player yesVoter : voteYes)
                {
                    if (player.getAddress().toString().split(":")[0].compareToIgnoreCase(yesVoter.getAddress().toString().split(":")[0]) == 0
                        && player.getName().compareToIgnoreCase(yesVoter.getName()) != 0)
                    {
                        player.sendMessage(Vote.configuration.getPlayerIPAlreadyVoted());
                        return false;
                    }
                }
                for (Player noVoter : voteNo)
                {
                    if (player.getAddress().toString().split(":")[0].compareToIgnoreCase(noVoter.getAddress().toString().split(":")[0]) == 0
                            && player.getName().compareToIgnoreCase(noVoter.getName()) != 0)
                    {
                        player.sendMessage(Vote.configuration.getPlayerIPAlreadyVoted());
                        return false;
                    }
                }
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
                VoteStatus currentVoteTally = voteResults(false);
                
                if (currentVoteTally == VoteStatus.Success || currentVoteTally == VoteStatus.Fail)
                    voteTimeOver(plugin);
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
     * Called when the vote is over with and the tally needs to be taken. This should only be run by VoteFinishedTimer
     */
    public static void voteTimeOver(Plugin plugin)
    {
        if (isVoting)
        {
            // Get the results of the vote
            VoteStatus finalVoteTally = voteResults(true);
            
            isVoting = false;
            
            // If it's a success, then yay. Anything else is an immediate fail
            if (finalVoteTally == VoteStatus.Success)
                voteSuccess(plugin);
            else 
                voteFail(plugin);
        }
    }
    
    /**
     * called when vote succeeds notify of the success
     */
    @SuppressWarnings("unchecked") // This is for the arguments.clone(). It'll always be an ArrayList<String> without fail
    private static void voteSuccess(Plugin plugin)
    {
        if (currentVote.getVoteSuccessText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndSuccessText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteSuccessText());
        
        // Add up the time for the successful vote cooldown
        currentVote.setLastSuccessfulVote(new Date().getTime());
        currentVote.save();
        
        // Now add the commandset to the scheduled set and start the command timer if it hasn't been started already
        CommandScheduler.scheduleCommandSet(new CommandSet(plugin
                                                            , voteStarter
                                                            , currentVote.getVoteSuccessCommands()
                                                            , (ArrayList<String>)arguments.clone()
                                                            , currentVote.isConsoleCommand())
                                            , currentVote.getVoteSuccessCommandDelaySeconds());
    }
    
    /**
     * On vote failure notify of the fail
     */
    @SuppressWarnings("unchecked") // This is for the arguments.clone(). It'll always be an ArrayList<String> without fail
    private static void voteFail(Plugin plugin)
    {
        // Send the fail message
        if (currentVote.getVoteFailText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndFailText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
        
        // Add up the time for the failed vote cooldown
        currentVote.setLastFailedVote(new Date().getTime());
        currentVote.save();
        
        // Now add the commandset to the scheduled set and start the command timer if it hasn't been started already
        CommandScheduler.scheduleCommandSet(new CommandSet(plugin
                                                            , voteStarter
                                                            , currentVote.getVoteFailCommands()
                                                            , (ArrayList<String>)arguments.clone()
                                                            , currentVote.isConsoleCommand())
                                            , currentVote.getVoteFailCommandDelaySeconds());
    }
}

