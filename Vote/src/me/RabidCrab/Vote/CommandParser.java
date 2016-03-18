package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.List;

import me.RabidCrab.Vote.Exceptions.PlayerNotFoundException;
import me.RabidCrab.Vote.Exceptions.PlayerNotOnlineException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Parses commands with various conditions that need to be accounted for
 * @author Rabid
 *
 */
public class CommandParser
{
    /**
     * Parse an unformatted command into a executable string
     * @param plugin Plugin
     * @param command Unformatted command to parse
     * @param arguments The potential arguments to replace parameters with
     * @return A directly executable command, or null if not executable
     */
    @SuppressWarnings("deprecation")
    public static String ParseCommand(Plugin plugin, String command, ArrayList<String> arguments) throws PlayerNotOnlineException, PlayerNotFoundException
    {
        // Loop through all of the arguments and add them to the command if it exists
        for (int i = 0; i < arguments.size(); i++)
            command = command.replaceAll("\\[\\%" + i + "\\]", arguments.get(i));
        
        // We need to check and see if there's any VERIFYPLAYERONLINE. If there is, we need to verify the player(s) are online before
        // we continue
        if (command.contains("VERIFYPLAYERONLINE"))
        {
            boolean playerFound = false;
            // Get the name from the verify player online command line
            String playerTextName = command.replaceAll("VERIFYPLAYERONLINE", "").trim();
            
            // Loop over all the online players to see if the target is online
            for (Player playerName : plugin.getServer().getOnlinePlayers())
            {
                // If they're online, set the verified to true so that the commands can run
                if (playerName.getName().equalsIgnoreCase(playerTextName))
                {
                    playerFound = true;
                    break;
                }
            }
            
            // If we couldn't find the player, it failed
            if (!playerFound)
                throw new PlayerNotOnlineException(playerTextName);
            
            return null;
        }
        
        // If it's a findplayer search, attempt to find the player
        if (command.contains("FINDPLAYER"))
        {
            // Get the name from the verify player online command line
            String playerTextName = command.replaceAll("FINDPLAYER", "").trim();
            
            // We still have to go by the player string name because that's what people use to target players. This functionality will remain, although
            // it is deprecated
            if (plugin.getServer().getPlayer(playerTextName) == null)
                throw new PlayerNotFoundException(playerTextName);
            
            return null;
        }

        // We made it! return the parsed command
        return command;
    }
    
    /**
     * Sometimes we need to expand a partial name. This is designed to fill the names and return a full set of arguments
     * @return
     */
    @SuppressWarnings("deprecation")
    public static ArrayList<String> ParseArguments(Plugin plugin, List<String> commands, ArrayList<String> arguments) throws PlayerNotFoundException
    {
        // Loop through all of the arguments and add them to the command if it exists
        for (int i = 0; i < arguments.size(); i++)
            for (String command : commands)
                command = command.replaceAll("\\[\\%" + i + "\\]", arguments.get(i));
        
        for (String command : commands)
        {
            // If it's a findplayer search, attempt to find the player
            if (command.contains("FINDPLAYER"))
            {
                // Get the name from the verify player online command line
                String argumentNumber = command.replaceAll("FINDPLAYER", "").trim();

                // Now we gotta find the specific player. If we can't, throw an exception, otherwise change the argument
                for (int i = 0; i < arguments.size(); i++)
                {
                    // If it's the right argument number, find the player
                    if (argumentNumber.contains("[%" + i + "]"))
                    {
                        // We still have to go by the player string name because that's what people use to target players. This functionality will remain, although
                        // it is deprecated
                        Player foundPlayer = plugin.getServer().getPlayer(arguments.get(i));
                        
                        if (foundPlayer == null)
                            throw new PlayerNotFoundException(arguments.get(i));
                        else 
                            arguments.set(i, foundPlayer.getName());
                    }   
                }
            }
        }
        
        return arguments;
    }
}
