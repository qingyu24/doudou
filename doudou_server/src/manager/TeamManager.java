package manager;

import logic.LogRecord;
import logic.MyUser;
import logic.module.room.RoomRule;
import logic.userdata.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamManager {

    private static TeamManager _instance;
    private static Map<Integer, Team> x_list = new HashMap<Integer, Team>(); // 所有队伍
    private static Map<Integer, Team> x_waitlist = new HashMap<Integer, Team>(); // 等待中队伍
    private static Map<Long, Team> u_teamlist = new HashMap<Long, Team>(); // 等待中队伍 ，人所在队伍
    private static int teamId = 0;

    public static TeamManager getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return _instance = new TeamManager();
    }

    public Team getNewteam() {
        teamId++;
        Team team = new Team(teamId);
        x_list.put(team.getM_teamID(), team);
        x_waitlist.put(team.getM_teamID(), team);
        return team;

        // TODO Auto-generated method stub

    }

    public Team joinTeam(MyUser user, int teamID) {
        // TODO Auto-generated method stub
        Team team = x_waitlist.get(teamID);
        //先把从原来队伍去掉
        Team team2 = this.getTeam(user.getRoomId());
        if (team2 != null) {
            team2.removeUser(user);
        }

        if (team != null /*&& team.getM_users().size() < new RoomRule().getPalyerNum()*/) {
            team.addUser(user);
            this.u_teamlist.put(user.GetRoleGID(), team);
            return team;
        } else {

            LogRecord.Log("加入队伍时候没有取到队伍");

        }
        return null;
    }

    public Team getTeam(int teamID) {
        // TODO Auto-generated method stub

        return x_list.get(teamID);
    }

    // 隊伍匹配  從等待隊列中去掉
    public void teamMatch() {
        // TODO Auto-generated method stub

    }


    public void removeUser(MyUser p_user) {
        // TODO Auto-generated method stub
        Team team = this.u_teamlist.get(p_user.GetRoleGID());
        if (team != null) {
            team.removeUser(p_user);
        } else {

            System.out.println("沒有取到");


        }

	/*	this.x_list.remove(p_user.GetRoleGID());
        this.x_waitlist.remove(p_user.GetRoleGID());*/
        this.u_teamlist.remove(p_user.GetRoleGID());

    }

    public void destroyTeam(Team team) {
        // TODO Auto-generated method stub
        this.x_list.remove(team.getM_teamID());
        this.x_waitlist.remove(team.getM_teamID());

    }

}
