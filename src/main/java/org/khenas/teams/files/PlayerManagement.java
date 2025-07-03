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
            ArrayList<Player> teamMembers = new ArrayList<>(team.getMembers());
            for (Player member : teamMembers) {
                Member player = new Member(team, member);
                member.sendMessage("Hola");
            }
        }

    }

}
