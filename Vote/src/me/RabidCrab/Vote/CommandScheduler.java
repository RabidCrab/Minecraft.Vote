package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import me.RabidCrab.Vote.Exceptions.PlayerNotFoundException;
import me.RabidCrab.Vote.Timers.ExecuteCommandsTimer;

/**
 * Maintains the scheduling for CommandSets that are created after a successful/failed vote
 * @author Rabid
 *
 */
public class CommandScheduler
{
    private static List<CommandSet> commandSetList = new ArrayList<CommandSet>();
    // The timer is static because I only want it to run at maximum every few seconds. Any more often is a waste or resources
    // and can lag the server in very, very rare cases
    private static Timer commandsTimer = new Timer();
    
    /**
     * Schedule a CommandSet to be executed
     * @param commandSet CommandSet to execute
     * @param firstExecutionDelaySeconds The amount of seconds to delay before executing the commands
     */
    public static void scheduleCommandSet(CommandSet commandSet, long firstExecutionDelaySeconds)
    {
        // Now create a new Command to put in the list of commands to run, and start the command timer if it hasn't been started already
        commandSetList.add(commandSet);
        
        // Schedule the timer
        commandsTimer.schedule(new ExecuteCommandsTimer(), firstExecutionDelaySeconds * 1000);
    }
    
    /**
     * This executes any commands that a vote has requested to be run. It's designed like this because for player
     * consequences, they can just log out to avoid the ban or kick hammer. This will allow me to re-run commands if the player isn't there
     */
    public static void executeCommands()
    {
        List<CommandSet> commandsToRemove = new ArrayList<CommandSet>();
        
        // Go through the list of commands in each of the list of commands
        for (CommandSet command : commandSetList)
        {
            try
            {
                // Attempt to execute the commands. If they go fine, remove them from the execution list
                if (command.ExecuteCommands())
                    commandsToRemove.add(command);
            } 
            catch (PlayerNotFoundException e)
            {
                // Player not found probably means they're offline, so no need to worry about it
                //commandsToRemove.add(command);
            }
        }
        
        // Clear out the ones that ran successfully
        for (CommandSet command : commandsToRemove)
            commandSetList.remove(command);
        
        // Re-run it if there's still CommandSets to execute
        if (!commandSetList.isEmpty())
            commandsTimer.schedule(new ExecuteCommandsTimer(), 3000);
    }
    
    /**
     * Clear out all the data
     */
    public static void clearCommands()
    {
        commandSetList = new ArrayList<CommandSet>();
        commandsTimer = new Timer();
    }
}
