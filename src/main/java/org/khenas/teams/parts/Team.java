package org.khenas.teams.parts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private final String teamName;
    private UUID leader;
    private final ArrayList<Member> members;

    //new team
    public Team(String teamName){
        this.teamName = teamName;
        members = new ArrayList<>();
    }

    public int getMembersCount(){
        return members.size();
    }

    public void setLeader(UUID leader){
        this.leader = leader;
    }

    public OfflinePlayer getLeaderPlayer(){
        return Bukkit.getOfflinePlayer(leader);
    }

    public void addMember(Member newMember){
        boolean isOnTheTeam = false;
        for(Member member: members){
            if(member.getPlayer().getUniqueId().equals(newMember.getPlayer().getUniqueId())){
                isOnTheTeam = true;
            }
        }
        if(!isOnTheTeam){
            members.add(newMember);
        }
    }

    public void removeMember(Member oldMember){
        members.remove(oldMember);
    }


    public String getTeamName(){
        return teamName;
    }

    public boolean isLeader(Player other){
        String leaderUUID = leader.toString();
        String otherUUID = other.getUniqueId().toString();
        return leaderUUID.equals(otherUUID);
    }

    public ArrayList<Member> getMembers(){
        return members;
    }

    public String getOnlineMembersStringList(){
        StringBuilder cad = new StringBuilder();
        for(Member member: getMembers()){
            if(member.isOnline()){
                cad.append(member.getPlayer().getName()).append(", ");
            }
        }


        if(!getMembers().isEmpty()){
            if(cad.isEmpty()){
                return ChatColor.RED + "All the members are offline right now.";
            } else {
                cad.setLength(cad.length() - 2);
            }
        }

        return cad.toString();
    }

    public int getOnlineCount(){
        int i = 0;
        for(Member member: members){
            if(member.isOnline()){
                i++;
            }
        }
        return i;
    }

    public String getOfflineMembersStringList(){
        StringBuilder cad = new StringBuilder();
        for(Member member: getMembers()){
            if(!member.isOnline()){
                cad.append(member.getPlayer().getName()).append(", ");
            }
        }

        if(!getMembers().isEmpty()){
            if(cad.isEmpty()){
                return ChatColor.GREEN + "All the members are online right now.";
            } else {
                cad.setLength(cad.length() - 2);
            }
        }

        return cad.toString();
    }

    public int getOfflineCount(){
        int i = 0;
        for(Member member: members){
            if(!member.isOnline()){
                i++;
            }
        }
        return i;
    }

    public void showTeamInfoToPlayer(Player player){
        player.sendMessage("-------------------- " + ChatColor.RED + ChatColor.RED + getTeamName() + ChatColor.WHITE + " --------------------");
        player.sendMessage(ChatColor.YELLOW + "Leader: " + ChatColor.WHITE + getLeaderPlayer().getName());
        player.sendMessage(ChatColor.YELLOW + "Members online: " + ChatColor.GREEN + getOnlineMembersStringList());
        player.sendMessage(ChatColor.YELLOW + "Members offline: " + ChatColor.RED + getOfflineMembersStringList());
        player.sendMessage(ChatColor.YELLOW + "Number of members: " + ChatColor.WHITE + getMembersCount());
    }

    public boolean equals(Team other){
        return this.getTeamName().equals(other.getTeamName());
    }


}
