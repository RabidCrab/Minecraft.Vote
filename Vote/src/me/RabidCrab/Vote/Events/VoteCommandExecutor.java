package me.RabidCrab.Vote.Events;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
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
	
	public VoteCommandExecutor(Vote instance) {
		plugin = instance;
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("vote") && sender != null){
			// Get the player, we'll need it
			Player player = (Player)sender;
			
			if (args.length < 1)
			    displayGeneralHelp(player);
			
			if (args.length == 1)
			{
    			// If they voted yes, and they can, then we need to iterate the current yes vote by 1
    			if ((args[0].equalsIgnoreCase("y") || args[0].equalsIgnoreCase("yes")))
    			{
    			    if (Vote.permissions == null)
    			        plugin.voter.playerVoteYes(player);
    			    else
        				if (Vote.permissions.has(player, "vote.voteyes"))
        					plugin.voter.playerVoteYes(player);
        				else
        				    player.sendMessage("You do not have permission to vote");
    			}
    			else
    			{
    				if ((args[0].equalsIgnoreCase("n") || args[0].equalsIgnoreCase("no")))
    				{
    				    if (Vote.permissions == null)
    				        plugin.voter.playerVoteNo(player);
    				    else
        					if (Vote.permissions.has(player, "vote.voteno") || Vote.permissions == null)
        					    plugin.voter.playerVoteNo(player);
        					else
        					    player.sendMessage("You do not have permission to vote");
    				}
    				else
    				{
    				    if (args[0].equalsIgnoreCase("start"))
    				        displayVoteStartHelp(player);
    				    else
    				        displayGeneralHelp(player);
    				}
    			}
			}
			
			// Vote starting begins here
			if (args.length >= 2 && args[0].equalsIgnoreCase("start"))
			{
			    if (args[1].equalsIgnoreCase("?"))
			    {
			        displayVoteStartHelp(player);
			        return true;
			    }
			        
			    if (Vote.permissions == null)
			        startVote(sender, args);
			    else
    				if (Vote.permissions.has(player, "votes.startvote." + args[1].toString().toLowerCase()))
    				    startVote(sender, args);
    				else
    				    player.sendMessage("You do not have permission to start a vote");
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
	    List<SimpleEntry<String,String>> list = Vote.configuration.getVotesListAndDescription();
	    
	    if (list.size() > 0)
    	    for (SimpleEntry<String,String> entry : Vote.configuration.getVotesListAndDescription())
                player.sendMessage(entry.getKey() + " - " + entry.getValue());
	    else
	        player.sendMessage(Vote.configuration.getVoteStartHelpNotFound()); 
	}

	private void startVote(CommandSender sender, String[] args)
	{
	    for (String s : Vote.configuration.getAllVoteTypes())
        {
            if (s.compareToIgnoreCase(args[1].toString()) == 0)
                plugin.voter.beginVote((Player)sender, Vote.configuration.getPlayerVote(plugin, s));
            else
                ((Player)sender).sendMessage(args[1].toString() + " does not exist!");
        }
	}
}