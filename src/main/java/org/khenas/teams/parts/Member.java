package org.khenas.teams.parts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class Member implements Listener {
    private Team team;
    private Player player;

    public Member(){
        team = null;
        player = null;
    }

    public Member(Team team, Player player){
        this.team = team;
        this.player = player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    public Team getTeam(){
        return team;
    }

    public boolean isOnTheSameTeam(Member player){
        return team.getTeamName().equals(player.getTeam().getTeamName());
    }
}
