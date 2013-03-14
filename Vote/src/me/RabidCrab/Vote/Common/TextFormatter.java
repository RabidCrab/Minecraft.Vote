package me.RabidCrab.Vote.Common;

import org.bukkit.ChatColor;

/**
 * Text formatting for Minecraft to allow for colored text
 * @author Rabid
 *
 */
public class TextFormatter
{
    /**
     * Format the text by replacing color/font type syntax with something Minecraft can read
     * @param text The text to format colors/italics
     * @return The formatted text that can be shown in Minecraft
     */
    public static String format(String text)
    {
            text = text.replaceAll("&0", ChatColor.BLACK+"");
            text = text.replaceAll("&1", ChatColor.DARK_BLUE+"");
            text = text.replaceAll("&2", ChatColor.DARK_GREEN+"");
            text = text.replaceAll("&3", ChatColor.DARK_AQUA+"");
            text = text.replaceAll("&4", ChatColor.DARK_RED+"");
            text = text.replaceAll("&5", ChatColor.DARK_PURPLE+"");
            text = text.replaceAll("&6", ChatColor.GOLD+"");
            text = text.replaceAll("&7", ChatColor.GRAY+"");
            text = text.replaceAll("&8", ChatColor.DARK_GRAY+"");
            text = text.replaceAll("&9", ChatColor.BLUE+"");
            text = text.replaceAll("&A", ChatColor.GREEN+"");
            text = text.replaceAll("&B", ChatColor.AQUA+"");
            text = text.replaceAll("&C", ChatColor.RED+"");
            text = text.replaceAll("&D", ChatColor.LIGHT_PURPLE+"");
            text = text.replaceAll("&E", ChatColor.YELLOW+"");
            text = text.replaceAll("&F", ChatColor.WHITE+"");
            text = text.replaceAll("&a", ChatColor.GREEN+"");
            text = text.replaceAll("&b", ChatColor.AQUA+"");
            text = text.replaceAll("&c", ChatColor.RED+"");
            text = text.replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
            text = text.replaceAll("&e", ChatColor.YELLOW+"");
            text = text.replaceAll("&u", ChatColor.UNDERLINE+"");
            text = text.replaceAll("&U", ChatColor.UNDERLINE+"");
            text = text.replaceAll("&n", ChatColor.BOLD+"");
            text = text.replaceAll("&N", ChatColor.BOLD+"");
            text = text.replaceAll("&o", ChatColor.ITALIC+"");
            text = text.replaceAll("&O", ChatColor.ITALIC+"");
            text = text.replaceAll("&i", ChatColor.ITALIC+"");
            text = text.replaceAll("&I", ChatColor.ITALIC+"");
            text = text.replaceAll("&k", ChatColor.MAGIC+"");
            text = text.replaceAll("&K", ChatColor.MAGIC+"");
            
            text = text.replaceAll("#0", ChatColor.BLACK+"");
            text = text.replaceAll("#1", ChatColor.DARK_BLUE+"");
            text = text.replaceAll("#2", ChatColor.DARK_GREEN+"");
            text = text.replaceAll("#3", ChatColor.DARK_AQUA+"");
            text = text.replaceAll("#4", ChatColor.DARK_RED+"");
            text = text.replaceAll("#5", ChatColor.DARK_PURPLE+"");
            text = text.replaceAll("#6", ChatColor.GOLD+"");
            text = text.replaceAll("#7", ChatColor.GRAY+"");
            text = text.replaceAll("#8", ChatColor.DARK_GRAY+"");
            text = text.replaceAll("#9", ChatColor.BLUE+"");
            text = text.replaceAll("#A", ChatColor.GREEN+"");
            text = text.replaceAll("#B", ChatColor.AQUA+"");
            text = text.replaceAll("#C", ChatColor.RED+"");
            text = text.replaceAll("#D", ChatColor.LIGHT_PURPLE+"");
            text = text.replaceAll("#E", ChatColor.YELLOW+"");
            text = text.replaceAll("#F", ChatColor.WHITE+"");
            text = text.replaceAll("#a", ChatColor.GREEN+"");
            text = text.replaceAll("#b", ChatColor.AQUA+"");
            text = text.replaceAll("#c", ChatColor.RED+"");
            text = text.replaceAll("#d", ChatColor.LIGHT_PURPLE+"");
            text = text.replaceAll("#e", ChatColor.YELLOW+"");
            text = text.replaceAll("#u", ChatColor.UNDERLINE+"");
            text = text.replaceAll("#U", ChatColor.UNDERLINE+"");
            text = text.replaceAll("#n", ChatColor.BOLD+"");
            text = text.replaceAll("#N", ChatColor.BOLD+"");
            text = text.replaceAll("#o", ChatColor.ITALIC+"");
            text = text.replaceAll("#O", ChatColor.ITALIC+"");
            text = text.replaceAll("#i", ChatColor.ITALIC+"");
            text = text.replaceAll("#I", ChatColor.ITALIC+"");
            text = text.replaceAll("#k", ChatColor.MAGIC+"");
            text = text.replaceAll("#K", ChatColor.MAGIC+"");
            
            return text;
    }
}
