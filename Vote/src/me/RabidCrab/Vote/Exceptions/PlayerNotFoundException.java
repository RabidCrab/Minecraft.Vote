package me.RabidCrab.Vote.Exceptions;

/**
 * Player not found
 * @author Rabid
 *
 */
public class PlayerNotFoundException extends Exception 
{
    private static final long serialVersionUID = 1852559108511215746L;
    String playerName;
    
    public String getPlayerName()
    {
        return playerName;
    }
    
    public PlayerNotFoundException(String playerName) 
    {
        super("Player not found");
        
        this.playerName = playerName;
    }

    public PlayerNotFoundException(String playerName, String msg) 
    {
        super(msg);
        
        this.playerName = playerName;
    }
  }