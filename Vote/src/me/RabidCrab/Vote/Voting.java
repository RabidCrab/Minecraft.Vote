package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import me.RabidCrab.Vote.Timers.VoteFinishedTimer;

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
    private Player voteStarter;
    
    public Voting(Vote vote)
    {
        voteTimer = new Timer();
        plugin = vote;
    }
    
    /**
     * Begin a vote with the specified vote
     * @return True if successful
     */
    public boolean beginVote(Player player, PlayerVote vote)
    {
        // Verify the user has the rights to start this vote
        if (!Vote.permissions.has(player, "vote.startvote." + vote.getVoteShortName()))
        {
            player.sendMessage(Vote.configuration.getPlayerVoteStartNoPermission());
            return false;
        }
        
        // Make sure a vote isn't already going
        if (IsVoting)
        {
            player.sendMessage(Vote.configuration.getVoteAlreadyInProgress());
            return false;
        }
        
        // Begin the declarations
        currentVote = vote;
        voteStarter = player;
        
        // Check and see if the cooldowns are ready to go
        Date now = new Date();
        Date lastFailedDate = new Date(currentVote.getLastFailedVote() + (currentVote.getCooldownMinutesToFailRevote() * 60000));
        Date lastSuccessfulDate = new Date(currentVote.getLastSuccessfulVote() + (currentVote.getCooldownMinutesToSuccessRevote() * 60000));
        
        // If they're still on cooldown, don't let the player start the vote
        if (now.compareTo(lastFailedDate) < 0 || now.compareTo(lastSuccessfulDate) < 0)
        {
            player.sendMessage(currentVote.getVoteOnCooldownText());
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
            if (canPlayerVoteYes(currentPlayer) || canPlayerVoteNo(currentPlayer))
                loggedInPlayers.add(currentPlayer);
        
        playerVoteYes(player);

        // Begin the timer. Why can't I just do a callback method?
        voteTimer.schedule(new VoteFinishedTimer(this), vote.getTimeoutSeconds() * 1000);
        
        return true;
    }
    
    /**
     * A check to see if the player can vote yes
     * @return True if the player can vote yes
     */
    private boolean canPlayerVoteYes(Player player)
    {
        if (currentVote == null)
            return false;

        if (Vote.permissions.has(player, "vote.voteyes." + currentVote.getVoteShortName()))
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
    public boolean cancelVote(Player player, PlayerVote vote)
    {
        if (IsVoting)
        {
            IsVoting = false;
            player.sendMessage(Vote.configuration.getVoteCanceled());
            return true;
        }
        else
        {
            player.sendMessage(Vote.configuration.getNoVoteInProgress());
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
            if (voteYes.size() < currentVote.getMinimumVotes() || (potentialYesVotes / loggedInPlayers.size()) * 100 < currentVote.getPercentToSucceed())
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
            if (!voteYes.contains(player))
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
            if (voteYes.size() < currentVote.getMinimumVotes())
            {
                plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
                voteFail();
                return;
            }

            if (voteYes.size() > 0)
            {
                // Next check the ratio
                if ((voteYes.size() / plugin.getServer().getOnlinePlayers().length) * 100 < currentVote.getPercentToSucceed())
                {
                    plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
                    voteFail();
                    return;
                } 
            }
            else
                voteFail();
            
            // Finally it's time to execute the success commands
            voteSuccess();
        }
    }
    
    /**
     * called when vote succeeds
     */
    private void voteSuccess()
    {
        if (currentVote.getVoteSuccessText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndSuccessText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteSuccessText());
        
        try
        {
            // Add up the time for the successful vote cooldown
            currentVote.setLastSuccessfulVote(new Date().getTime());
            currentVote.save();
            
            Vote.getPlayerCommandExecutor().setCaller(voteStarter);
            ConsoleCommandSender commandSender = new ConsoleCommandSender(plugin.getServer());
            
            Thread.sleep(currentVote.getVoteSuccessCommandDelaySeconds() * 1000);
            
            if (currentVote.getVoteSuccessCommands().size() > 0)
            {
                for (String string : currentVote.getVoteSuccessCommands())
                {
                    if (isConsoleCommand(string))
                        plugin.getServer().dispatchCommand(commandSender, string);
                    else
                        plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), string);
                    
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e)
        {
            plugin.log.info(e.getMessage());
        }
    }
    
    /**
     * on vote failure
     */
    private void voteFail()
    {
        if (currentVote.getVoteFailText() == "")
            plugin.getServer().broadcastMessage(Vote.configuration.getVoteEndFailText());
        else
            plugin.getServer().broadcastMessage(currentVote.getVoteFailText());
        
        try
        {
            // Add up the time for the failed vote cooldown
            currentVote.setLastFailedVote(new Date().getTime());
            currentVote.save();
            
            Vote.getPlayerCommandExecutor().setCaller(voteStarter);
            ConsoleCommandSender commandSender = new ConsoleCommandSender(plugin.getServer());
            
            Thread.sleep(currentVote.getVoteFailCommandDelaySeconds() * 1000);
                    
            if (currentVote.getVoteFailCommands().size() > 0)
            {
                for (String string : currentVote.getVoteFailCommands())
                {
                    if (isConsoleCommand(string))
                        plugin.getServer().dispatchCommand(commandSender, string);
                    else
                        plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), string);
                    
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e)
        {
            plugin.log.info(e.getMessage());
        }
    }
    
    /**
     * If something is a console command, it gets executed differently from a player command
     */
    private boolean isConsoleCommand(String command)
    {
        if (command.equalsIgnoreCase("kickall") 
                || command.equalsIgnoreCase("stop") 
                || command.equalsIgnoreCase("save-all"))
            return true;
        
        return false;
    }
}

