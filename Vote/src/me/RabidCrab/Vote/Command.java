package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

/**
 * I use this to store commands for the vote plugin to use later. There's a situation where players need to be verified to be online,
 * so I can't just execute the commands and not check and see if they're online
 * @author Rabid
 *
 */
public class Command
{
    private List<String> commands;
    private CommandSender sender;
    private ArrayList<String> arguments;
    
    public Command(List<String> commands, CommandSender commandSender, ArrayList<String> arguments)
    {
        this.commands = commands;
        this.sender = commandSender;
        this.arguments = arguments;
    }
    
    public List<String> getCommands()
    {
        return commands;
    }
    
    public CommandSender getSender()
    {
        return sender;
    }
    
    public ArrayList<String> getArguments()
    {
        return arguments;
    }
}
