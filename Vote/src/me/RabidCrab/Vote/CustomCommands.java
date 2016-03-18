package me.RabidCrab.Vote;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Contains custom commands such as kicking/banning a player and changing the weather so that I don't have to rely on other plugins
 * @author Rabid
 *
 */
public class CustomCommands
{
    private static Vote plugin;
    
    public CustomCommands(Vote instance)
    {
        plugin = instance;
    }
    
    /**
     * I knew I'd have to set my own custom commands to be executed at some point in time. Well, here it is
     */
    public void setValue(CommandSender sender, String commandName, String[] args)
    {
        if (commandName.equalsIgnoreCase("sun"))
            if (sender instanceof Player)
                setWeatherStorm((Player)sender, false);
            else 
                plugin.getServer().getWorld("world").setStorm(false);
        else
            if (commandName.equalsIgnoreCase("rain"))
                if (sender instanceof Player)
                    setWeatherStorm((Player)sender, true);
                else 
                    plugin.getServer().getWorld("world").setStorm(true);
            else
                if (commandName.equalsIgnoreCase("kick"))
                    kickPlayer(sender, args[2]);
                else
                    if (commandName.equalsIgnoreCase("ban"))
                        banPlayer(sender, args[2]);
                    else
                        if (commandName.equalsIgnoreCase("time"))
                            if (sender instanceof Player)
                                ((Player)sender).getWorld().setTime(Long.parseLong(args[2]));
                            else 
                                plugin.getServer().getWorld("world").setTime(Long.parseLong(args[2]));
    }
    
    /**
     * I bet you could guess what this does
     */
    private void setWeatherStorm(Player player, boolean weather)
    {
        // It's funny how my permission handler is going to trick this command when executed via a vote.
        // Don't worry, it's by design
        if (Vote.permissions.has(player, "vote.setvalue"))
            player.getWorld().setStorm(weather);
        else
            player.sendMessage(Vote.configuration.getPlayerSetValueNoPermission());
    }
    
    /**
     * Delios posted a request that there be permissions that make users immune to getting kicked/banned. This is the most elegant
     * solution I can come up with
     */
    private void kickPlayer(CommandSender sender, String targetKick)
    {
        Player target = null;
        
        try
        {
            target = plugin.getServer().getPlayer(targetKick);

            if (Vote.permissions.has(sender, "vote.setvalue"))
                if (!Vote.permissions.has(target, "vote.unkickable"))
                    plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), "kick " + targetKick);
        }
        catch (Exception e) {}
    }
    
    /**
     * Delios posted a request that there be permissions that make users immune to getting kicked/banned. This is the most elegant
     * solution I can come up with
     */
    private void banPlayer(CommandSender sender, String targetBan)
    {
        Player target = null;
                
        try
        {
            target = plugin.getServer().getPlayer(targetBan);
            
            if (Vote.permissions.has(sender, "vote.setvalue"))
                if (!Vote.permissions.has(target, "vote.unbannable"))
                    plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), "ban " + targetBan);
        }
        catch (Exception e) {}
    }
}
