package me.RabidCrab.Vote;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import me.RabidCrab.Vote.Events.VoteCommandExecutor;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import advancedafk.AFK_API;
 
/**
 * The big cheese
 * @author RabidCrab
 *
 */
public class Vote extends JavaPlugin {
	Logger log = Logger.getLogger("Vote");
	
	private final VoteCommandExecutor commandExecutor = new VoteCommandExecutor(this);
	public static IPermissionHandler permissions;
	public static DefaultConfigurationFile configuration;
	private static PlayerWrapper playerCommandExecutor;
	
	/* AdvancedAFK */
	public static ConfigAccessor autovoteConfig = null;
	
	public void onEnable()
	{
	    // Set Notch as an op for situations where I need to emulate an op
        this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "op notch");
        
        // I wasn't expecting to need to pass arguments to the file, but here's my workaround without telling
        // ConfigurationFile that anything exists except itself and the file.
        configuration = new DefaultConfigurationFile(this, new Callable<ArrayList<String>>() 
                                                            {
                                                                public ArrayList<String> call() 
                                                                {
                                                                    return ActiveVote.getArguments();
                                                                }
                                                            });
        
        /* AdvancedAFK */
        autovoteConfig = new ConfigAccessor(this, "autovotes.yml");
        //If you update your config, this applies changes to users config
        autovoteConfig.getConfig().options().copyDefaults(true);
        autovoteConfig.saveConfig();
        
        //Hook EventListener
        getServer().getPluginManager().registerEvents(commandExecutor, this);
        
        // Hook onto Bukkit's command event
        this.getCommand("vote").setExecutor(commandExecutor);
        
        // Enable permissions
        setupPermissions();
        
        //Try AdvancedAFK hook
        try{
            if(AFK_API.isInstalled()){
                log.info("[Vote] AdvancedAFK found, automatic vote for afk-player");
            }
        }catch(NoClassDefFoundError NCDFE){
            //AdvancedAFK not installed
        }catch(NullPointerException NPE){
            //AdvancedAFK not installed
        }
        
        // Yay on the successful start
        log.info("[Vote] has been enabled.");
	}
	
	/**
	 * Clear out all the used resources and notify the log file and server the plugin has been disabled
	 */
	public void onDisable()
	{
		log.info("[Vote] has been disabled.");
	}
	
	/**
	 * Clear out all the data to reload the plugin
	 */
	public void reload(CommandSender sender)
	{
	    if (permissions.has(sender, "vote.reload"))
	    {
    	    // Clearing out...
    	    configuration.reload();
    	    CommandScheduler.clearCommands();
    	    if (ActiveVote.isVoting())
    	        ActiveVote.cancelVote(this.getServer().getConsoleSender());
    	    
    	    // Re-enabling...
    	    onEnable();
    	    log.info("[Vote] Reloaded.");
	    }
	    else 
	        sender.sendMessage(configuration.getPlayerReloadNoPermission());
	}
	
	/**
	 * Setup permissions using http://forums.bukkit.org/threads/admn-dev-permissions-3-1-4-the-plugin-of-tomorrow-818.18430/
	 * I also added a setup to where it adds VoteExecutor as an op
	 */
    private void setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionsPlugin = null;  
        
        if (getServer().getPluginManager().isPluginEnabled("Vault"))
        {
            log.info("[Vote] Vault detected. Using Vault.");
            
            permissionsPlugin = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        
            Vote.permissions = (IPermissionHandler)new PermissionHandlerWrapper(permissionsPlugin.getProvider());
        }
        else 
        {
            log.info("[Vote] Vault not detected for permissions, defaulting to Bukkit Permissions");
            
            Vote.permissions = (IPermissionHandler)new MockPermissionHandler();
        }
          
        playerCommandExecutor = new PlayerWrapper("notch");
        
        if (playerCommandExecutor == null)
            log.severe("Can't find the player notch to mimic!");
    }
    
    /**
     * @return The admin to mimic
     */
    public static PlayerWrapper getPlayerCommandExecutor()
    {
        return playerCommandExecutor;
    }
}