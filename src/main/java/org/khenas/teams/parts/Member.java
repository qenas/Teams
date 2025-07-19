package org.khenas.teams.parts;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Member {
    private Team team;
    private OfflinePlayer player;
    private boolean online;

    public Member(Team team, OfflinePlayer player){
        this.team = team;
        this.player = player;
        this.online = player.isOnline();
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public OfflinePlayer getPlayer(){
        return player;
    }

    public void setTeam(Team team){
        this.team = team;
    }

    public void setOnline(boolean state){
        this.online = state;
    }

    public boolean isOnline(){
        return online;
    }

    public Team getTeam(){
        return team;
    }

    public boolean isOnTheSameTeam(Member player){
        return team.getTeamName().equals(player.getTeam().getTeamName());
    }

    public boolean hasTheSamePlayer(Player other){
        String thisUUID = this.getPlayer().getUniqueId().toString();
        String otherUUID = other.getUniqueId().toString();
        return thisUUID.equals(otherUUID);
    }

    public boolean equals(Member other){
        return hasTheSamePlayer((Player) other.getPlayer());
    }
}
