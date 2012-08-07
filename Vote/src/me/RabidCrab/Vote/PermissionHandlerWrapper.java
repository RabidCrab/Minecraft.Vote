package me.RabidCrab.Vote;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;

/**
 * Fracking wrappers. Too bad I can't use dynamic proxies.
 * @author Rabid
 */
public class PermissionHandlerWrapper implements IPermissionHandler
{
    private Permission permissionBase;
    
    public PermissionHandlerWrapper(Permission permission)
    {
        permissionBase = permission;
    }

    public boolean has(Player player, String permissionLevel)
    {
        return permissionBase.has(player, permissionLevel);
    }
}
