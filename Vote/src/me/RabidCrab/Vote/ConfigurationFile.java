package me.RabidCrab.Vote;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.config.Configuration;

import me.RabidCrab.Entities.YMLFile;

/**
 * Currently the file with all of the configuration information.
 * May split file up later if there's any issues of size or scope.
 * @author RabidCrab
 */
public class ConfigurationFile extends YMLFile
{
    public ConfigurationFile(File file) throws IOException
    {
        super(file);
    }

    @Override
    protected void populateFile(Configuration file)
    {
        // Create the default reset button PlayerVote
        PlayerVote firstVote = new PlayerVote(file, "votes.restart");
        
        List<String> successCommands = new ArrayList<String>();
        List<String> failCommands = new ArrayList<String>();
        
        successCommands.add("kickall");
        successCommands.add("save-all");
        successCommands.add("stop");
        
        firstVote.setDescription("Restart the server");
        firstVote.setLastFailedVote(0);
        firstVote.setLastSuccessfulVote(0);
        firstVote.setVoteOnCooldownText("The server has been restarted recently. Please wait a bit before trying again.");
        firstVote.setVoteStartText("Vote to restart the server has started! type /vote yes or /vote no to vote");
        firstVote.setVoteSuccessText("Vote succeeded! Restarting server in 60 seconds");
        firstVote.setVoteFailText("Vote failed! Try again in 30 minutes if it's still lagging");
        firstVote.setVoteSuccessCommands(successCommands);
        firstVote.setVoteFailCommands(failCommands);
        firstVote.setVoteSuccessCommandDelaySeconds(60);
        firstVote.setVoteFailCommandDelaySeconds(0);
        firstVote.setTimeoutSeconds(60);
        firstVote.setMinimumVotes(1);
        firstVote.setPercentToSucceed(60);
        firstVote.setCooldownMinutesToFailRevote(30);
        firstVote.setCooldownMinutesToSuccessRevote(120);
        
        firstVote.save();
        
        // application information I'll need eventually for updates and whatnot
        super.configurationFile.setProperty("application.files.config.Version", "0.2");
        super.configurationFile.setProperty("application.Version", "0.2");
        
        // Settings with no embedding
        super.configurationFile.setProperty("default.VoteStartText", "A vote has begun! Type /vote Yes or /vote No to vote.");
        super.configurationFile.setProperty("default.VoteEndSuccessText", "The majority has voted yes.");
        super.configurationFile.setProperty("default.VoteEndFailText", "The majority has voted no.");
        super.configurationFile.setProperty("default.VoteAlreadyInProgress", "A vote is already active, please wait to start another vote.");
        super.configurationFile.setProperty("default.VoteCanceled", "The vote has been canceled.");
        super.configurationFile.setProperty("default.NoVoteInProgress", "No vote is currently in progress!");
        super.configurationFile.setProperty("default.PlayerAlreadyVoted", "You have already voted!");
        super.configurationFile.setProperty("default.PlayerVoteCounted", "Vote counted.");
        super.configurationFile.setProperty("default.PlayerVoteNoPermission", "You do not have permission to vote.");
        super.configurationFile.setProperty("default.PlayerVoteStartNoPermission", "You do not have permission to start a vote.");
        super.configurationFile.setProperty("default.PlayerVoteChanged", "You changed your vote.");
        
        // Help settings
        List<String> generalCommandsHelp = new ArrayList<String>();
        
        generalCommandsHelp.add("/vote yes - Vote yes on the active vote.");
        generalCommandsHelp.add("/vote no - Vote no on the active vote.");
        generalCommandsHelp.add("/vote start [votename] - Start a new vote with the keyword. ");
        generalCommandsHelp.add("/vote start ? - Display list of votes you can start.");
        
        super.configurationFile.setProperty("help.GeneralCommands", generalCommandsHelp);
        super.configurationFile.setProperty("help.GeneralHelpNotFound", "No help list found.");
        super.configurationFile.setProperty("help.VoteStartHelpNotFound", "No votes you can start found.");
        
        super.configurationFile.save();
    }
    
    public String getVoteStartText()
    {
        return super.configurationFile.getString("default.VoteStartText");
    }
    
    public void setVoteStartText(String defaultVoteStartText)
    {
        super.configurationFile.setProperty("default.VoteStartText", defaultVoteStartText);
        this.save();
    }
    
    public String getVoteEndSuccessText()
    {
        return super.configurationFile.getString("default.VoteEndSuccessText");
    }
    
    public void setVoteEndSuccessText(String defaulVoteEndSuccessText)
    {
        super.configurationFile.setProperty("default.VoteEndSuccessText", defaulVoteEndSuccessText);
        this.save();
    }
    
    public String getVoteEndFailText()
    {
        return super.configurationFile.getString("default.VoteEndFailText");
    }
    
    public void setVoteEndFailText(String defaulVoteEndFailText)
    {
        super.configurationFile.setProperty("default.VoteEndFailText", defaulVoteEndFailText);
        this.save();
    }
    
