package org.khenas.teams.parts;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class Member implements Listener {
    private Team team;
    private OfflinePlayer player;

    public Member(){
        team = null;
        player = null;
    }

    public Member(Team team, OfflinePlayer player){
        this.team = team;
        this.player = player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public OfflinePlayer getPlayer(){
        return player;
    }

    public Team getTeam(){
        return team;
    }



    public boolean isOnTheSameTeam(Member player){
        return team.getTeamName().equals(player.getTeam().getTeamName());
    }
}
