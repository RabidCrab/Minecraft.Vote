package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.RabidCrab.Vote.Exceptions.PlayerNotFoundException;
import me.RabidCrab.Vote.Exceptions.PlayerNotOnlineException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A set of commands to be executed on a plugin
 * @author Rabid
 *
 */
public class CommandSet
{
    private List<String> commands;
    private ArrayList<String> arguments;
    private CommandSender voteStarter;
    private boolean isConsoleCommand;
    private int failedExecutionAttempts = 0;
    private PlayerWrapper playerWrapper = new PlayerWrapper(Vote.getPlayerCommandExecutor().getName());
    private Plugin plugin;
    
    /**
     * Create a new command set
     * @param voteStarter The starter of the vote. It can be either a player or console. To handle this possibility, I have it casted as a CommandSender instead of Player
     * @param commands List of commands
     * @param arguments The arguments for the set of commands
     */
    public CommandSet(Plugin plugin, CommandSender voteStarter, List<String> commands, ArrayList<String> arguments, boolean isConsoleCommand)
    {
        this.plugin = plugin;
        this.commands = commands;
        this.arguments = arguments;
        this.voteStarter = voteStarter;
        this.isConsoleCommand = isConsoleCommand;
        
        // Create the player mimic. Hopefully we don't need to use it, but if we do it'll be ready to be used
        if (voteStarter instanceof Player)
            playerWrapper.setCaller((Player)voteStarter);
    }
    
    public List<String> getCommands()
    {
        return commands;
    }
    
    public ArrayList<String> getArguments()
    {
        return arguments;
    }
    
    public CommandSender getVoteStarter()
    {
        return voteStarter;
    }
    
    /**
     * @return The number of times the commands failed to execute
     */
    public int getFailedExecutionAttempts()
    {
        return failedExecutionAttempts;
    }
    
    /**
     * Attempt to execute the commands
     * @return False if any of the commands fails to execute, including if a player simply isn't online
     */
    public boolean ExecuteCommands() throws PlayerNotFoundException
    {
        // Loop over all the commands to look for a VERIFYPLAYERONLINE
        for (String command : commands)
        {
            try
            {
                // Format the command to be executable. If there's a failure to deal with players not existing and whatnot, throw it here
                final String formattedCommand = CommandParser.ParseCommand(plugin, command, arguments);
                
                // If it contains DELAY, we need to sleep the thread for a few seconds, then continue.
                if (command.contains("DELAY"))
                {
                    // Get the name from the verify player online command line
                    String argumentNumber = formattedCommand.replaceAll("DELAY", "").trim();
                    
                    try
                    {
                        Thread.sleep(Long.parseLong(argumentNumber) * 1000);
                    } catch (NumberFormatException e)
                    { 
                        plugin.getLogger().log(Level.SEVERE, "Cannot parse DELAY " + argumentNumber); 
                        return true; 
                    } 
                    catch (InterruptedException e) { return false; }
                }
                    
                // If the console started the vote, there's no way we can pass the console as the sender. Most plugins explode if I do this
                // so in order to account for their poor coding practices, I create notch as a player and have him run the commands
                if (formattedCommand != null)
                {
                    // Make sure the command is executed on the primary thread, otherwise problems could potentially happen
                    Bukkit.getScheduler().runTask(plugin, new Runnable() 
                    {
                        public void run() 
                        {
                            if (isConsoleCommand(formattedCommand) || !(voteStarter instanceof Player))
                                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), formattedCommand);
                            else
                                plugin.getServer().dispatchCommand(playerWrapper, formattedCommand);
                        }
                    });
                }
            } 
            catch (PlayerNotOnlineException e)
            {
                // If the player's not online, just iterate the failed attempts and move along
                failedExecutionAttempts++;
                return false;
            }
        }

        return true;
    }
    
    /**
     * If something is a console command, it gets executed differently from a player command
     */
    private boolean isConsoleCommand(String command)
    {
        if (command.equalsIgnoreCase("kickall") 
                || command.equalsIgnoreCase("stop") 
                || command.equalsIgnoreCase("save-all")
                || isConsoleCommand)
            return true;
        
        return false;
    }
    
    /**
     * Before a vote starts, the commands to run need to be validated. This is done by mock executing the commands
     * @return True if successful
     */
    public ArrayList<String> ValidateArguments() throws PlayerNotFoundException
    {
        for (String command : commands)
        {
            try
            {
                CommandParser.ParseCommand(plugin, command, arguments);
            } 
            // We don't care if a player isn't online, only if it's not found
            catch (PlayerNotOnlineException e) { }
        }
        
        arguments = CommandParser.ParseArguments(plugin, commands, arguments);
        
        return arguments;
    }
}
