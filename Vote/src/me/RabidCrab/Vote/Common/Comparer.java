package me.RabidCrab.Vote.Common;

import java.util.List;

/**
 * A class containing all the custom comparers I use
 * @author Rabid
 *
 */
public class Comparer
{
    /**
     * Search for the item in the list while ignoring case
     * @param listToCompare The list to search through
     * @param searchString The string to search for
     * @return True if found
     */
    public static boolean containsIgnoreCase(List<String> listToCompare, String searchString)
    {
        for (String string : listToCompare)
        {
            if (string.equalsIgnoreCase(searchString))
                return true;
        }
        
        return false;
    }
}
