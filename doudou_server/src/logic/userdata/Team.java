package logic.userdata;

import core.detail.impl.socket.SendMsgBuffer;
import logic.LogRecord;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.module.center.CenterInterface;
import logic.module.room.Room;
import logic.module.room.RoomPlayer;
import manager.RoomManager;
import manager.TeamManager;

import java.util.*;
import java.util.Map.Entry;

public class Team implements Comparable<Team> {
	private int m_teamID;
	private int m_roomID;
	private Boolean gameStart;
	private long roleID;// 队伍创建者ＩＤ
	private int ranking;// 队伍排名
	private int teamName;// 队名

	public int getTeamName() {
		return teamName;
	}

	public void setTeamName(int teamName) {

		this.teamName = teamName;

	}

	public HashMap<Long, MyUser> m_users;
	public ArrayList<MyUser> m_allUsers;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
		Iterator<MyUser> it = this.m_allUsers.iterator();
		Room room = RoomManager.getInstance().getRoom(this.getM_roomID());
		while (it.hasNext()) {
			MyUser myUser = (MyUser) it.next();
			RoomPlayer rp = room.GetPlayer(myUser);
			if(rp!=null){
				rp.setRanking(ranking);
			}
		}
	}

	public Team() {
		this.m_teamID = 0;
		this.teamName=0;
		m_users = new HashMap<Long, MyUser>();
		m_allUsers = new ArrayList<MyUser>();

	}

	public Team(int m_teamID) {
		super();
		this.m_teamID = m_teamID;
		gameStart = false;
		m_users = new HashMap<Long, MyUser>();
		m_allUsers = new ArrayList<MyUser>();
	}

	public int getM_roomID() {
		return m_roomID;
	}

	public void setM_roomID(int m_roomID) {
		this.m_roomID = m_roomID;
	}

	public Boolean getGameStart() {
		return gameStart;
	}

	public void setGameStart(Boolean gameStart) {
		this.gameStart = gameStart;
	}

	public void addUser(MyUser user) {
		// TODO Auto-generated method stub
		//在玩家加入队伍是 如果玩家在队伍中 应该先把他移除

		if (this.roleID == 0) {
			this.roleID = user.GetRoleGID();
		}
		m_users.put(user.GetRoleGID(), user);
		if(!m_allUsers.contains(user)){
		m_allUsers.add(user);}
	}

	public void removeUser(MyUser user) {
		// TODO Auto-generated method stub
		m_users.remove(user.GetRoleGID());
		m_allUsers.remove(user);
		if(user.GetRoleGID()==this.getCreaterID()){
			if(m_allUsers.size()>0){
				this.roleID=m_allUsers.get(0).GetRoleGID();
		this.boradcastChange(this.roleID);
			}
		}

	/*	Room room2 = RoomManager.getInstance().getRoom(this.getM_roomID());*/
/*if(room2!=null&&room2.getRr().isFree()&&room2.getM_state()==eGameState.GAME_PREPARING){
	room2.broadcastFree(room2.getRr().getM_type().ID());
}
  */       if(m_allUsers.size()==0){
      /*  	 Room room = RoomManager.getInstance().getRoom(m_roomID);*/
          	 Room room = RoomManager.getInstance().getRoom(user.GetRoleGID());
        	 if(room!=null){
        		 room.removeTeam(this);
        	 }
        	 TeamManager.getInstance().destroyTeam(this);
         }

	}

	public int getM_teamID() {
		return m_teamID;
	}

	public void setM_teamID(int m_teamID) {
		this.m_teamID = m_teamID;
	}

	public HashMap<Long, MyUser> getM_users() {
		return m_users;
	}

	public void setM_users(HashMap<Long, MyUser> m_users) {
		this.m_users = m_users;
	}

	public void packDate(SendMsgBuffer buffer) {
		buffer.Add((short) this.m_users.size());
		Set<Entry<Long, MyUser>> s = this.m_users.entrySet();
		Iterator<Entry<Long, MyUser>> it = s.iterator();
		while (it.hasNext()) {
			Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it.next();
			MyUser user = entry.getValue();
			user.packDate(buffer);
		}
	}

	public void join(MyUser user) {
		// TODO Auto-generated method stub

	}

	public boolean contain(long roleId) {
		// TODO Auto-generated method stub
		MyUser myUser = this.m_users.get(roleId);
		if (myUser != null) {
			return true;
		}
		return false;
	}

	public void boradcast(MyUser p_user,boolean isJoin) {
		// TODO Auto-generated method stub

		Iterator<Entry<Long, MyUser>> it = m_users.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it.next();
			MyUser user = entry.getValue();
			SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTER,
					CenterInterface.MID_BROADCAST_JOIN);
			p.Add(this.getM_teamID());
			p.Add(this.getCreaterID());
			this.packDate(p);
			user.packFriends(p);
			p.Send(user);
		}
	}



    public void boradcastChange(Long roleID) {
        // TODO Auto-generated method stub
        MyUser myUser = m_users.get(roleID);
        if(myUser!=null){


        Iterator<Entry<Long, MyUser>> it = m_users.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it.next();
            MyUser user = entry.getValue();
            SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTER,
                    CenterInterface.MID_TEAM_OWNERCHANGE);
            p.Add(this.roleID);
            p.Add(myUser.getTickName());
            p.Send(user);
        }
    }
    }
	public long getCreaterID() {
		// TODO Auto-generated method stub
		return roleID;
	}

	public void addTeam(Team team) {
		// TODO Auto-generated method stub
		Iterator<Entry<Long, MyUser>> it = team.m_users.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it.next();
			TeamManager.getInstance().joinTeam(entry.getValue(), this.getM_teamID());
		}
	}

	private int getWeight() {
		// TODO Auto-generated method stub
		int i = 0;
		Room room = RoomManager.getInstance().getRoom(m_roomID);
		if (room != null) {
			Iterator<Entry<Long, MyUser>> it = this.m_users.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it.next();
				RoomPlayer player = room.GetPlayer(entry.getValue());
				if (player != null) {
					i += player.getWeight();
				}
			}
		} else {
			LogRecord.Log(null, "此处取到房间为空");
		}
		return i;
	}

	@Override
	public int compareTo(Team o) {
		if (this.getWeight() > o.getWeight()) {
			return -1;
		} else {
			return 1;
		}

	}

	public void endPack(SendMsgBuffer buffer) {
		// TODO Auto-generated method stub
		/* Collections.sort(m_allUsers); */
		Room room = RoomManager.getInstance().getRoom(m_roomID);
		if (room != null) {
			ArrayList<RoomPlayer> list = new ArrayList<RoomPlayer>();
			Iterator<MyUser> it = this.m_allUsers.iterator();
			while (it.hasNext()) {
				MyUser myUser = (MyUser) it.next();
				RoomPlayer player = room.GetPlayer(myUser);
				if (player != null) {
					boolean add = list.add(player);
				}
			}
			Collections.sort(list);
			for (RoomPlayer roomPlayer : list) {
				roomPlayer.endpack(buffer);
			}
		}
	}

	public void packSize(SendMsgBuffer buffer) {
		// TODO Auto-generated method stub
		buffer.Add(this.m_teamID);
		buffer.Add(this.m_allUsers.size());
		buffer.Add(this.teamName);

	}

	public void  bracatstTeam(Room r){
            Team team=this;
        for (MyUser myUser : team.m_allUsers) {
            SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTER,
                    CenterInterface.MID_BROADCAST_MATCHPLAYERS);
            p.Add(team.getM_teamID());
            p.Add(r.getRr().getPalyerNum());
            p.Add(team.getTeamName());

            team.packDate(p);

            p.Send(myUser);
        }
        }


}
