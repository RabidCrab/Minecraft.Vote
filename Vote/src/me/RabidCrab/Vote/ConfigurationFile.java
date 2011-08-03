package me.RabidCrab.Vote;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.util.config.Configuration;

import me.RabidCrab.Entities.YMLFile;

/**
 * Currently the file with all of the configuration information.
 * May split file up later if there's any issues of size or scope.
 * @author RabidCrab
 */
public class ConfigurationFile extends YMLFile
{
    Callable<String[]> arguments;
    
    public ConfigurationFile(File file, Callable<String[]> callable) throws IOException
    {
        super(file);
        
        this.arguments = callable;
    }

    @Override
    protected void populateFile(Configuration file)
    {
        // Create the default reset button PlayerVote
        PlayerVote restartVote = new PlayerVote(file, "vote.votes.restart", arguments);
        PlayerVote dayVote = new PlayerVote(file, "vote.votes.day", arguments);
        PlayerVote nightVote = new PlayerVote(file, "vote.votes.night", arguments);
        PlayerVote kickVote = new PlayerVote(file, "vote.votes.kick", arguments);
        PlayerVote banVote = new PlayerVote(file, "vote.votes.ban", arguments);
        PlayerVote sunVote = new PlayerVote(file, "vote.votes.sun", arguments);
        PlayerVote rainVote = new PlayerVote(file, "vote.votes.rain", arguments);
        
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
        restartVote.setIgnoreUnvotedPlayers(true);
        restartVote.setArgumentCount(0);
        restartVote.setInsufficientArgumentsError("");
        restartVote.save();
        
        // Vote to set day
        List<String> daySuccessCommands = new ArrayList<String>();
        List<String> dayFailCommands = new ArrayList<String>();
        
        daySuccessCommands.add("time set 0");
        
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
        dayVote.setIgnoreUnvotedPlayers(true);
        dayVote.setArgumentCount(0);
        dayVote.setInsufficientArgumentsError("");
        dayVote.save();
        
        // Vote to set night
        List<String> nightSuccessCommands = new ArrayList<String>();
        List<String> nightFailCommands = new ArrayList<String>();
        
        nightSuccessCommands.add("time set 13000");
        
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
        nightVote.setIgnoreUnvotedPlayers(true);
        nightVote.setArgumentCount(0);
        nightVote.setInsufficientArgumentsError("");
        nightVote.save();
        
        // Vote to kick
        List<String> kickSuccessCommands = new ArrayList<String>();
        List<String> kickFailCommands = new ArrayList<String>();
        
        kickSuccessCommands.add("vote setvalue kick [%0]");
        
        kickVote.setDescription("Kick player");
        kickVote.setLastFailedVote(0);
        kickVote.setLastSuccessfulVote(0);
        kickVote.setVoteOnCooldownText("A vote to kick has been done too recently");
        kickVote.setVoteStartText("Vote to kick [%0] has started! type /vote yes or /vote no to vote");
        kickVote.setVoteSuccessText("Vote succeeded! See you later [%0]");
        kickVote.setVoteFailText("Vote failed to kick [%0]!");
        kickVote.setVoteSuccessCommands(kickSuccessCommands);
        kickVote.setVoteFailCommands(kickFailCommands);
        kickVote.setVoteSuccessCommandDelaySeconds(3);
        kickVote.setVoteFailCommandDelaySeconds(0);
        kickVote.setTimeoutSeconds(60);
        kickVote.setMinimumVotes(1);
        kickVote.setPercentToSucceed(70);
        kickVote.setCooldownMinutesToFailRevote(5);
        kickVote.setCooldownMinutesToSuccessRevote(1);
        kickVote.setArgumentCount(1);
        kickVote.setInsufficientArgumentsError("Incorrect arguments! You need to do '/Vote kick PlayerName' where PlayerName is the player's name");
        kickVote.save();
        
         // Vote to ban
        List<String> banSuccessCommands = new ArrayList<String>();
        List<String> banFailCommands = new ArrayList<String>();
        
        banSuccessCommands.add("vote setvalue ban [%0]");
        
        banVote.setDescription("Ban a player");
        banVote.setLastFailedVote(0);
        banVote.setLastSuccessfulVote(0);
        banVote.setVoteOnCooldownText("A player has been banned too recently!");
        banVote.setVoteStartText("Vote to ban [%0] has started! type /vote yes or /vote no to vote");
        banVote.setVoteSuccessText("Vote succeeded! [%0] has been banned");
        banVote.setVoteFailText("Vote to ban [%0] failed!");
        banVote.setVoteSuccessCommands(banSuccessCommands);
        banVote.setVoteFailCommands(banFailCommands);
        banVote.setVoteSuccessCommandDelaySeconds(3);
        banVote.setVoteFailCommandDelaySeconds(0);
        banVote.setTimeoutSeconds(60);
        banVote.setMinimumVotes(1);
        banVote.setPercentToSucceed(80);
        banVote.setCooldownMinutesToFailRevote(30);
        banVote.setCooldownMinutesToSuccessRevote(15);
        banVote.setArgumentCount(1);
        banVote.setInsufficientArgumentsError("Incorrect arguments! You need to do '/Vote ban PlayerName' where PlayerName is the player's name");
        banVote.save();
        
        // Vote to set sun
        List<String> sunSuccessCommands = new ArrayList<String>();
        List<String> sunFailCommands = new ArrayList<String>();
        
        sunSuccessCommands.add("vote setvalue sun");
        
        sunVote.setDescription("Set weather to sun");
        sunVote.setLastFailedVote(0);
        sunVote.setLastSuccessfulVote(0);
        sunVote.setVoteOnCooldownText("Weather has been set to sun too recently!");
        sunVote.setVoteStartText("Vote to set the weather to sun has started! type /vote yes or /vote no to vote");
        sunVote.setVoteSuccessText("Vote succeeded! It's now sunny.");
        sunVote.setVoteFailText("Vote failed! Try again next time.");
        sunVote.setVoteSuccessCommands(sunSuccessCommands);
        sunVote.setVoteFailCommands(sunFailCommands);
        sunVote.setVoteSuccessCommandDelaySeconds(3);
        sunVote.setVoteFailCommandDelaySeconds(0);
        sunVote.setTimeoutSeconds(60);
        sunVote.setMinimumVotes(1);
        sunVote.setPercentToSucceed(60);
        sunVote.setCooldownMinutesToFailRevote(10);
        sunVote.setCooldownMinutesToSuccessRevote(5);
        sunVote.setIgnoreUnvotedPlayers(true);
        sunVote.setArgumentCount(0);
        sunVote.setInsufficientArgumentsError("");
        sunVote.save();
        
        // Vote to set rain
        List<String> rainSuccessCommands = new ArrayList<String>();
        List<String> rainFailCommands = new ArrayList<String>();
        
        rainSuccessCommands.add("vote setvalue rain");
        
        rainVote.setDescription("Set weather to rain");
        rainVote.setLastFailedVote(0);
        rainVote.setLastSuccessfulVote(0);
        rainVote.setVoteOnCooldownText("Weather has been set to rain too recently!");
        rainVote.setVoteStartText("Vote to set the weather to rain has started! type /vote yes or /vote no to vote");
        rainVote.setVoteSuccessText("Vote succeeded! It's now rainy.");
        rainVote.setVoteFailText("Vote failed! Try again next time.");
        rainVote.setVoteSuccessCommands(rainSuccessCommands);
        rainVote.setVoteFailCommands(rainFailCommands);
        rainVote.setVoteSuccessCommandDelaySeconds(3);
        rainVote.setVoteFailCommandDelaySeconds(0);
        rainVote.setTimeoutSeconds(60);
        rainVote.setMinimumVotes(1);
        rainVote.setPercentToSucceed(60);
        rainVote.setCooldownMinutesToFailRevote(10);
        rainVote.setCooldownMinutesToSuccessRevote(5);
        rainVote.setIgnoreUnvotedPlayers(true);
        rainVote.setArgumentCount(0);
        rainVote.setInsufficientArgumentsError("");
        rainVote.save();
        
        // application information I'll need eventually for updates and whatnot
        super.configurationFile.setProperty("vote.application.files.config.Version", "1.1");
        super.configurationFile.setProperty("vote.application.Version", "1.1");
        
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
        super.configurationFile.setProperty("vote.default.PlayerSetValueNoPermission", "You do not have permission to the setvalue command.");
        super.configurationFile.setProperty("vote.default.PlayerVoteStartNoPermission", "You do not have permission to start a vote.");
        super.configurationFile.setProperty("vote.default.PlayerVoteChanged", "You changed your vote.");
        super.configurationFile.setProperty("vote.default.PlayerUnbannable", "[%0] cannot be banned!");
        super.configurationFile.setProperty("vote.default.PlayerUnkickable", "[%0] cannot be kicked!");
        super.configurationFile.setProperty("vote.default.PlayerNotFound", "[%0] cannot be found!");

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
    
    /**
     * Yet again it has to do with arguments. This replaces any possible instance of an argument with the value
     * @param location The location of the string. Generally something like "vote.default.VoteStartText"
     * @return A string from the file
     */
    private String getStringFromFile(String location)
    {
        String foundString = this.configurationFile.getString(location);
        String[] args;
        
        try
        {
            args = arguments.call();
            
            for (int i = 0; i < args.length; i++)
                foundString = foundString.replaceAll("\\[\\%" + i + "\\]", args[i]);
        } 
        catch (Exception e) { }
        
        return foundString;
    }
    
    public String getVoteStartText()
    {
        return getStringFromFile("vote.default.VoteStartText");
    }
    
    public void setVoteStartText(String defaultVoteStartText)
    {
        super.configurationFile.setProperty("vote.default.VoteStartText", defaultVoteStartText);
        this.save();
    }
    
    public String getVoteEndSuccessText()
    {
        return getStringFromFile("vote.default.VoteEndSuccessText");
    }
    
    public void setVoteEndSuccessText(String defaulVoteEndSuccessText)
    {
        super.configurationFile.setProperty("vote.default.VoteEndSuccessText", defaulVoteEndSuccessText);
        this.save();
    }
    
    public String getVoteEndFailText()
    {
        return getStringFromFile("vote.default.VoteEndFailText");
    }
    
    public void setVoteEndFailText(String defaulVoteEndFailText)
    {
        super.configurationFile.setProperty("vote.default.VoteEndFailText", defaulVoteEndFailText);
        this.save();
    }
    
    public String getVoteAlreadyInProgress()
    {
        return getStringFromFile("vote.default.VoteAlreadyInProgress");
    }
    
    public void setVoteAlreadyInProgress(String voteAlreadyInProgress)
    {
        super.configurationFile.setProperty("vote.default.VoteAlreadyInProgress", voteAlreadyInProgress);
        this.save();
    }
    
    public String getVoteCanceled()
    {
        return getStringFromFile("vote.default.VoteCanceled");
    }
    
    public void setVoteCanceled(String voteCanceled)
    {
        super.configurationFile.setProperty("vote.default.VoteCanceled", voteCanceled);
        this.save();
    }
    
    public String getNoVoteInProgress()
    {
        return getStringFromFile("vote.default.NoVoteInProgress");
    }
    
    public void setNoVoteInProgress(String noVoteInProgress)
    {
        super.configurationFile.setProperty("vote.default.NoVoteInProgress", noVoteInProgress);
        this.save();
    }
    
    public String getPlayerVoteCounted()
    {
        return getStringFromFile("vote.default.PlayerVoteCounted");
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
        return getStringFromFile("vote.default.PlayerAlreadyVoted");
    }
    
    public void setPlayerAlreadyVoted(String playerAlreadyVoted)
    {
        super.configurationFile.setProperty("vote.default.PlayerAlreadyVoted", playerAlreadyVoted);
        this.save();
    }

    public String getPlayerVoteNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteNoPermission");
    }
    
