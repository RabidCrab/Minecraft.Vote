package me.RabidCrab.Vote;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import me.RabidCrab.Vote.Common.TextFormatter;

import org.bukkit.plugin.Plugin;

/**
 * Currently the file with all of the configuration information.
 * May split file up later if there's any issues of size or scope.
 * @author RabidCrab
 */
public class DefaultConfigurationFile
{
    Plugin plugin;
    Callable<ArrayList<String>> arguments;
    
    public DefaultConfigurationFile(Plugin plugin, Callable<ArrayList<String>> callable)
    {
        this.plugin = plugin;
        this.arguments = callable;
        
        // This won't overwrite the config if it's already there
        plugin.saveDefaultConfig();
    }
    
    /**
     * Yet again it has to do with arguments. This replaces any possible instance of an argument with the value
     * @param location The location of the string. Generally something like "vote.default.VoteStartText"
     * @return A string from the file
     */
    private String getStringFromFile(String location)
    {
        String foundString = plugin.getConfig().getString(location);
        ArrayList<String> args;
        
        try
        {
            args = arguments.call();
            
            for (int i = 0; i < args.size(); i++)
                foundString = foundString.replaceAll("\\[\\%" + i + "\\]", args.get(i));
        } 
        catch (Exception e) { }
        
        // Format the text for colors
        return TextFormatter.format(foundString);
    }
    
    public String getVoteStartText()
    {
        return getStringFromFile("vote.default.VoteStartText");
    }
    
    public void setVoteStartText(String defaultVoteStartText)
    {
        plugin.getConfig().set("vote.default.VoteStartText", defaultVoteStartText);
        this.save();
    }
    
    public String getVoteEndSuccessText()
    {
        return getStringFromFile("vote.default.VoteEndSuccessText");
    }
    
    public void setVoteEndSuccessText(String defaulVoteEndSuccessText)
    {
        plugin.getConfig().set("vote.default.VoteEndSuccessText", defaulVoteEndSuccessText);
        this.save();
    }
    
    public String getVoteEndFailText()
    {
        return getStringFromFile("vote.default.VoteEndFailText");
    }
    
    public void setVoteEndFailText(String defaulVoteEndFailText)
    {
        plugin.getConfig().set("vote.default.VoteEndFailText", defaulVoteEndFailText);
        this.save();
    }
    
    public String getVoteAlreadyInProgress()
    {
        return getStringFromFile("vote.default.VoteAlreadyInProgress");
    }
    
    public void setVoteAlreadyInProgress(String voteAlreadyInProgress)
    {
        plugin.getConfig().set("vote.default.VoteAlreadyInProgress", voteAlreadyInProgress);
        this.save();
    }
    
    public String getVoteCanceled()
    {
        return getStringFromFile("vote.default.VoteCanceled");
    }
    
    public void setVoteCanceled(String voteCanceled)
    {
        plugin.getConfig().set("vote.default.VoteCanceled", voteCanceled);
        this.save();
    }
    
    public String getNoVoteInProgress()
    {
        return getStringFromFile("vote.default.NoVoteInProgress");
    }
    
    public void setNoVoteInProgress(String noVoteInProgress)
    {
        plugin.getConfig().set("vote.default.NoVoteInProgress", noVoteInProgress);
        this.save();
    }
    
    public String getPlayerVoteCounted()
    {
        return getStringFromFile("vote.default.PlayerVoteCounted");
    }
    
    public void setPlayerVoteCounted(String playerVoteCounted)
    {
        plugin.getConfig().set("vote.default.PlayerVoteCounted", playerVoteCounted);
        this.save();
    }
    
    public List<String> getAllVoteTypes()
    {
        if (plugin.getConfig().getConfigurationSection("vote.votes") != null)
            return new ArrayList<String>(plugin.getConfig().getConfigurationSection("vote.votes").getKeys(false));
        else
            return null;
    }
    
    public String getPlayerAlreadyVoted()
    {
        return getStringFromFile("vote.default.PlayerAlreadyVoted");
    }
    
