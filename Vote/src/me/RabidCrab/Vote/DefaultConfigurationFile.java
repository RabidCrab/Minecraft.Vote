package me.RabidCrab.Vote;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import me.RabidCrab.Vote.Common.TextFormatter;

import org.bukkit.plugin.Plugin;

/**
 * The file with all of the configuration information
 * @author RabidCrab
 * 
 */
public class DefaultConfigurationFile
{
    Plugin plugin;
    Callable<ArrayList<String>> arguments;
    
    /**
     * Initiate the default configuration file used in the plugin
     * @param plugin
     * @param callable The list of arguments in the vote
     */
    public DefaultConfigurationFile(Plugin plugin, Callable<ArrayList<String>> callable)
    {
        this.plugin = plugin;
        this.arguments = callable;
        
        // This won't overwrite the config if it's already there
        plugin.saveDefaultConfig();
        
        // Check if it's the latest version. If it's not, we need to update the file
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.4") != 0)
            UpdateToLatestVersion();
    }
    
    /**
     * Run all of the necessary updates to the config file. For ease of use and just in case someone has a really old config,
     * I have it loop through all the ones I've ever made, and had them apply the update one at a time
     */
    private void UpdateToLatestVersion()
    {
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.2") == 0)
            UpdateTo13();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.3") == 0)
            UpdateTo14();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.4") == 0)
            UpdateTo15();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.5") == 0)
            UpdateTo16();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.6") == 0)
            UpdateTo17();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.7") == 0)
            UpdateTo18();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.8") == 0)
            UpdateTo19();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("1.9") == 0)
            UpdateTo20();
        
