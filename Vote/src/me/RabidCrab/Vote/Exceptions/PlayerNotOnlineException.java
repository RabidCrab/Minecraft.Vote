package me.RabidCrab.Vote.Exceptions;

/**
 * Player not online
 * @author Rabid
 *
 */
public class PlayerNotOnlineException extends Exception 
{
    private static final long serialVersionUID = 1852559108511215746L;
    String playerName;
    
    public String getPlayerName()
    {
        return playerName;
    }
    
    public PlayerNotOnlineException(String playerName) 
    {
        super("Player not online");
        
        this.playerName = playerName;
    }

    public PlayerNotOnlineException(String playerName, String msg) 
    {
        super(msg);
        
        this.playerName = playerName;
    }
  }