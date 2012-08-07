package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.RabidCrab.Vote.Common.TextFormatter;

import org.bukkit.configuration.Configuration;

/**
 * A PlayerVote is an instance of a vote, and holds no data beyond the settings of the vote located in the Config.yml
 * @author RabidCrab
 *
 */
public class PlayerVote
{
    private boolean saved = true;
    private String voteFullName;
    private String voteShortName;
    private Configuration configurationFile;

    private String Description;
    private String VoteOnCooldownText;
    private long LastFailedVote;
    private long LastSuccessfulVote;
    private String VoteStartText;
    private String VoteSuccessText;
    private String VoteFailText;
    private List<String> VoteSuccessCommands;
    private List<String> VoteFailCommands;
    private int VoteSuccessCommandDelaySeconds;
    private int VoteFailCommandDelaySeconds;
    private int TimeoutSeconds;
    private int MinimumVotes;
    private int PercentToSucceed;
    private int CooldownMinutesToFailRevote;
    private int CooldownMinutesToSuccessRevote;
    private int ArgumentCount;
    private String InsufficientArgumentsError;
    private Callable<ArrayList<String>> arguments;
    
    private boolean IgnoreUnvotedPlayers;
    
    /**
     * Used to deal with the numerous kind of votes allowed
     * @param voteName The name of the vote
     */
    public PlayerVote(Configuration file, String voteName, Callable<ArrayList<String>> callable)
    {
        this.voteFullName = voteName;
        this.voteShortName = voteName.substring(voteName.lastIndexOf('.') + 1, voteName.length());
        this.configurationFile = file;
        this.arguments = callable;
        
        Description = file.getString(voteFullName + ".Description"); 
        VoteOnCooldownText = file.getString(voteFullName + ".VoteOnCooldownText");
        LastFailedVote = (long)file.getDouble(voteFullName + ".LastFailedVote", 0);
        LastSuccessfulVote = (long)file.getDouble(voteFullName + ".LastSuccessfulVote", 0);
        VoteStartText = file.getString(voteFullName + ".VoteStartText");
        VoteSuccessText = file.getString(voteFullName + ".VoteSuccessText");
        VoteFailText = file.getString(voteFullName + ".VoteFailText");
        VoteSuccessCommands = file.getStringList(voteFullName + ".VoteSuccessCommands");
        VoteFailCommands = file.getStringList(voteFullName + ".VoteFailCommands");
        VoteSuccessCommandDelaySeconds = file.getInt(voteFullName + ".VoteSuccessCommandDelaySeconds", 0);
        VoteFailCommandDelaySeconds = file.getInt(voteFullName + ".VoteFailCommandDelaySeconds", 0);
        TimeoutSeconds = file.getInt(voteFullName + ".TimeoutSeconds", 60);
        MinimumVotes = file.getInt(voteFullName + ".MinimumVotes", 0);
        PercentToSucceed = file.getInt(voteFullName + ".PercentToSucceed", 50);
        CooldownMinutesToFailRevote = file.getInt(voteFullName + ".CooldownMinutesToFailRevote", 0);
        CooldownMinutesToSuccessRevote = file.getInt(voteFullName + ".CooldownMinutesToSuccessRevote", 0);
        IgnoreUnvotedPlayers = file.getBoolean(voteFullName + ".IgnoreUnvotedPlayers", true);
        ArgumentCount = file.getInt(voteFullName + ".ArgumentCount", 0);
        InsufficientArgumentsError = file.getString(voteFullName + ".InsufficientArgumentsError");
    }
    
