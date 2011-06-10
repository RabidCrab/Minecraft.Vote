package me.RabidCrab.Entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.util.config.Configuration;

/**
 * A class to inherit when files need to be read/written. 
 * Eventually this will have some more functionality beyond abstract declarations to make it worth the abstraction
 * @author RabidCrab
 */
public abstract class YMLFile
{
    public Configuration configurationFile;
    
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
     * @return A valid property path. If no path found, returns null
     */
    public String validateProperty(String property)
    {
        // First the query gets separated by .'s
        List<String> keyList = new ArrayList<String>(Arrays.asList(property.split("\\.")));
        String returnResult = "vote";
        
        // Check and make sure there's a result. If there isn't, cry foul
        if (keyList.size() < 1)
            throw new IndexOutOfBoundsException();
        
        // Remove the vote that's going to be put in correctly to begin with
        keyList.remove(0);
        
        // Loop over all the keys 
        for (String key : keyList)
        {
            boolean foundKey = false;
            List<String> keys = configurationFile.getKeys(returnResult);
            
            // If there's no keys, say bye
            if (keys == null)
                return null;
            
            // Loop through all the keys and find the right one
            for (String fileKey : keys)
            {
                if (fileKey.equalsIgnoreCase(key))
                {
                    returnResult += "." + fileKey;
                    foundKey = true;
                    break;
                }
            }
            
            // If we didn't make a match, return null
            if (!foundKey)
                return null;
        }
        
        return returnResult;
    }
}
