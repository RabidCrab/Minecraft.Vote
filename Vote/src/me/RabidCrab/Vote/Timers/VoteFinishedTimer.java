package me.RabidCrab.Vote.Timers;

import org.bukkit.plugin.Plugin;

import me.RabidCrab.Vote.ActiveVote;

/**
 * Used by me.RabidCrab.Vote.Voting to execute the timeout of a active vote after a set time
 * @author RabidCrab
 *
 */
public class VoteFinishedTimer implements Runnable
{
    Plugin plugin;
    
    public VoteFinishedTimer(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public void run()
    {
        ActiveVote.voteTimeOver(plugin);
    }
}
