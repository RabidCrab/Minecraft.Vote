package me.RabidCrab.Vote.Timers;

import java.util.TimerTask;

import me.RabidCrab.Vote.Voting;

/**
 * Used by me.RabidCrab.Vote.Voting to execute commands if the player attempted to dodge the punishment
 * @author RabidCrab
 */
public class ExecuteCommandsTimer extends TimerTask
{
    private Voting caller;
    
    public ExecuteCommandsTimer(Voting caller)
    {
        this.caller = caller;
    }
    
    @Override
    public void run()
    {
        caller.executeCommands();
    }
}
