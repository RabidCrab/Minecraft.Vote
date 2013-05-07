package me.RabidCrab.Vote.Events;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.RabidCrab.Vote.CustomCommands;
import me.RabidCrab.Vote.Vote;
import me.RabidCrab.Vote.ActiveVote;
import me.RabidCrab.Vote.Common.Comparer;
import me.RabidCrab.Vote.Common.TextFormatter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import advancedafk.AFK_API;

/**
 * Upon a player command, figure out what they want
 * @author RabidCrab
 *
 */
public class VoteCommandExecutor implements CommandExecutor, Listener 
{
    public static Vote plugin;
    public static List<LivingEntity> entities;
    public CustomCommands customCommands;
	
	public VoteCommandExecutor(Vote instance) 
	{
		plugin = instance;
		customCommands = new CustomCommands(instance);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("vote") && sender != null && args != null)
		{
			if (args.length < 1)
			    displayGeneralHelp(sender);
			
			// This really should be turned into a case statement
			if (args.length == 1)
			{
			    // Only players can vote, so make sure they're a player, and check their input against a list of possible choices
    			if (Comparer.containsIgnoreCase(Vote.configuration.getVoteYesCommands(), args[0]) && sender instanceof Player)
    			    ActiveVote.playerVoteYes(plugin, (Player)sender);
    			else
    			    // Find out if it's a no vote
    				if (Comparer.containsIgnoreCase(Vote.configuration.getVoteNoCommands(), args[0]) && sender instanceof Player)
    				    ActiveVote.playerVoteNo(plugin, (Player)sender);
    				else
    				    // Maybe they're looking for the list of possible votes they can start
    				    if (Comparer.containsIgnoreCase(Vote.configuration.getVoteListCommands(), args[0]))
    				        displayVoteStartHelp(sender);
    				    else
    				        // Or they want to see all the vote commands possible
    				        if (Comparer.containsIgnoreCase(Vote.configuration.getVoteHelpCommands(), args[0]))
    				            displayGeneralHelp(sender);
    				        else
    				            // Or an admin just wants to veto a vote
    				            if (Comparer.containsIgnoreCase(Vote.configuration.getVoteVetoCommands(), args[0]))
    				                CancelActiveVote(sender);
    				            else
    				                // Or reload the server
    				                if (Comparer.containsIgnoreCase(Vote.configuration.getReloadCommands(), args[0]))
                                        plugin.reload(sender);
                                    else
                                        // But if they don't want to do any of those, then it must be a request to start a vote
                                        startVote(sender, args);
			}
			else
			{
			    /* AdvancedAFK */
                // I have a situation where I call my own commands. Currently it's only for weather, but it'll most likely
                // be for other things as well
                if (args.length > 0)
                    if(sender instanceof Player && args.length == 3 && args[0].equalsIgnoreCase("auto")){
                        autoVote((Player) sender,args[1],args[2]);
                    }else if(sender instanceof Player && args.length == 2 && args[0].equalsIgnoreCase("autoyes")){
                        autoVote((Player) sender,args[1],"yes");
                    }else if(sender instanceof Player && args.length == 2 && args[0].equalsIgnoreCase("autono")){
                        autoVote((Player) sender,args[1],"no");
                    }else if (args[0].equalsIgnoreCase("setvalue"))
                        customCommands.setValue(sender, args[1].toString(), args);
                    else
                        startVote(sender, args);
            }
			
			// Return true if we handled it
			return true;
		}
		
