package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.config.Configuration;

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
    
    /**
     * Used to deal with the numerous kind of votes allowed
     * @param voteName The name of the vote
     */
    public PlayerVote(Configuration file, String voteName)
    {
        this.voteFullName = voteName;
        this.voteShortName = voteName.substring(voteName.lastIndexOf('.') + 1, voteName.length() - 1);
        this.configurationFile = file;

        Description = file.getString(voteFullName + ".Description"); 
        VoteOnCooldownText = file.getString(voteFullName + ".VoteOnCooldownText");
        LastFailedVote = (long)file.getDouble(voteFullName + ".LastFailedVote", 0);
        LastSuccessfulVote = (long)file.getDouble(voteFullName + ".LastSuccessfulVote", 0);
        VoteStartText = file.getString(voteFullName + ".VoteStartText");
        VoteSuccessText = file.getString(voteFullName + ".VoteSuccessText");
        VoteFailText = file.getString(voteFullName + ".VoteFailText");
        VoteSuccessCommands = file.getStringList(voteFullName + ".VoteSuccessCommands", new ArrayList<String>());
        VoteFailCommands = file.getStringList(voteFullName + ".VoteFailCommands", new ArrayList<String>());
        VoteSuccessCommandDelaySeconds = file.getInt(voteFullName + ".VoteSuccessCommandDelaySeconds", 0);
        VoteFailCommandDelaySeconds = file.getInt(voteFullName + ".VoteFailCommandDelaySeconds", 0);
        TimeoutSeconds = file.getInt(voteFullName + ".TimeoutSeconds", 60);
        MinimumVotes = file.getInt(voteFullName + ".MinimumVotes", 0);
        PercentToSucceed = file.getInt(voteFullName + ".PercentToSucceed", 50);
        CooldownMinutesToFailRevote = file.getInt(voteFullName + ".CooldownMinutesToFailRevote", 0);
        CooldownMinutesToSuccessRevote = file.getInt(voteFullName + ".CooldownMinutesToSuccessRevote", 0);
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
                configurationFile.setProperty(voteFullName + ".Description", Description);
                configurationFile.setProperty(voteFullName + ".VoteOnCooldownText", VoteOnCooldownText);
                configurationFile.setProperty(voteFullName + ".LastFailedVote", LastFailedVote);
                configurationFile.setProperty(voteFullName + ".LastSuccessfulVote", LastSuccessfulVote);
                configurationFile.setProperty(voteFullName + ".VoteStartText", VoteStartText);
                configurationFile.setProperty(voteFullName + ".VoteSuccessText", VoteSuccessText);
                configurationFile.setProperty(voteFullName + ".VoteFailText", VoteFailText);
                configurationFile.setProperty(voteFullName + ".VoteSuccessCommands", VoteSuccessCommands);
                configurationFile.setProperty(voteFullName + ".VoteFailCommands", VoteFailCommands);
                configurationFile.setProperty(voteFullName + ".VoteSuccessCommandDelaySeconds", VoteSuccessCommandDelaySeconds);
                configurationFile.setProperty(voteFullName + ".VoteFailCommandDelaySeconds", VoteFailCommandDelaySeconds);
                configurationFile.setProperty(voteFullName + ".TimeoutSeconds", TimeoutSeconds);
                configurationFile.setProperty(voteFullName + ".MinimumVotes", MinimumVotes);
                configurationFile.setProperty(voteFullName + ".PercentToSucceed", PercentToSucceed);
                configurationFile.setProperty(voteFullName + ".CooldownMinutesToFailRevote", CooldownMinutesToFailRevote);
                configurationFile.setProperty(voteFullName + ".CooldownMinutesToSuccessRevote", CooldownMinutesToSuccessRevote);
                
                configurationFile.save();
            }
            catch (Exception e)
            {
                return false;
            }
        }
    
        return true;
    }

    public boolean isSaved()
    {
        return saved;
    }

    public String getVoteFullName()
    {
        return voteFullName;
    }

    public void setVoteSuccessText(String voteSuccessText)
    {
        saved = false;
        VoteSuccessText = voteSuccessText;
    }

    public String getVoteSuccessText()
    {
        return VoteSuccessText;
    }

    public void setVoteFailText(String voteFailText)
    {
        saved = false;
        VoteFailText = voteFailText;
    }

    public String getVoteFailText()
    {
        return VoteFailText;
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

    public void setVoteSuccessCommandDelaySeconds(
            int voteSuccessCommandDelaySeconds)
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

    public void setCooldownMinutesToSuccessRevote(
            int cooldownMinutesToSuccessRevote)
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
        return VoteStartText;
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
        return VoteOnCooldownText;
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
    
    
}