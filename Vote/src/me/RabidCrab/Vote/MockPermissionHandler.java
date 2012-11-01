package me.RabidCrab.Vote;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * These aren't the droids you are looking for.
 * @author Rabid
 *
 */
public class MockPermissionHandler implements IPermissionHandler
{
    /**
     * Default permissions are handled by Bukkit
     */
    public boolean has(Player player, String permission)
    {
        return player.hasPermission(permission);
    }
    
    /**
     * Default permissions are handled by Bukkit
     */
    public boolean has(CommandSender sender, String permission)
    {
        return sender.hasPermission(permission);
    }
}
