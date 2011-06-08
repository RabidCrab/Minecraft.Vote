package me.RabidCrab.Entities;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

/**
 * A class to inherit when files need to be read/written. 
 * Eventually this will have some more functionality beyond abstract declarations to make it worth the abstraction
 * @author RabidCrab
 */
public abstract class YMLFile
{
    protected Configuration configurationFile;
    
    /**
     * Attempt to load the configuration file. If it doesn't exist, create it
     * @param file The configuration file
     * @throws IOException 
     */
    public YMLFile(File file) throws IOException
    {
        // Used to C#. Declaring booleans to false is a bit weird
        boolean fileCreated = false;
        
        if (!file.exists())
        {
            // In case we need to make the initial file directory
            File pluginPath = new File(file.getPath().substring(0, file.getPath().lastIndexOf(File.separator)));
            
            pluginPath.mkdirs();
            file.createNewFile();
            fileCreated = true;
        }
        
        configurationFile = new Configuration(file);
        
        if (fileCreated)
        {
            populateFile(configurationFile);
            configurationFile.save();
        }
        
        configurationFile.load();
    }
    
    /**
     * If the file doesn't exist, create it and add these parameters
     * @param file The configuration file that was loaded
     */
    protected abstract void populateFile(Configuration file);
    
    /**
     * When retrieving data from a YML file, the hierarchy is case sensitive. This fixes that issue by validating the
     * property path
     * @param property The property to validate
     * @return A valid property path
     */
    public String validateProperty(String property)
    {
        // Not made quite yet >.<
        return property;
    }
}
