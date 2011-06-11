package me.RabidCrab.Vote;

import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

/**
 * Fracking wrappers. Too bad I can't use dynamic proxies.
 * @author Rabid
 */
public class PermissionHandlerWrapper
{
    private PermissionHandler permissionBase;
    
    public PermissionHandlerWrapper(PermissionHandler permission)
    {
        permissionBase = permission;
    }

    public boolean has(Player player, String permissionLevel)
    {
        return permissionBase.has(player, permissionLevel);
    }
}
