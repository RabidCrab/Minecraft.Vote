package me.RabidCrab.Vote;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import me.RabidCrab.Vote.Events.VoteCommandExecutor;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijikokun.bukkit.Permissions.Permissions;
 
/**
 * The big cheese
 * @author RabidCrab
 *
 */
public class Vote extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	
	private final VoteCommandExecutor commandExecutor = new VoteCommandExecutor(this);
	public static IPermissionHandler permissions;
	public static ConfigurationFile configuration;
	public final Voting voter = new Voting(this);
	private static PlayerWrapper playerCommandExecutor;
	
	public void onEnable()
	{
	    // Setup the configuration file
	    try
        {
            configuration = new ConfigurationFile(new File("plugins" + File.separator + "Vote" + File.separator + "Config.yml"));
        } 
	    catch (IOException e)
        {
            log.info(e.getMessage());
        }
	    
		// Hook onto Bukkit's command event
		this.getCommand("vote").setExecutor(commandExecutor);
		
		// Enable permissions
		setupPermissions();
		
		// Yay on the successful start
		log.info("Voter has been enabled.");
	}
	
	/**
	 * Clear out all the used resources and notify the log file and server the plugin has been disabled
	 */
	public void onDisable()
	{
		log.info("Voter has been disabled.");
	}
	
	public static PlayerWrapper getPlayerCommandExecutor()
	{
	    return playerCommandExecutor;
	}
	
	/**
	 * Setup permissions using http://forums.bukkit.org/threads/admn-dev-permissions-3-1-4-the-plugin-of-tomorrow-818.18430/
	 * I also added a setup to where it adds VoteExecutor as an op
	 */
    private void setupPermissions()
    {
        Plugin permissionsPlugin = null;
          
        try
        {
            permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        }
        catch (Exception e) {}
          
        // If there's no permissions found, create the fake permissions wrapper, otherwise load permissions
        if (Vote.permissions == null) {
            if (permissionsPlugin != null) {
                Vote.permissions = (IPermissionHandler)new PermissionHandlerWrapper(((Permissions)permissionsPlugin).getHandler());
            } else {
          	  log.info("Permission system not detected, defaulting to OP");
          	  Vote.permissions = (IPermissionHandler)new MockPermissionHandler();
            }
        }
         
        // Pull the first name from ops.txt and use them to call functions
        FileInputStream fileStream = null;
        
        try
        {
            fileStream = new FileInputStream("ops.txt");
        } catch (FileNotFoundException e)
        {
            log.severe("Cannot find the ops file!");
            return;
        }
        
        // Get the object of DataInputStream
        DataInputStream dataStream = new DataInputStream(fileStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataStream));
        String firstOp;
        
        try
        {
            firstOp = bufferedReader.readLine();
            playerCommandExecutor = new PlayerWrapper(firstOp);
        } 
        catch (IOException e)
        {
            log.severe("Ops file is corrupt!");
            return;
        }
        
        if (playerCommandExecutor == null)
            log.severe("Can't find an op to mimic!");
    }
}