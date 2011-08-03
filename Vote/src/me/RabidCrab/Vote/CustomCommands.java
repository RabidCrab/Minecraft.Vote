package me.RabidCrab.Vote;

import org.bukkit.entity.Player;

public class CustomCommands
{
    /**
     * I knew I'd have to set my own custom commands to be executed at some point in time. Well, here it is
     */
    public void setValue(Player player, String commandName)
    {
        if (commandName.equalsIgnoreCase("sun"))
            setWeatherStorm(player, false);
        else
            if (commandName.equalsIgnoreCase("rain"))
                setWeatherStorm(player, true);
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
}
