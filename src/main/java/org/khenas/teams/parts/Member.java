package org.khenas.teams.parts;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.khenas.teams.manager.ChatInterface;

import java.util.UUID;

public class Member implements ChatInterface {
    private Team team;
    private UUID player;
    private boolean online;
    private String chat;

    public Member(Team team, UUID player){
        this.team = team;
        this.player = player;
        this.chat = ChatInterface.globalChat;
    }

    public void setPlayer(UUID player){
        this.player = player;
    }

    public UUID getUUID(){
        return player;
    }

    public OfflinePlayer getPlayer(){
        return Bukkit.getOfflinePlayer(player);
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

    public String getChat(){
        return this.chat;
    }

    public void setChat(String key) {
        this.chat = key;
    }


    public boolean isTeammate(Member player){
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
