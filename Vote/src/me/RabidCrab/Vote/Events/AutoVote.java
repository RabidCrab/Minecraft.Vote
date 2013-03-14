package me.RabidCrab.Vote.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.RabidCrab.Vote.PlayerVote;

/**
 * Part of Auto-AFK
 * @author nacramell
 *
 */
public class AutoVote {

    private List<String> autoyes = new ArrayList<String>();
    private List<String> autono = new ArrayList<String>();
    
    public List<String> getYesVotes(){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<autoyes.size();i++){
            list.add(autoyes.get(i)+ "/yes");
        }
        return list;
    }
    
    public List<String> getNoVotes(){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<autono.size();i++){
            list.add(autono.get(i)+ "/no");
        }
        return list;
    }
    
    public int setAuto(PlayerVote playerVote, String string) {
        if(playerVote == null){
            return 0;
        }       
        if(string.equals("yes") || string.equals("y")){
            if(!autoyes.contains(playerVote.getVoteShortName())){
                autoyes.add(playerVote.getVoteShortName());
                return 1;
            }else{
                return 3;
            }
        }else if(string.equals("no") || string.equals("n")){
            if(!autono.contains(playerVote.getVoteShortName())){
                autono.add(playerVote.getVoteShortName());
                return 1;
            }else{
                return 3;
            }
        }else{
            if(autoyes.contains(playerVote.getVoteShortName())){
                autoyes.remove(playerVote.getVoteShortName());
                return 2;
            }else if(autono.contains(playerVote.getVoteShortName())){
                autono.remove(playerVote.getVoteShortName());
                return 2;
            }else{
                return 4;
            }
        }
    }
    
    public int vote(Player p, PlayerVote vote){
        if(autoyes.contains(vote.getVoteShortName())){
            p.chat("/vote yes");
            return 1;
        }else if(autono.contains(vote.getVoteShortName())){
            p.chat("/vote no");
            return 2;
        }
        return 0;
    }
    
}
