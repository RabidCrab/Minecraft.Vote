package me.RabidCrab.Vote.Timers;

import java.util.TimerTask;

import me.RabidCrab.Vote.Voting;

/**
 * Used by me.RabidCrab.Vote.Voting to execute the timeout of a active vote after a set time
 * @author RabidCrab
 *
 */
public class VoteFinishedTimer extends TimerTask
{
    private Voting caller;
    
    public VoteFinishedTimer(Voting caller)
    {
        this.caller = caller;
    }
    
    @Override
    public void run()
    {
        caller.voteTimeOver();
    }
    
}
