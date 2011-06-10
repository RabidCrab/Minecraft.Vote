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
        PlayerVote restartVote = new PlayerVote(file, "vote.votes.restart");
        PlayerVote dayVote = new PlayerVote(file, "vote.votes.day");
        PlayerVote nightVote = new PlayerVote(file, "vote.votes.night");
        //PlayerVote sunVote = new PlayerVote(file, "votes.sun");
        //PlayerVote stormVote = new PlayerVote(file, "votes.storm");
        
        // Vote to restart server
        List<String> restartSuccessCommands = new ArrayList<String>();
        List<String> restartFailCommands = new ArrayList<String>();
        
        restartSuccessCommands.add("kickall");
        restartSuccessCommands.add("save-all");
        restartSuccessCommands.add("stop");
        
        restartVote.setDescription("Restart the server");
        restartVote.setLastFailedVote(0);
        restartVote.setLastSuccessfulVote(0);
        restartVote.setVoteOnCooldownText("The server has been restarted recently. Please wait a bit before trying again.");
        restartVote.setVoteStartText("Vote to restart the server has started! type /vote yes or /vote no to vote");
        restartVote.setVoteSuccessText("Vote succeeded! Restarting server in 60 seconds");
        restartVote.setVoteFailText("Vote failed! Try again in 30 minutes if it's still lagging");
        restartVote.setVoteSuccessCommands(restartSuccessCommands);
        restartVote.setVoteFailCommands(restartFailCommands);
        restartVote.setVoteSuccessCommandDelaySeconds(60);
        restartVote.setVoteFailCommandDelaySeconds(0);
        restartVote.setTimeoutSeconds(60);
        restartVote.setMinimumVotes(1);
        restartVote.setPercentToSucceed(60);
        restartVote.setCooldownMinutesToFailRevote(30);
        restartVote.setCooldownMinutesToSuccessRevote(120);
        restartVote.save();
        
        // Vote to set day
        List<String> daySuccessCommands = new ArrayList<String>();
        List<String> dayFailCommands = new ArrayList<String>();
        
        daySuccessCommands.add("time day");
        
        dayVote.setDescription("Set time to day");
        dayVote.setLastFailedVote(0);
        dayVote.setLastSuccessfulVote(0);
        dayVote.setVoteOnCooldownText("The time has been set to day too recently!");
        dayVote.setVoteStartText("Vote to set the time to day has started! type /vote yes or /vote no to vote");
        dayVote.setVoteSuccessText("Vote succeeded! Time set to day");
        dayVote.setVoteFailText("Vote failed! Try again the next time night comes around.");
        dayVote.setVoteSuccessCommands(daySuccessCommands);
        dayVote.setVoteFailCommands(dayFailCommands);
        dayVote.setVoteSuccessCommandDelaySeconds(3);
        dayVote.setVoteFailCommandDelaySeconds(0);
        dayVote.setTimeoutSeconds(60);
        dayVote.setMinimumVotes(1);
        dayVote.setPercentToSucceed(60);
        dayVote.setCooldownMinutesToFailRevote(15);
        dayVote.setCooldownMinutesToSuccessRevote(10);
        dayVote.save();
        
        // Vote to set night
        List<String> nightSuccessCommands = new ArrayList<String>();
        List<String> nightFailCommands = new ArrayList<String>();
        
        nightSuccessCommands.add("time night");
        
        nightVote.setDescription("Set time to night");
        nightVote.setLastFailedVote(0);
        nightVote.setLastSuccessfulVote(0);
        nightVote.setVoteOnCooldownText("The time has been set to night too recently!");
        nightVote.setVoteStartText("Vote to set the time to night has started! type /vote yes or /vote no to vote");
        nightVote.setVoteSuccessText("Vote succeeded! Time set to night");
        nightVote.setVoteFailText("Vote failed! Try again the next time day comes around.");
        nightVote.setVoteSuccessCommands(nightSuccessCommands);
        nightVote.setVoteFailCommands(nightFailCommands);
        nightVote.setVoteSuccessCommandDelaySeconds(3);
        nightVote.setVoteFailCommandDelaySeconds(0);
        nightVote.setTimeoutSeconds(60);
        nightVote.setMinimumVotes(1);
        nightVote.setPercentToSucceed(60);
        nightVote.setCooldownMinutesToFailRevote(15);
        nightVote.setCooldownMinutesToSuccessRevote(10);
        nightVote.save();
        
        /* Not ready quite yet. Still need to add some parameterization
        // Vote to set weather to sun
        List<String> sunSuccessCommands = new ArrayList<String>();
        List<String> sunFailCommands = new ArrayList<String>();
        
        sunSuccessCommands.add("weather sun");
        
        sunVote.setDescription("Set weather to sunny");
        sunVote.setLastFailedVote(0);
        sunVote.setLastSuccessfulVote(0);
        sunVote.setVoteOnCooldownText("The weather has been set to sunny too recently!");
        sunVote.setVoteStartText("Vote to set the weather to sunny has started! type /vote yes or /vote no to vote");
        sunVote.setVoteSuccessText("Vote succeeded! Weather set to sunny");
        sunVote.setVoteFailText("Vote failed! Try again later.");
        sunVote.setVoteSuccessCommands(sunSuccessCommands);
        sunVote.setVoteFailCommands(sunFailCommands);
        sunVote.setVoteSuccessCommandDelaySeconds(3);
        sunVote.setVoteFailCommandDelaySeconds(0);
        sunVote.setTimeoutSeconds(60);
        sunVote.setMinimumVotes(1);
        sunVote.setPercentToSucceed(60);
        sunVote.setCooldownMinutesToFailRevote(10);
        sunVote.setCooldownMinutesToSuccessRevote(5);
        sunVote.save();
        
         // Vote to set weather to storm
        List<String> stormSuccessCommands = new ArrayList<String>();
        List<String> stormFailCommands = new ArrayList<String>();
        
        stormSuccessCommands.add("weather storm");
        
        stormVote.setDescription("Set weather to stormy");
        stormVote.setLastFailedVote(0);
        stormVote.setLastSuccessfulVote(0);
        stormVote.setVoteOnCooldownText("The weather has been set to stormy too recently!");
        stormVote.setVoteStartText("Vote to set the weather to stormy has started! type /vote yes or /vote no to vote");
        stormVote.setVoteSuccessText("Vote succeeded! Weather set to stormy");
        stormVote.setVoteFailText("Vote failed! Try again later.");
        stormVote.setVoteSuccessCommands(stormSuccessCommands);
        stormVote.setVoteFailCommands(stormFailCommands);
        stormVote.setVoteSuccessCommandDelaySeconds(3);
        stormVote.setVoteFailCommandDelaySeconds(0);
        stormVote.setTimeoutSeconds(60);
        stormVote.setMinimumVotes(1);
        stormVote.setPercentToSucceed(60);
        stormVote.setCooldownMinutesToFailRevote(10);
        stormVote.setCooldownMinutesToSuccessRevote(5);
        stormVote.save();
        */
        
        // application information I'll need eventually for updates and whatnot
        super.configurationFile.setProperty("vote.application.files.config.Version", "0.2");
        super.configurationFile.setProperty("vote.application.Version", "0.2");
        
        // Settings with no embedding
        super.configurationFile.setProperty("vote.default.VoteStartText", "A vote has begun! Type /vote Yes or /vote No to vote.");
        super.configurationFile.setProperty("vote.default.VoteEndSuccessText", "The majority has voted yes.");
        super.configurationFile.setProperty("vote.default.VoteEndFailText", "The majority has voted no.");
        super.configurationFile.setProperty("vote.default.VoteAlreadyInProgress", "A vote is already active, please wait to start another vote.");
        super.configurationFile.setProperty("vote.default.VoteCanceled", "The vote has been canceled.");
        super.configurationFile.setProperty("vote.default.NoVoteInProgress", "No vote is currently in progress!");
        super.configurationFile.setProperty("vote.default.PlayerAlreadyVoted", "You have already voted!");
        super.configurationFile.setProperty("vote.default.PlayerVoteCounted", "Vote counted.");
        super.configurationFile.setProperty("vote.default.PlayerVoteNoPermission", "You do not have permission to vote.");
        super.configurationFile.setProperty("vote.default.PlayerVoteStartNoPermission", "You do not have permission to start a vote.");
        super.configurationFile.setProperty("vote.default.PlayerVoteChanged", "You changed your vote.");
        
        // Help settings
        List<String> generalCommandsHelp = new ArrayList<String>();
        
        generalCommandsHelp.add("/vote yes - Vote yes on the active vote.");
        generalCommandsHelp.add("/vote no - Vote no on the active vote.");
        generalCommandsHelp.add("/vote [votename] - Start a new vote with the keyword. ");
        generalCommandsHelp.add("/vote list - Display list of votes you can start.");
        
        super.configurationFile.setProperty("vote.help.GeneralCommands", generalCommandsHelp);
        super.configurationFile.setProperty("vote.help.GeneralHelpNotFound", "No help list found.");
        super.configurationFile.setProperty("vote.help.VoteStartHelpNotFound", "No votes you can start found.");
        
        super.configurationFile.save();
    }
    
    public String getVoteStartText()
    {
        return super.configurationFile.getString("vote.default.VoteStartText");
    }
    
    public void setVoteStartText(String defaultVoteStartText)
    {
        super.configurationFile.setProperty("vote.default.VoteStartText", defaultVoteStartText);
        this.save();
    }
    
    public String getVoteEndSuccessText()
    {
        return super.configurationFile.getString("vote.default.VoteEndSuccessText");
    }
    
    public void setVoteEndSuccessText(String defaulVoteEndSuccessText)
    {
        super.configurationFile.setProperty("vote.default.VoteEndSuccessText", defaulVoteEndSuccessText);
        this.save();
    }
    
    public String getVoteEndFailText()
    {
        return super.configurationFile.getString("vote.default.VoteEndFailText");
    }
    
    public void setVoteEndFailText(String defaulVoteEndFailText)
    {
        super.configurationFile.setProperty("vote.default.VoteEndFailText", defaulVoteEndFailText);
        this.save();
    }
    
    public String getVoteAlreadyInProgress()
    {
        return super.configurationFile.getString("vote.default.VoteAlreadyInProgress");
    }
    
    public void setVoteAlreadyInProgress(String voteAlreadyInProgress)
    {
        super.configurationFile.setProperty("vote.default.VoteAlreadyInProgress", voteAlreadyInProgress);
        this.save();
    }
    
    public String getVoteCanceled()
    {
        return super.configurationFile.getString("vote.default.VoteCanceled");
    }
    
    public void setVoteCanceled(String voteCanceled)
    {
        super.configurationFile.setProperty("vote.default.VoteCanceled", voteCanceled);
        this.save();
    }
    
    public String getNoVoteInProgress()
    {
        return super.configurationFile.getString("vote.default.NoVoteInProgress");
    }
    
    public void setNoVoteInProgress(String noVoteInProgress)
    {
        super.configurationFile.setProperty("vote.default.NoVoteInProgress", noVoteInProgress);
        this.save();
    }
    
    public String getPlayerVoteCounted()
    {
        return super.configurationFile.getString("vote.default.PlayerVoteCounted");
    }
    
    public void setPlayerVoteCounted(String playerVoteCounted)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteCounted", playerVoteCounted);
        this.save();
    }
    
    public List<String> getAllVoteTypes()
    {
        return super.configurationFile.getKeys("vote.votes");
    }
    
    public String getPlayerAlreadyVoted()
    {
        return super.configurationFile.getString("vote.default.PlayerAlreadyVoted");
    }
    
    public void setPlayerAlreadyVoted(String playerAlreadyVoted)
    {
        super.configurationFile.setProperty("vote.default.PlayerAlreadyVoted", playerAlreadyVoted);
        this.save();
    }

    public String getPlayerVoteNoPermission()
    {
        return super.configurationFile.getString("vote.default.PlayerVoteNoPermission");
    }
    
    public void setPlayerVoteNoPermission(String playerVoteNoPermission)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteNoPermission", playerVoteNoPermission);
        this.save();
    }
    
    public String getPlayerVoteStartNoPermission()
    {
        return super.configurationFile.getString("vote.default.PlayerVoteStartNoPermission");
    }
    
    public void setPlayerVoteStartNoPermission(String playerVoteStartNoPermission)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteStartNoPermission", playerVoteStartNoPermission);
        this.save();
    }
    
    public String getPlayerVoteChanged()
    {
        return super.configurationFile.getString("vote.default.PlayerVoteChanged");
    }
    
    public void setPlayerVoteChanged(String playerVoteChanged)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteChanged", playerVoteChanged);
        this.save();
    }
    
    public String getGeneralHelpNotFound()
    {
        return super.configurationFile.getString("vote.help.GeneralHelpNotFound");
    }
    
    public void setGeneralHelpNotFound(String generalHelpNotFound)
    {
        super.configurationFile.setProperty("vote.help.GeneralHelpNotFound", generalHelpNotFound);
        this.save();
    }
    
    public String getVoteStartHelpNotFound()
    {
        return super.configurationFile.getString("vote.help.VoteStartHelpNotFound");
    }
    
    public void setVoteStartHelpNotFound(String voteStartHelpNotFound)
    {
        super.configurationFile.setProperty("vote.help.VoteStartHelpNotFound", voteStartHelpNotFound);
        this.save();
    }
    
    public List<String> getGeneralCommandsHelp()
    {
        return super.configurationFile.getStringList("vote.help.GeneralCommands", new ArrayList<String>());
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
            playerVote = new PlayerVote(super.configurationFile, "vote.votes." + voteType);
            
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
        return new PlayerVote(super.configurationFile, "vote.votes." + playerVote);
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