    /**
     * Attempt to save the file to the configuration file
     * @return true if save successful
     */
    public boolean save()
    {   
        if (!saved && configurationFile != null)
        {
            try
            {
                configurationFile.set(voteFullName + ".Description", Description);
                configurationFile.set(voteFullName + ".VoteOnCooldownText", VoteOnCooldownText);
                configurationFile.set(voteFullName + ".LastFailedVote", LastFailedVote);
                configurationFile.set(voteFullName + ".LastSuccessfulVote", LastSuccessfulVote);
                configurationFile.set(voteFullName + ".VoteStartText", VoteStartText);
                configurationFile.set(voteFullName + ".VoteSuccessText", VoteSuccessText);
                configurationFile.set(voteFullName + ".VoteFailText", VoteFailText);
                configurationFile.set(voteFullName + ".VoteSuccessCommands", VoteSuccessCommands);
                configurationFile.set(voteFullName + ".VoteFailCommands", VoteFailCommands);
                configurationFile.set(voteFullName + ".VoteSuccessCommandDelaySeconds", VoteSuccessCommandDelaySeconds);
                configurationFile.set(voteFullName + ".VoteFailCommandDelaySeconds", VoteFailCommandDelaySeconds);
                configurationFile.set(voteFullName + ".TimeoutSeconds", TimeoutSeconds);
                configurationFile.set(voteFullName + ".MinimumVotes", MinimumVotes);
                configurationFile.set(voteFullName + ".PercentToSucceed", PercentToSucceed);
                configurationFile.set(voteFullName + ".CooldownMinutesToFailRevote", CooldownMinutesToFailRevote);
                configurationFile.set(voteFullName + ".CooldownMinutesToSuccessRevote", CooldownMinutesToSuccessRevote);
                configurationFile.set(voteFullName + ".IgnoreUnvotedPlayers", IgnoreUnvotedPlayers);
                configurationFile.set(voteFullName + ".ArgumentCount", ArgumentCount);
                configurationFile.set(voteFullName + ".InsufficientArgumentsError", InsufficientArgumentsError);
            }
            catch (Exception e)
            {
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * Yet again it has to do with arguments. This replaces any possible instance of an argument with the value
     * @param location The location of the string. Generally something like "vote.default.VoteStartText"
     * @return A string from the file
     */
    private String getString(String text)
    {
        ArrayList<String> args;
        
        try
        {
            args = arguments.call();
        } 
        catch (Exception e)
        {
            return e.getMessage();
            //return text;
        }
        
        for (int i = 0; i < args.size(); i++)
            text = text.replaceAll("\\[\\%" + i + "\\]", args.get(i));
        
        // Return the formatted text
        return TextFormatter.format(text);
    }

    public boolean isSaved()
    {
        return saved;
    }

    public String getVoteFullName()
    {
        return getString(voteFullName);
    }

    public void setVoteSuccessText(String voteSuccessText)
    {
        saved = false;
        VoteSuccessText = voteSuccessText;
    }

    public String getVoteSuccessText()
    {
        return getString(VoteSuccessText);
    }

    public void setVoteFailText(String voteFailText)
    {
        saved = false;
        VoteFailText = voteFailText;
    }

    public String getVoteFailText()
    {
        return getString(VoteFailText);
    }

    public void setVoteSuccessCommands(List<String> voteSuccessCommands)
    {
        saved = false;
        VoteSuccessCommands = voteSuccessCommands;
    }

    public List<String> getVoteSuccessCommands()
    {
        return VoteSuccessCommands;
    }

    public void setVoteFailCommands(List<String> voteFailCommands)
    {
        saved = false;
        VoteFailCommands = voteFailCommands;
    }

    public List<String> getVoteFailCommands()
    {
        return VoteFailCommands;
    }

    public void setVoteSuccessCommandDelaySeconds(int voteSuccessCommandDelaySeconds)
    {
        saved = false;
        VoteSuccessCommandDelaySeconds = voteSuccessCommandDelaySeconds;
    }

    public int getVoteSuccessCommandDelaySeconds()
    {
        return VoteSuccessCommandDelaySeconds;
    }

    public void setVoteFailCommandDelaySeconds(int voteFailCommandDelaySeconds)
    {
        saved = false;
        VoteFailCommandDelaySeconds = voteFailCommandDelaySeconds;
    }

    public int getVoteFailCommandDelaySeconds()
    {
        return VoteFailCommandDelaySeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds)
    {
        saved = false;
        TimeoutSeconds = timeoutSeconds;
    }

    public int getTimeoutSeconds()
    {
        return TimeoutSeconds;
    }

    public void setMinimumVotes(int minimumVotes)
    {
        saved = false;
        MinimumVotes = minimumVotes;
    }

    public int getMinimumVotes()
    {
        return MinimumVotes;
    }

    public void setPercentToSucceed(int percentToSucceed)
    {
        saved = false;
        PercentToSucceed = percentToSucceed;
    }

    public int getPercentToSucceed()
    {
        return PercentToSucceed;
    }

    public void setCooldownMinutesToFailRevote(int cooldownMinutesToFailRevote)
    {
        saved = false;
        CooldownMinutesToFailRevote = cooldownMinutesToFailRevote;
    }

    public int getCooldownMinutesToFailRevote()
    {
        return CooldownMinutesToFailRevote;
    }

    public void setCooldownMinutesToSuccessRevote(int cooldownMinutesToSuccessRevote)
    {
        saved = false;
        CooldownMinutesToSuccessRevote = cooldownMinutesToSuccessRevote;
    }

    public int getCooldownMinutesToSuccessRevote()
    {
        return CooldownMinutesToSuccessRevote;
    }

    public String getVoteShortName()
    {
        return voteShortName;
    }
    
    public void setConfigurationFile(Configuration file)
    {
        configurationFile = file;
    }

    public void setVoteStartText(String voteStartText)
    {
        saved = false;
        VoteStartText = voteStartText;
    }

    public String getVoteStartText()
    {
        return getString(VoteStartText);
    }

    public void setLastFailedVote(long lastFailedVote)
    {
        saved = false;
        LastFailedVote = lastFailedVote;
    }

    public long getLastFailedVote()
    {
        return LastFailedVote;
    }
    
    public void setLastSuccessfulVote(long lastSuccessfulVote)
    {
        saved = false;
        LastSuccessfulVote = lastSuccessfulVote;
    }

    public long getLastSuccessfulVote()
    {
        return LastSuccessfulVote;
    }
    
    public void setVoteOnCooldownText(String voteOnCooldownText)
    {
        saved = false;
        VoteOnCooldownText = voteOnCooldownText;
    }

    public String getVoteOnCooldownText()
    {
        return getString(VoteOnCooldownText);
    }

    public void setDescription(String description)
    {
        saved = false;
        Description = description;
    }

    public String getDescription()
    {
        return Description;
    }
    
    public void setIgnoreUnvotedPlayers(boolean ignoreUnvotedPlayers)
    {
        saved = false;
        IgnoreUnvotedPlayers = ignoreUnvotedPlayers;
    }

    public boolean getIgnoreUnvotedPlayers()
    {
        return IgnoreUnvotedPlayers;
    }

    public void setArgumentCount(int argumentCount)
    {
        saved = false;
        ArgumentCount = argumentCount;
    }

    public int getArgumentCount()
    {
        return ArgumentCount;
    }

    public void setInsufficientArgumentsError(String insufficientArgumentsError)
    {
        saved = false;
        InsufficientArgumentsError = insufficientArgumentsError;
    }

    public String getInsufficientArgumentsError()
    {
        return getString(InsufficientArgumentsError);
    }
}