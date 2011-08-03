package me.RabidCrab.Vote.Events;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import me.RabidCrab.Vote.CustomCommands;
import me.RabidCrab.Vote.Vote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Upon a player command, figure out what they want
 * @author RabidCrab
 *
 */
public class VoteCommandExecutor implements CommandExecutor {

	public static Vote plugin;
	public static List<LivingEntity> entities;
	public CustomCommands customCommands;
	
	public VoteCommandExecutor(Vote instance) {
		plugin = instance;
		customCommands = new CustomCommands(instance);
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(label.equalsIgnoreCase("vote") && sender != null && args != null)
		{
			// Get the player, we'll need it
			Player player = (Player)sender;
			
			if (args.length < 1)
			    displayGeneralHelp(player);
			
			// This really should be turned into a case statement
			if (args.length == 1)
			{
    			if ((args[0].equalsIgnoreCase("y") || args[0].equalsIgnoreCase("yes")))
    			    plugin.voter.playerVoteYes(player);
    			else
    				if ((args[0].equalsIgnoreCase("n") || args[0].equalsIgnoreCase("no")))
				        plugin.voter.playerVoteNo(player);
    				else
    				    if (args[0].equalsIgnoreCase("list"))
    				        displayVoteStartHelp(player);
    				    else
    				        startVote((Player)sender, args);
			}
			else
			{
			    // I have a situation where I call my own commands. Currently it's only for weather, but it'll most likely
			    // be for other things as well
			    if (args.length > 0)
    			    if (args[0].equalsIgnoreCase("setvalue"))
    			        customCommands.setValue((Player)sender, args[1].toString(), args);
    			    else
    			        startVote((Player)sender, args);
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Display the general help to a specific player
	 */
	private void displayGeneralHelp(Player player)
	{
	    List<String> helpList = Vote.configuration.getGeneralCommandsHelp();

        if (helpList.size() > 0)
    	    for (String helpText : Vote.configuration.getGeneralCommandsHelp())
        	    player.sendMessage(helpText);
        else
            player.sendMessage(Vote.configuration.getGeneralHelpNotFound()); 
	}
	
	/**
	 * Display all of the start vote options available to the player
	 */
	private void displayVoteStartHelp(Player player)
	{
	    int listWritten = 0;
	    List<SimpleEntry<String,String>> list = Vote.configuration.getVotesListAndDescription();
	    
	    // If there's a list, go through it and only show the ones the player has permission to start
	    if (list.size() > 0)
    	    for (SimpleEntry<String,String> entry : Vote.configuration.getVotesListAndDescription())
    	        if (Vote.permissions.has(player, "vote.startvote." + entry.getKey()))
    	        {
    	            player.sendMessage(entry.getKey() + " - " + entry.getValue());
    	            listWritten++;
    	        }
	    
	    if (listWritten == 0)
	        player.sendMessage(Vote.configuration.getVoteStartHelpNotFound()); 
	}

	private void startVote(Player player, String[] args)
	{
	    if (args.length < 1)
	        return;
	    
	    for (String s : Vote.configuration.getAllVoteTypes())
        {
            if (s.compareToIgnoreCase(args[0].toString()) == 0)
            {
                String[] extraArgs = new String[args.length - 1];
                
                for (int i = 1; i < args.length; i++)
                    extraArgs[i - 1] = args[i];
                
                plugin.voter.beginVote(player, Vote.configuration.getPlayerVote(plugin, s), extraArgs);
                return;
            }
        }
	    
	    player.sendMessage(args[0].toString() + " does not exist!");
	}
}