    public void setPlayerAlreadyVoted(String playerAlreadyVoted)
    {
        plugin.getConfig().set("vote.default.PlayerAlreadyVoted", playerAlreadyVoted);
        this.save();
    }

    public String getPlayerVoteNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteNoPermission");
    }
    
    public void setPlayerVoteNoPermission(String playerVoteNoPermission)
    {
        plugin.getConfig().set("vote.default.PlayerVoteNoPermission", playerVoteNoPermission);
        this.save();
    }
    
    public String getPlayerVoteStartNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteStartNoPermission");
    }
    
    public void setPlayerUnbannable(String playerUnbannable)
    {
        plugin.getConfig().set("vote.default.PlayerUnbannable", playerUnbannable);
        this.save();
    }
    
    public String getPlayerUnbannable()
    {
        return getStringFromFile("vote.default.PlayerUnbannable");
    }
    
    public void setPlayerUnkickable(String playerUnkickable)
    {
        plugin.getConfig().set("vote.default.PlayerUnkickable", playerUnkickable);
        this.save();
    }
    
    public String getPlayerUnkickable()
    {
        return getStringFromFile("vote.default.PlayerUnkickable");
    }
    
    public void setPlayerVoteStartNoPermission(String playerVoteStartNoPermission)
    {
        plugin.getConfig().set("vote.default.PlayerVoteStartNoPermission", playerVoteStartNoPermission);
        this.save();
    }
    
    public String getPlayerVoteChanged()
    {
        return getStringFromFile("vote.default.PlayerVoteChanged");
    }
    
    public void setPlayerVoteChanged(String playerVoteChanged)
    {
        plugin.getConfig().set("vote.default.PlayerVoteChanged", playerVoteChanged);
        this.save();
    }
    
    public String getGeneralHelpNotFound()
    {
        return getStringFromFile("vote.help.GeneralHelpNotFound");
    }
    
    public void setGeneralHelpNotFound(String generalHelpNotFound)
    {
        plugin.getConfig().set("vote.help.GeneralHelpNotFound", generalHelpNotFound);
        this.save();
    }
    
    public String getVoteStartHelpNotFound()
    {
        return getStringFromFile("vote.help.VoteStartHelpNotFound");
    }
    
    public void setVoteStartHelpNotFound(String voteStartHelpNotFound)
    {
        plugin.getConfig().set("vote.help.VoteStartHelpNotFound", voteStartHelpNotFound);
        this.save();
    }
    
    public List<String> getGeneralCommandsHelp()
    {
        return plugin.getConfig().getStringList("vote.help.GeneralCommands");
    }
    
    public void setPlayerSetValueNoPermission(String playerSetValueNoPermission)
    {
        plugin.getConfig().set("vote.default.PlayerSetValueNoPermission", playerSetValueNoPermission);
        this.save();
    }
    
    public String getPlayerSetValueNoPermission()
    {
        return getStringFromFile("vote.default.PlayerSetValueNoPermission");
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
            playerVote = new PlayerVote(plugin.getConfig(), "vote.votes." + voteType, arguments);
            
            returnList.add(new SimpleEntry<String,String>(voteType, playerVote.getDescription()));
        }
        
        return returnList;
    }
    
    /**
     * Save any changes made to the configuration file
     */
    public void save()
    {
        plugin.saveDefaultConfig();
    }
    
    /**
     * Get the specific PlayerVote
     * @param playerVote
     * @return
     */
    public PlayerVote getPlayerVote(Vote plugin, String playerVote)
    {
        return new PlayerVote(plugin.getConfig(), "vote.votes." + playerVote, arguments);
    }
    
    /**
     * Saves the player vote to this specific configuration file
     * @param playerVote The PlayerVote to save
     * @return True if save successful
     */
    public boolean savePlayerVote(PlayerVote playerVote)
    {
        playerVote.setConfigurationFile(plugin.getConfig());
        
        return playerVote.save();
    }
}