        if (plugin.getConfig().getString("vote.application.files.config.Version").compareTo("2.0") == 0)
            UpdateTo21();
    }
    
    private void UpdateTo21()
    {
    	plugin.getLogger().log(Level.INFO, "Updating Config file to 2.1");

    	plugin.getConfig().set("vote.default.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote! Click below to vote!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.ban.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%1] has started a vote to ban &C[%0]&A!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.kick.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%1] has started a vote to kick &C[%0]&A!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.restart.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote to restart the server!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.night.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote to set the time to night!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.day.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote to set the time to day!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.rain.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote to set the weather to rain!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");
    	plugin.getConfig().set("vote.votes.sun.VoteStartText", "tellraw @p [\"\",{\"text\":\"&A[%0] has started a vote to set the weather to sunny!\"},{\"text\":\"\nVote yes\",\"color\":\"green\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote yes\"}},{\"text\":\"\nVote no\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/vote no\"}}]");

    	plugin.getConfig().set("vote.application.files.config.Version", "2.1");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 2.1 successful");
    }
    
    private void UpdateTo20()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 2.0");
        
        plugin.getConfig().set("vote.default.PlayerIPAlreadyVoted", "&CA player with your IP address has already voted!");
        plugin.getConfig().set("vote.default.CheckPlayerIPAddress", true);
        
        plugin.getConfig().set("vote.application.files.config.Version", "2.0");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 2.0 successful");
    }
    
    private void UpdateTo19()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.9");
        
        List<String> reloadCommands = new LinkedList<String>();
        reloadCommands.add("reload");

        plugin.getConfig().set("vote.default.VoteNotFound", "&C[%0] does not exist!");
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.9");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 1.9 successful");
    }
    
    private void UpdateTo18()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.8");
        
        List<String> reloadCommands = new LinkedList<String>();
        reloadCommands.add("reload");
        plugin.getConfig().set("vote.default.ReloadCommands", reloadCommands);
        
        plugin.getConfig().set("vote.default.ReloadNoPermission", "&CYou do not have permission to reload!");
        
        List<String> voteHelpCommands = plugin.getConfig().getStringList("vote.help.GeneralCommands");
        voteHelpCommands.add("&6/vote reload &A- Reload Vote plugin.");
        plugin.getConfig().set("vote.help.GeneralCommands", voteHelpCommands);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.8");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 1.8 successful");
    }
    
    private void UpdateTo17()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.7");
        
        List<String> voteBanCommands = new LinkedList<String>();
        voteBanCommands.add("FINDPLAYER[%0]");
        voteBanCommands.add("VERIFYPLAYERONLINE [%0]");
        voteBanCommands.add("vote setvalue ban [%0]");
        plugin.getConfig().set("vote.votes.ban.VoteSuccessCommands", voteBanCommands);
        
        List<String> voteKickCommands = new LinkedList<String>();
        voteKickCommands.add("FINDPLAYER[%0]");
        voteKickCommands.add("VERIFYPLAYERONLINE [%0]");
        voteKickCommands.add("vote setvalue kick [%0]");
        plugin.getConfig().set("vote.votes.kick.VoteSuccessCommands", voteKickCommands);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.7");
        plugin.saveConfig();
        plugin.getLogger().log(Level.INFO, "Update to 1.7 successful");
    }
    
    private void UpdateTo16()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.6");
        
        List<String> voteYesCommands = new LinkedList<String>();
        voteYesCommands.add("yes");
        voteYesCommands.add("y");
        plugin.getConfig().set("vote.default.VoteYesCommands", voteYesCommands);
        
        List<String> voteNoCommands = new LinkedList<String>();
        voteNoCommands.add("no");
        voteNoCommands.add("n");
        plugin.getConfig().set("vote.default.VoteNoCommands", voteNoCommands);
        
        List<String> voteListCommands = new LinkedList<String>();
        voteListCommands.add("list");
        plugin.getConfig().set("vote.default.VoteListCommands", voteListCommands);
        
        List<String> voteHelpCommands = new LinkedList<String>();
        voteHelpCommands.add("help");
        plugin.getConfig().set("vote.default.VoteHelpCommands", voteHelpCommands);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.6");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 1.6 successful");
    }
    
    private void UpdateTo15()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.5");
        
        List<String> voteBanCommands = new LinkedList<String>();
        voteBanCommands.add("VERIFYPLAYERONLINE [%0]");
        voteBanCommands.add("vote setvalue ban [%0]");
        plugin.getConfig().set("vote.votes.ban.VoteSuccessCommands", voteBanCommands);
        
        List<String> voteKickCommands = new LinkedList<String>();
        voteKickCommands.add("VERIFYPLAYERONLINE [%0]");
        voteKickCommands.add("vote setvalue kick [%0]");
        plugin.getConfig().set("vote.votes.kick.VoteSuccessCommands", voteKickCommands);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.5");
        plugin.saveConfig();
        plugin.getLogger().log(Level.INFO, "Update to 1.5 successful");
    }
    
    private void UpdateTo14()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.4");
        
        List<String> voteVetoCommands = new LinkedList<String>();
        voteVetoCommands.add("veto");
        plugin.getConfig().set("vote.default.VoteVetoCommands", voteVetoCommands);
        
        plugin.getConfig().set("vote.default.VoteVetoNoPermission", "&CYou do not have permission to veto!");
        
        List<String> voteHelpCommands = plugin.getConfig().getStringList("vote.help.GeneralCommands");
        voteHelpCommands.add("&6/vote veto &A- Immediately cancel vote in progress.");
        plugin.getConfig().set("vote.help.GeneralCommands", voteHelpCommands);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.4");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 1.4 successful");
    }
    
    private void UpdateTo13()
    {
        plugin.getLogger().log(Level.INFO, "Updating Config file to 1.3");
        
        List<String> voteYesCommands = new LinkedList<String>();
        voteYesCommands.add("yes");
        voteYesCommands.add("y");
        plugin.getConfig().set("vote.default.VoteYesCommands", voteYesCommands);
        
        List<String> voteNoCommands = new LinkedList<String>();
        voteNoCommands.add("no");
        voteNoCommands.add("n");
        plugin.getConfig().set("vote.default.VoteNoCommands", voteNoCommands);
        
        List<String> voteListCommands = new LinkedList<String>();
        voteListCommands.add("list");
        plugin.getConfig().set("vote.default.VoteListCommands", voteListCommands);
        
        List<String> voteHelpCommands = new LinkedList<String>();
        voteHelpCommands.add("help");
        plugin.getConfig().set("vote.default.VoteHelpCommands", voteHelpCommands);
        
        List<String> voteDay = new LinkedList<String>();
        voteDay.add("vote setvalue time 0");
        plugin.getConfig().set("vote.votes.day.VoteSuccessCommands", voteDay);
        
        List<String> voteNight = new LinkedList<String>();
        voteNight.add("vote setvalue time 13000");
        plugin.getConfig().set("vote.votes.night.VoteSuccessCommands", voteNight);
        
        plugin.getConfig().set("vote.application.files.config.Version", "1.3");
        plugin.saveConfig();
        
        plugin.getLogger().log(Level.INFO, "Update to 1.3 successful");
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
    
    /**
     * Yet again it has to do with arguments. This replaces any possible instance of an argument with the value
     * @param location The location of the string. Generally something like "vote.default.VoteStartText"
     * @param args The arguments to compare
     * @return A string from the file
     */
    private String getStringFromFile(String location, ArrayList<String> args)
    {
        String foundString = plugin.getConfig().getString(location);
        
        try
        {
            for (int i = 0; i < args.size(); i++)
                foundString = foundString.replaceAll("\\[\\%" + i + "\\]", args.get(i));
        }
        catch (Exception e) { }
        
        // Format the text for colors
        return TextFormatter.format(foundString);
    }
    
    /**
     * This command happens before arguments are passed anywhere, so they need to be passed with the get
     * @param args
     */
    public String getVoteNotFound(ArrayList<String> args)
    {
        return getStringFromFile("vote.default.VoteNotFound", args);
    }
    
    public boolean getCheckPlayerIPAddress()
    {
        return plugin.getConfig().getBoolean("vote.default.CheckPlayerIPAddress");
    }
    
    public String getPlayerIPAlreadyVoted()
    {
        return getStringFromFile("vote.default.PlayerIPAlreadyVoted");
    }
    
    public List<String> getReloadCommands()
    {
        return plugin.getConfig().getStringList("vote.default.ReloadCommands");
    }
    
    public String getPlayerReloadNoPermission()
    {
        return getStringFromFile("vote.default.ReloadNoPermission");
    }
    
    public String getPlayerVetoNoPermission()
    {
        return getStringFromFile("vote.default.VoteVetoNoPermission");
    }
    
    public List<String> getVoteVetoCommands()
    {
        return plugin.getConfig().getStringList("vote.default.VoteVetoCommands");
    }
    
    public List<String> getVoteYesCommands()
    {
        return plugin.getConfig().getStringList("vote.default.VoteYesCommands");
    }
    
    public List<String> getVoteNoCommands()
    {
        return plugin.getConfig().getStringList("vote.default.VoteNoCommands");
    }
    
    public List<String> getVoteListCommands()
    {
        return plugin.getConfig().getStringList("vote.default.VoteListCommands");
    }
    
    public List<String> getVoteHelpCommands()
    {
        return plugin.getConfig().getStringList("vote.default.VoteHelpCommands");
    }
    
    public String getVoteStartText()
    {
        return getStringFromFile("vote.default.VoteStartText");
    }
    
    public String getVoteEndSuccessText()
    {
        return getStringFromFile("vote.default.VoteEndSuccessText");
    }
    
    public String getVoteEndFailText()
    {
        return getStringFromFile("vote.default.VoteEndFailText");
    }

    public String getVoteAlreadyInProgress()
    {
        return getStringFromFile("vote.default.VoteAlreadyInProgress");
    }
    
    public String getVoteCanceled()
    {
        return getStringFromFile("vote.default.VoteCanceled");
    }
    
    public String getNoVoteInProgress()
    {
        return getStringFromFile("vote.default.NoVoteInProgress");
    }
    
    public String getPlayerVoteCounted()
    {
        return getStringFromFile("vote.default.PlayerVoteCounted");
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

    public String getPlayerVoteNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteNoPermission");
    }
    
    public String getPlayerVoteStartNoPermission()
    {
        return getStringFromFile("vote.default.PlayerVoteStartNoPermission");
    }
    
    public String getPlayerUnbannable()
    {
        return getStringFromFile("vote.default.PlayerUnbannable");
    }
    
    public String getPlayerUnkickable()
    {
        return getStringFromFile("vote.default.PlayerUnkickable");
    }
    
    public String getPlayerVoteChanged()
    {
        return getStringFromFile("vote.default.PlayerVoteChanged");
    }
    
    public String getGeneralHelpNotFound()
    {
        return getStringFromFile("vote.help.GeneralHelpNotFound");
    }
    
    public String getVoteStartHelpNotFound()
    {
        return getStringFromFile("vote.help.VoteStartHelpNotFound");
    }
    
    public List<String> getGeneralCommandsHelp()
    {
        return plugin.getConfig().getStringList("vote.help.GeneralCommands");
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
    
    public void reload()
    {
        save();
        plugin.reloadConfig();
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