package org.khenas.teams.parts;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Member implements Listener {
    private Team team;
    private Player player;

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

    public void setTeam(Team team){
        this.team = team;
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
