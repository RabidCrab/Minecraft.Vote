package me.RabidCrab.Vote;

import org.bukkit.entity.Player;

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
    public void setValue(Player player, String commandName, String[] args)
    {
        if (commandName.equalsIgnoreCase("sun"))
            setWeatherStorm(player, false);
        else
            if (commandName.equalsIgnoreCase("rain"))
                setWeatherStorm(player, true);
            else
                if (commandName.equalsIgnoreCase("kick"))
                    kickPlayer(player, args[2]);
                else
                    if (commandName.equalsIgnoreCase("ban"))
                        banPlayer(player, args[2]);
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
    private void kickPlayer(Player player, String targetKick)
    {
        Player target = null;
        
        try
        {
            target = plugin.getServer().getPlayer(targetKick);

            if (Vote.permissions.has(player, "vote.setvalue"))
                if (!Vote.permissions.has(target, "vote.unkickable"))
                    plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), "kick " + targetKick);
        }
        catch (Exception e) {}
    }
    
    /**
     * Delios posted a request that there be permissions that make users immune to getting kicked/banned. This is the most elegant
     * solution I can come up with
     */
    private void banPlayer(Player player, String targetBan)
    {
        Player target = null;
                
        try
        {
            target = plugin.getServer().getPlayer(targetBan);
            
            if (Vote.permissions.has(player, "vote.setvalue"))
                if (!Vote.permissions.has(target, "vote.unbannable"))
                    plugin.getServer().dispatchCommand(Vote.getPlayerCommandExecutor(), "ban " + targetBan);
        }
        catch (Exception e) {}
    }
}
