package me.RabidCrab.Vote;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Wrapping the permissions handler so that I don't have to check if permissions actually exist every time I want to check them
 * @author Rabid
 * 
 */
public interface IPermissionHandler
{
    public boolean has(Player player, String doesntMatter);
    public boolean has(CommandSender sender, String doesntMatter);
}
