package me.RabidCrab.Vote.Timers;

import java.util.TimerTask;

import me.RabidCrab.Vote.CommandScheduler;

/**
 * Used by me.RabidCrab.Vote.CommandScheduler to execute commands if the player attempted to dodge the punishment
 * @author RabidCrab
 * 
 */
public class ExecuteCommandsTimer extends TimerTask
{
    @Override
    public void run()
    {
        CommandScheduler.executeCommands();
    }
}