    public void setPlayerVoteNoPermission(String playerVoteNoPermission)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteNoPermission", playerVoteNoPermission);
        this.save();
    }
    
    public String getPlayerVoteStartNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteStartNoPermission");
    }
    
    public void setPlayerUnbannable(String playerUnbannable)
    {
        super.configurationFile.setProperty("vote.default.PlayerUnbannable", playerUnbannable);
        this.save();
    }
    
    public String getPlayerUnbannable()
    {
        return getStringFromFile("vote.default.PlayerUnbannable");
    }
    
    public void setPlayerUnkickable(String playerUnkickable)
    {
        super.configurationFile.setProperty("vote.default.PlayerUnkickable", playerUnkickable);
        this.save();
    }
    
    public String getPlayerUnkickable()
    {
        return getStringFromFile("vote.default.PlayerUnkickable");
    }
    
    public void setPlayerVoteStartNoPermission(String playerVoteStartNoPermission)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteStartNoPermission", playerVoteStartNoPermission);
        this.save();
    }
    
    public String getPlayerVoteChanged()
    {
        return getStringFromFile("vote.default.PlayerVoteChanged");
    }
    
    public void setPlayerVoteChanged(String playerVoteChanged)
    {
        super.configurationFile.setProperty("vote.default.PlayerVoteChanged", playerVoteChanged);
        this.save();
    }
    
    public String getGeneralHelpNotFound()
    {
        return getStringFromFile("vote.help.GeneralHelpNotFound");
    }
    
    public void setGeneralHelpNotFound(String generalHelpNotFound)
    {
        super.configurationFile.setProperty("vote.help.GeneralHelpNotFound", generalHelpNotFound);
        this.save();
    }
    
    public String getVoteStartHelpNotFound()
    {
        return getStringFromFile("vote.help.VoteStartHelpNotFound");
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
    
    public void setPlayerSetValueNoPermission(String playerSetValueNoPermission)
    {
        super.configurationFile.setProperty("vote.default.PlayerSetValueNoPermission", playerSetValueNoPermission);
        this.save();
    }
    
    public String getPlayerSetValueNoPermission()
    {
        return super.configurationFile.getString("vote.default.PlayerSetValueNoPermission");
    }
    
    public void setPlayerNotFound(String playerNotFound)
    {
        super.configurationFile.setProperty("vote.default.PlayerNotFound", playerNotFound);
        this.save();
    }
    
    public String getPlayerNotFound()
    {
        return getStringFromFile("vote.default.PlayerNotFound");
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
            playerVote = new PlayerVote(super.configurationFile, "vote.votes." + voteType, arguments);
            
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
        return new PlayerVote(super.configurationFile, "vote.votes." + playerVote, arguments);
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