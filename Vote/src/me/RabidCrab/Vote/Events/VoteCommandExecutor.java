package me.RabidCrab.Vote.Events;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import me.RabidCrab.Vote.CustomCommands;
import me.RabidCrab.Vote.Vote;
import me.RabidCrab.Vote.ActiveVote;
import me.RabidCrab.Vote.Common.Comparer;
import me.RabidCrab.Vote.Common.TextFormatter;

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
    				if (Comparer.containsIgnoreCase(Vote.configuration.getVoteNoCommands(), args[0]) && sender instanceof Player)
    				    ActiveVote.playerVoteNo(plugin, (Player)sender);
    				else
    				    if (Comparer.containsIgnoreCase(Vote.configuration.getVoteListCommands(), args[0]))
    				        displayVoteStartHelp(sender);
    				    else
    				        if (Comparer.containsIgnoreCase(Vote.configuration.getVoteHelpCommands(), args[0]))
    				            displayGeneralHelp(sender);
    				        else
    				            if (Comparer.containsIgnoreCase(Vote.configuration.getVoteVetoCommands(), args[0]))
    				                CancelActiveVote(sender);
    				            else
    				                if (Comparer.containsIgnoreCase(Vote.configuration.getReloadCommands(), args[0]))
                                        plugin.reload(sender);
                                    else
                                        startVote(sender, args);
			}
			else
			{
			    // I have a situation where I call my own commands. Currently it's only for weather, but it'll most likely
			    // be for other things as well
			    if (args.length > 0)
    			    if (args[0].equalsIgnoreCase("setvalue"))
    			        customCommands.setValue(sender, args[1].toString(), args);
    			    else
    			        startVote(sender, args);
			}
			
			return true;
		}
		
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

	private void startVote(CommandSender sender, String[] args)
	{
	    if (args.length < 1)
	        return;
	    
	    for (String s : Vote.configuration.getAllVoteTypes())
        {
            if (s.compareToIgnoreCase(args[0].toString()) == 0)
            {
                ArrayList<String> extraArgs = new ArrayList<String>();
                
                for (int i = 1; i < args.length; i++)
                    extraArgs.add(args[i]);
                
                ActiveVote.beginVote(plugin, sender, Vote.configuration.getPlayerVote(plugin, s), extraArgs);
                
                return;
            }
        }
	    
	    sender.sendMessage(args[0].toString() + " does not exist!");
	}
}