package me.RabidCrab.Vote;

import org.bukkit.entity.Player;

/**
 * These aren't the droids you are looking for.
 * @author Rabid
 *
 */
public class MockPermissionHandler implements IPermissionHandler
{
    /**
     * Default permissions are... of course all yes! And this is all I'm going to use
     */
    public boolean has(Player player, String doesntMatter)
    {
        return true;
    }
}