    public String getVoteAlreadyInProgress()
    {
        return super.configurationFile.getString("default.VoteAlreadyInProgress");
    }
    
    public void setVoteAlreadyInProgress(String voteAlreadyInProgress)
    {
        super.configurationFile.setProperty("default.VoteAlreadyInProgress", voteAlreadyInProgress);
        this.save();
    }
    
    public String getVoteCanceled()
    {
        return super.configurationFile.getString("default.VoteCanceled");
    }
    
    public void setVoteCanceled(String voteCanceled)
    {
        super.configurationFile.setProperty("default.VoteCanceled", voteCanceled);
        this.save();
    }
    
    public String getNoVoteInProgress()
    {
        return super.configurationFile.getString("default.NoVoteInProgress");
    }
    
    public void setNoVoteInProgress(String noVoteInProgress)
    {
        super.configurationFile.setProperty("default.NoVoteInProgress", noVoteInProgress);
        this.save();
    }
    
    public String getPlayerVoteCounted()
    {
        return super.configurationFile.getString("default.PlayerVoteCounted");
    }
    
    public void setPlayerVoteCounted(String playerVoteCounted)
    {
        super.configurationFile.setProperty("default.PlayerVoteCounted", playerVoteCounted);
        this.save();
    }
    
    public List<String> getAllVoteTypes()
    {
        return super.configurationFile.getKeys("votes");
    }
    
    public String getPlayerAlreadyVoted()
    {
        return super.configurationFile.getString("default.PlayerAlreadyVoted");
    }
    
    public void setPlayerAlreadyVoted(String playerAlreadyVoted)
    {
        super.configurationFile.setProperty("default.PlayerAlreadyVoted", playerAlreadyVoted);
        this.save();
    }

    public String getPlayerVoteNoPermission()
    {
        return super.configurationFile.getString("default.PlayerVoteNoPermission");
    }
    
    public void setPlayerVoteNoPermission(String playerVoteNoPermission)
    {
        super.configurationFile.setProperty("default.PlayerVoteNoPermission", playerVoteNoPermission);
        this.save();
    }
    
    public String getPlayerVoteStartNoPermission()
    {
        return super.configurationFile.getString("default.PlayerVoteStartNoPermission");
    }
    
    public void setPlayerVoteStartNoPermission(String playerVoteStartNoPermission)
    {
        super.configurationFile.setProperty("default.PlayerVoteStartNoPermission", playerVoteStartNoPermission);
        this.save();
    }
    
    public String getPlayerVoteChanged()
    {
        return super.configurationFile.getString("default.PlayerVoteChanged");
    }
    
    public void setPlayerVoteChanged(String playerVoteChanged)
    {
        super.configurationFile.setProperty("default.PlayerVoteChanged", playerVoteChanged);
        this.save();
    }
    
    public String getGeneralHelpNotFound()
    {
        return super.configurationFile.getString("help.GeneralHelpNotFound");
    }
    
    public void setGeneralHelpNotFound(String generalHelpNotFound)
    {
        super.configurationFile.setProperty("help.GeneralHelpNotFound", generalHelpNotFound);
        this.save();
    }
    
    public String getVoteStartHelpNotFound()
    {
        return super.configurationFile.getString("help.VoteStartHelpNotFound");
    }
    
    public void setVoteStartHelpNotFound(String voteStartHelpNotFound)
    {
        super.configurationFile.setProperty("help.VoteStartHelpNotFound", voteStartHelpNotFound);
        this.save();
    }
    
    public List<String> getGeneralCommandsHelp()
    {
        return super.configurationFile.getStringList("help.GeneralCommands", new ArrayList<String>());
    }
    
    /**
     * @return A list of all possible votes and their descriptions
     */
    public List<SimpleEntry<String,String>> getVotesListAndDescription()
    {
        List<SimpleEntry<String,String>> returnList = new ArrayList<SimpleEntry<String,String>>();
        PlayerVote playerVote;
        
        // Loop through all the vote types and pull their descriptions
        for (String voteType : getAllVoteTypes())
        {
            playerVote = new PlayerVote(super.configurationFile, "votes." + voteType);
            
            returnList.add(new SimpleEntry<String,String>(voteType, playerVote.getDescription()));
        }
        
        return returnList;
    }
    
    /**
     * Save any changes made to the configuration file
     */
    public void save()
    {
        super.configurationFile.save();
    }
    
    /**
     * Get the specific PlayerVote
     * @param playerVote
     * @return
     */
    public PlayerVote getPlayerVote(Vote plugin, String playerVote)
    {
        return new PlayerVote(super.configurationFile, "votes." + playerVote);
    }
    
    /**
     * Saves the player vote to this specific configuration file
     * @param playerVote The PlayerVote to save
     * @return True if save successful
     */
    public boolean savePlayerVote(PlayerVote playerVote)
    {
        playerVote.setConfigurationFile(super.configurationFile);
        
        return playerVote.save();
    }
}