		// Otherwise let it cascade past our plugin since it isn't something we can use
		return false;
	}
	
	/**
	 * Cancel a vote. It's the same as vetoing
	 */
	private void CancelActiveVote(CommandSender sender)
	{
	    ActiveVote.cancelVote(sender);
	}
	
	/**
	 * Display the general help to a specific player
	 */
	private void displayGeneralHelp(CommandSender sender)
	{
	    List<String> helpList = Vote.configuration.getGeneralCommandsHelp();

        if (helpList.size() > 0)
    	    for (String helpText : Vote.configuration.getGeneralCommandsHelp())
    	        sender.sendMessage(TextFormatter.format(helpText));
        else
            sender.sendMessage(Vote.configuration.getGeneralHelpNotFound()); 
	}
	
	/**
	 * Display all of the start vote options available to the player
	 */
	private void displayVoteStartHelp(CommandSender sender)
	{
	    int listWritten = 0;
	    List<SimpleEntry<String,String>> list = Vote.configuration.getVotesListAndDescription();
	    
	    // If there's a list, go through it and only show the ones the player has permission to start
	    if (list.size() > 0)
    	    for (SimpleEntry<String,String> entry : Vote.configuration.getVotesListAndDescription())
    	        if (Vote.permissions.has(sender, "vote.startvote." + entry.getKey()))
    	        {
    	            // The colors need to be specified for the key because you can't put a color code in the key
    	            sender.sendMessage(TextFormatter.format("&6" + entry.getKey() + " &A- " + entry.getValue()));
    	            listWritten++;
    	        }
	    
	    if (listWritten == 0)
	        sender.sendMessage(Vote.configuration.getVoteStartHelpNotFound()); 
	}

	/**
	 * Attempt to start a new vote
	 * @param sender Vote starter
	 * @param args The arguments passed through the vote
	 */
	private void startVote(CommandSender sender, String[] args)
	{
	    // There's always at least 1 argument. That would be the starter of the vote
	    if (args.length < 1)
	        return;
	    
	    // Loop through the list of potential votes
	    for (String s : Vote.configuration.getAllVoteTypes())
        {
	        // Try and find a match
            if (s.compareToIgnoreCase(args[0].toString()) == 0)
            {
                // Build out the arguments list
                ArrayList<String> extraArgs = new ArrayList<String>();
                
                // Add the arguments to the new list, while passing over the first one, which is the name of the vote
                for (int i = 1; i < args.length; i++)
                    extraArgs.add(args[i]);
                
                // and pass those arguments along with the others to start the vote
                ActiveVote.beginVote(plugin, sender, Vote.configuration.getPlayerVote(plugin, s), extraArgs);
                
              //Vote started, vote for AFK players
                try{
                    for(int i=0;i<Bukkit.getServer().getOnlinePlayers().length;i++){
                        Player p = Bukkit.getServer().getOnlinePlayers()[i];
                        
                        int result = autovotes.get(p).vote(p,Vote.configuration.getPlayerVote(plugin, s));
                        if(result == 1){
                            p.sendMessage("[Vote] I did vote YES for you");
                        }else if(result == 2){
                            p.sendMessage("[Vote] I did vote NO for you");
                        }else{
                            //Did not Vote yet
                            if(AFK_API.isAfk(p)){
                                p.chat("/vote yes");
                            }else if(AFK_API.isInInventory(p)){
                                p.chat("/vote yes");
                            }
                        }
                    }
                }catch(NoClassDefFoundError NCDFE){
                    //AdvancedAFK not installed
                }catch(NullPointerException NPE){
                    //AdvancedAFK not installed, or player are null
                }
                
                return;
            }
        }
	    
	    // It failed to find a match, but we need the arguments, including the failed vote name
        ArrayList<String> extraArgs = new ArrayList<String>();
        
        // Add the arguments to the new list
        for (int i = 0; i < args.length; i++)
            extraArgs.add(args[i]);
	    
	    // If we didn't find any, complain to the user
	    sender.sendMessage(Vote.configuration.getVoteNotFound(extraArgs));
	}
	
	/* Auto-AFK functions below */
	private Map<Player,AutoVote> autovotes = new HashMap<Player,AutoVote>();
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        addAutoVoter(e.getPlayer());
        loadAutoVotes(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        removeAutoVoter(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerLeave2(PlayerKickEvent e){
        removeAutoVoter(e.getPlayer());
    }
    
	public void addAutoVoter(Player p){
        autovotes.put(p,new AutoVote());
    }
    
    public void removeAutoVoter(Player p){
        autovotes.remove(p);
    }
    
    private void autoVote(Player p, String vote, String type){
        for (String s : Vote.configuration.getAllVoteTypes()){
            if (s.compareToIgnoreCase(vote.toString()) == 0){
                int result = autovotes.get(p).setAuto(Vote.configuration.getPlayerVote(plugin, s), type);
                if(result == 0){
                    p.sendMessage("[Vote] Error no such vote: " + vote);
                }else if(result == 1){
                    saveAutoVote(p,vote,type);
                    p.sendMessage("[Vote] AutoVote saved: " + type + " for " + vote);                   
                }else if(result == 2){
                    p.sendMessage("[Vote] AutoVote removed for " + vote);
                }else if(result == 3){
                    p.sendMessage("[Vote] This vote has been activated before: " + type + " for " + vote);
                }else{
                    p.sendMessage("[Vote] There was no such vote to deactivate: " + type + " for " + vote);
                }
            }
        }
    }
    
    private List<String> getAllAutoVotes(Player p){
        List<String> votes = new ArrayList<String>();
        AutoVote avotes = autovotes.get(p);
        votes.addAll(avotes.getYesVotes());
        votes.addAll(avotes.getNoVotes());
        return votes;
    }
    
    private void saveAutoVote(Player p, String vote, String type){
        ConfigurationSection CS = Vote.autovoteConfig.getConfig().getConfigurationSection("player");
        if(CS != null){
            Vote.autovoteConfig.getConfig().set("player." + p.getName() + ".votes", getAllAutoVotes(p));
            Vote.autovoteConfig.saveConfig();
        }else{
            plugin.getServer().getLogger().info("Config Error, autovote");
        }
    }
    
    private void loadAutoVotes(Player p){
        ConfigurationSection CS = Vote.autovoteConfig.getConfig().getConfigurationSection("player");
        if(CS != null){
            List<String> voteList = Vote.autovoteConfig.getConfig().getStringList("player." + p.getName() + ".votes");
            if(voteList == null || voteList.size() == 0){
                return;
            }
            Iterator<String> itVes = voteList.iterator();
            while(itVes.hasNext()){
                String line = itVes.next();
                String vote = line.split("/")[0];
                String type = line.split("/")[1];
                autoVote(p,vote,type);
            }
        }else{
            plugin.getServer().getLogger().info("Config Error, autovote");
        }
    }
}