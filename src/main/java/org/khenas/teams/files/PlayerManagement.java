package org.khenas.teams.files;


import org.bukkit.entity.Player;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;
import java.util.ArrayList;

public class PlayerManagement  {
    //private static final String sectionKey = "team-list";

    public static void loadPlayers(){
        ArrayList<Team> teamList = new ArrayList<>(TeamList.getTeamMap().values());
        for (Team team : teamList) {
            ArrayList<Member> teamMembers = new ArrayList<>(team.getMembers());
            for (Member member : teamMembers) {
                Player player = member.getPlayer();
                player.sendMessage("Hola");
            }
        }

    }

}
