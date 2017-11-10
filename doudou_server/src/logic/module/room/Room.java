package logic.module.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import logic.LogRecord;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.eGameState;
import logic.eGameType;
import logic.userdata.Team;
import logic.userdata.shopping;
import manager.RoomManager;
import manager.TeamManager;
import manager.ThornBallManager;
import core.EqiuType;
import core.Root;
import core.Tick;
import core.detail.impl.socket.SendMsgBuffer;

public class Room implements Tick {

	private RoomRule rr;
	private eGameState m_state;

	private ArrayList<RoomPlayer> m_players;
	private HashMap<Integer, RoomPlayer> m_allPlayer;
	private long m_timeid;
	private int m_roomId;
	private ArrayList<RoomQiu> m_Qius;// 房间内所有吐出来的球
	private int MapId;
	private ArrayList<ThornBall> m_thorns;// 房间内所有的刺球
	private int m_idSum;// 累计生成玩家ID
   private long m_beginTime;// 房间游戏开始时间
	private long m_leftTime;// 游戏倒计时
	private HashMap<Integer, Team> m_teams;// 房间内所有队伍信息
	private ArrayList<Team> m_allTeams;
	private boolean isTeamGame;
	private boolean needScore;// 是否需要广播分数
	private int m_countTime;
	private ArrayList<Integer> m_TeamNames;// 队伍记名
	private MyUser owner;// 房主

	public Room(int id) {
		rr = new RoomRule();// 默认规则
		m_roomId = id;
		m_timeid = Root.GetInstance().AddLoopMilliTimer(this, 1000, null);
		m_players = new ArrayList<RoomPlayer>();
		m_allPlayer = new HashMap<Integer, RoomPlayer>();
		m_Qius = new ArrayList<RoomQiu>();
		MapId = (int) (Math.random() * 10 + 1);
		m_thorns = ThornBallManager.getInstance().getNewlist();
		m_idSum = 1;
		m_beginTime = System.currentTimeMillis();
		m_leftTime = 60 * 1000 * rr.getTime();
		isTeamGame = false;
		m_state = eGameState.GAME_PREPARING;// 游戏准备
		m_allTeams = new ArrayList<Team>();
		m_teams = new HashMap<Integer, Team>();
		needScore = true;
		m_countTime = 0;
		m_TeamNames = creatNewNames();
		m_TeamNames.add(1);

	}

	public Room(int id, RoomRule rr) {
		this.rr = rr;// 默认规则
		m_roomId = id;
		m_timeid = Root.GetInstance().AddLoopMilliTimer(this, 1000, null);
		m_players = new ArrayList<RoomPlayer>();
		m_allPlayer = new HashMap<Integer, RoomPlayer>();
		m_Qius = new ArrayList<RoomQiu>();
		MapId = (int) (Math.random() * 10 + 1);
		m_thorns = ThornBallManager.getInstance().getNewlist();
		m_idSum = 1;
	
		m_beginTime = System.currentTimeMillis();
		m_leftTime = 60 * 1000 * rr.getTime();
		isTeamGame = rr.getM_type().ID()==1;
		m_state = eGameState.GAME_PREPARING;// 游戏准备
		m_allTeams = new ArrayList<Team>();
		m_teams = new HashMap<Integer, Team>();
		needScore = true;
		m_countTime = 0;
		m_TeamNames = creatNewNames();
		m_TeamNames.add(1);

	}

	public RoomRule getRr() {
		return rr;
	}


	public void setRr(RoomRule rr) {
		this.rr = rr;
	}

	private ArrayList<Integer> creatNewNames() {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < rr.getTeamNum(); i++) {
			list.add(i+1);
		}
		return list;
	}

	public eGameState getM_state() {
		return m_state;
	}

	public void setM_state(eGameState m_state) {
		
		if (m_state == eGameState.GAME_PLAYING&&this.m_state!=eGameState.GAME_PLAYING) {
			m_beginTime = System.currentTimeMillis();
		}
		this.m_state = m_state;
	}

	public long getM_leftTime() {
		return m_leftTime;
	}

	public void setM_leftTime(long m_leftTime) {
		this.m_leftTime = m_leftTime;
	}

	public int getID() {
		return this.m_roomId;
	}

	public RoomPlayer AddPlayer(MyUser user) {
	
		 user.setRoomId(this.m_roomId); 
		RoomManager.getInstance().joinRoom(user, m_roomId);
		if(!this.rr.isFree()&&rr.getTeamNum()==0)	{	
			this.setM_state(eGameState.GAME_PLAYING);}// 如果不是团战 只要有人进入房间就开始游戏
		RoomPlayer rp = new RoomPlayer();
		rp.init(user);
		this.AddPlayer(rp);
		needScore = true;
		System.out.println("房间人数" + m_players.size() + "房间ID" + m_roomId);
		return rp;
	}

	/*
	 * 房间人数1房间ID914433 房间人数1房间ID160627
	 */
	public boolean isFull() {
		return m_players.size() >= 30;
	}

	public void AddPlayer(RoomPlayer player) {
		// 第一个加入房间是房主
		if (m_players.size() == 0) {
			this.owner = player.getUser();
		}
		if(!this.containsUser(player.getUser())){
			this.m_players.add(player);
			player.setID(m_idSum);
			m_idSum++;
			this.m_allPlayer.put(player.getID(), player);
			needScore = true;
		}

	}

	private boolean containsUser(MyUser user) {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = this.m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer roomPlayer = (RoomPlayer) it.next();
			if(roomPlayer.getRoleId()==user.GetRoleGID()){
				return true;
			}
		}
		return false;

	}
	private boolean containsTeam(Team team) {
		// TODO Auto-generated method stub
		Iterator<Team> it = this.m_allTeams.iterator();
		while (it.hasNext()) {
			Team teams = it.next();
			if(team.getM_teamID()==teams.getM_teamID()){
				return true;
			}
		}
		return false;

	}
	public RoomPlayer GetPlayer(int id) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer u = it.next();
			if (u != null) {
				if (id == u.getID()) {
					return u;
				}
			}
		}
		LogRecord.Log(null, "没有找到对应玩家");
		return null;

		/* return m_players.get(id-1); */
	}

	public void RemovePlayer(MyUser user, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		boolean find = false;
		while (it.hasNext()) {
			RoomPlayer u = it.next();
			if (u != null) {
				if (user.GetRoleGID() == u.getRoleId()) {
					this.m_players.remove(u);
					/* this.m_allPlayer.remove(u.getID()); */
					broadcast(RoomInterface.MID_BROADCAST_lEAVE,
							user.GetRoleGID(), time);

					if (this.m_players.size() == 0) {

						RoomManager.getInstance().removeRoom(m_roomId);
						this.destroy();
					}
					break;
				}
			}
		}
		
		
	
		if(m_state==eGameState.GAME_PREPARING&&rr.isFree()){
			
			if(user==owner){
				this.dissolution();
			}else{
				this.broadcastFree(this.rr.getM_type().ID());
			}
			
		}
		
		
		if (!find) {

		}

	}

	public void packInit(SendMsgBuffer buffer) {

		buffer.Add(m_roomId);
		/* buffer.Add(r); */

	}

	public void packData(SendMsgBuffer buffer) {
		/* LogRecord.Log(null, "初始化发送其他玩家当前位置"); */
		buffer.Add(MapId);
		buffer.Add((short) m_thorns.size());
		LogRecord.Log(null, "刺球数量" + m_thorns.size());

		Iterator<ThornBall> its = m_thorns.iterator();
		while (its.hasNext()) {
			ThornBall thornBall = (ThornBall) its.next();
			thornBall.packData(buffer);
		}

		buffer.Add((short) this.m_players.size());
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null)
				user.packDataInit(buffer);

		}
		buffer.Add(System.currentTimeMillis());
	}

	// 将该用户的所有数据广播给其他的人；
	public void enbroadcast(int msgId, RoomPlayer ru, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null && user.near(ru)) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				ru.packDataInit(buffer);// 该用户的数据;
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	// 将该用户的数据广播给其他的人；
	public void broadcast(int msgId, RoomPlayer ru, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null && user.near(ru)) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				ru.packData(buffer); // 该用户的数据;
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	
	
	
	public void broadcast(int msgId, int arg1, int arg2, int arg3, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(arg1);
				buffer.Add(arg2);
				buffer.Add(arg3);
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	public void broadcast(int msgId, int arg1, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(arg1);
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	// 广播
	public void broadcast(int msgId, long roleId, ArrayList<Integer> list,
			long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();

			if (null == user) {
				continue;
			}
			SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
					.AddID(Reg.ROOM, msgId);
			buffer.Add(roleId);
			buffer.Add((short) Math.max(list.size(), 1));
			if (list.size() > 0) {
				Iterator<Integer> it2 = list.iterator();
				while (it2.hasNext()) {
					buffer.Add(it2.next());
				}
			} else {
				buffer.Add(0);
			}
			buffer.Add(time);
			buffer.Send(user.getUser());
		}
	}

	@Override
	public void OnTick(long p_lTimerID) throws Exception {
		// TODO Auto-generated method stub

		if (m_state == eGameState.GAME_PLAYING) {
			m_countTime++;
			if (!isTeamGame) {
				sortPlayers();// 定时刷新排行
			} else {
				sortTeams();// 定时刷新队伍排行
			}
			leftTime();// 刷新剩余时间
		}
		//
		if (m_state == eGameState.GAME_READY) {
			this.countDowm(m_countTime++);
			if (m_countTime == 3/*||this.rr.isFree()*/) {
				this.broadcastStart();

			}

		}
	}

	private void countDowm(int i) {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_TIMECOUNT);
				buffer.Add(3 - i);
				buffer.Add(System.currentTimeMillis());
				buffer.Send(user.getUser());
			}
		}
		/*
		 * broadcast(RoomInterface.MID_BROADCAST_LEFTTIME, (int) i,
		 * System.currentTimeMillis());
		 */
	}

	public void destroy() {
		Root.GetInstance().RemoveTimer(this.m_timeid);
	}

	public void clearUser() {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer rp = it.next();
			if (rp != null) {
				RoomManager.getInstance().removeRoomUser(rp.getRoleId());
				it.remove();
			}
		}

		this.m_players.clear();
		this.m_allPlayer.clear();
	}

	// 现在房间内所有玩家吐出来的球
	public void addQiu(int qiuId, int playerId, int xpos, int ypos) {
		// TODO Auto-generated method stub
		RoomQiu qiu = new RoomQiu(qiuId, playerId, xpos, ypos);
		if (!m_Qius.contains(qiu)) {
			m_Qius.add(qiu);
		}
		RoomPlayer p = m_allPlayer.get(playerId);
		if (p != null) {
			p.addSplitQiu(qiu);
		}

	}

	private void sortPlayers() {

		/* System.out.println(""); */
		ArrayList<RoomPlayer> list = new ArrayList<RoomPlayer>();
		list.addAll(m_players);
		Collections.sort(m_players);
		if (!list.equals(m_players) || needScore || m_countTime == 3) {
			m_countTime = 0;
			needScore = false;
			for (int i = 0; i < m_players.size(); i++) {
				m_players.get(i).setRanking(i + 1);
			}
			Iterator<RoomPlayer> its = m_players.iterator();
			while (its.hasNext()) {

				RoomPlayer roomPlayer = (RoomPlayer) its.next();
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_SCORE);
				if (m_players.size() >= 10) {
					buffer.Add((short) (10));
				} else {
					buffer.Add((short) m_players.size());

				}
				for (int i = 0; i < m_players.size(); i++) {
					if (i < 10) {

						buffer.Add(m_players.get(i).getID());
						buffer.Add(0);

						LogRecord.Log(null, "当前玩家" + m_players.get(i).getID()
								+ "名次" + m_players.get(i).getRanking());
					} else {
						break;
					}
				}

				buffer.Add(roomPlayer.getRanking());
				buffer.Add(System.currentTimeMillis());
				buffer.Send(roomPlayer.getUser());
			}
		}
	}

	private void sortTeams() {
		/* LogRecord.Log("开始计时"); */
		ArrayList<Team> list = new ArrayList<Team>();
		list.addAll(this.m_allTeams);
		Collections.sort(m_allTeams);
		if (!list.equals(m_allTeams) || needScore || m_countTime >= 1) {
			needScore = false;
			for (int i = 0; i < m_allTeams.size(); i++) {
				m_allTeams.get(i).setRanking(i + 1);
			}
			Iterator<RoomPlayer> its = m_players.iterator();
			while (its.hasNext()) {
				RoomPlayer rp = (RoomPlayer) its.next();
				Team team = this.getTeam(rp.getRoleId());
				if (team != null) {
					SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
							.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_SCORE);
					if (m_allTeams.size() >= 10) {
						buffer.Add((short) (10));
					} else {
						buffer.Add((short) m_allTeams.size());

					}
					for (int i = 0; i < m_allTeams.size(); i++) {
						if (i < 10) {
							buffer.Add(m_allTeams.get(i).getTeamName());
							buffer.Add(m_allTeams.get(i).m_allUsers.size());
							LogRecord.Log(null, "当前队伍"
									+ m_allTeams.get(i).getTeamName() + "名次"
									+ m_allTeams.get(i).getRanking() + "renshu"
									+ m_allTeams.get(i).m_allUsers.size());
						} else {
							break;
						}
					}

					buffer.Add(team.getTeamName());

					buffer.Add(System.currentTimeMillis());
					buffer.Send(rp.getUser());
				}
			}
		}
	}

	/*
	 * //团战开始前倒计时 public void countDown() {
	 * 
	 * 
	 * 
	 * }
	 */

	private RoomQiu getBall(int palyerId, int ballId) {
		Iterator<RoomQiu> it = m_Qius.iterator();
		while (it.hasNext()) {
			RoomQiu roomQiu = (RoomQiu) it.next();
			if (roomQiu.getM_playerId() == palyerId
					&& roomQiu.getM_id() == ballId) {
				return roomQiu;
			}
		}
		return null;
	}

	// 吃 球。刺球以及吐出来的球
	public void eatfood(int eatType, int playerId, int targetPlayerID,
			int bodyID) {
		// TODO Auto-generated method stub
		if (EqiuType.Ball.ID() == eatType) {
			RoomQiu ball = getBall(targetPlayerID, bodyID);
			if (ball != null) {
				this.m_Qius.remove(ball);
			}

		} else if (EqiuType.SplitBody.ID() == eatType) {
			RoomPlayer player = m_allPlayer.get(targetPlayerID);
			if (player != null) {
				PlayerBody body = player.getPlaybody(bodyID);
				if (body != null) {
					player.getPlaybodylist().remove(body);
					if (player.getPlaybodylist().size() == 0) {
						killOne(playerId);
					}
				}
			} else {
				LogRecord.Log(null, "玩家信息为空" + targetPlayerID);
			}
		} else if (EqiuType.ThornBall.ID() == eatType) {
			Iterator<ThornBall> it = this.m_thorns.iterator();
			while (it.hasNext()) {
				ThornBall tb = (ThornBall) it.next();
				if (tb.getThId() == bodyID) {
					/* m_thorns.remove(tb); */
					it.remove();
					break;
				}
			}
			while (m_thorns.size() < 10) {

				ThornBall ball = ThornBallManager.getInstance()
						.getNewThroBall();
				m_thorns.add(ball);

			}

		} else {
			LogRecord.Log(null, "没有对应的信息");
		}
	}

	// 吞噬一个
	private void killOne(int playerId) {
		// TODO Auto-generated method stub
		RoomPlayer player = m_allPlayer.get(playerId);
		player.killone();
	}

	private void checkKilled() {
		// TODO Auto-generated method stub

	}

	public void broadcast(int msgId, int arg1, int arg2, int arg3, int arg4,
			long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(arg1);
				buffer.Add(arg2);
				buffer.Add(arg3);
				buffer.Add(arg4);
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}
/*	public void broadcast(int midBroadcastSplit, int playerID, int m_xpos,
			int m_ypos, ArrayList<Integer> list, long time) {
		// TODO Auto-generated method stub
		
	}*/
	public void broadcast(int msgId, int arg1, int arg2, int arg3,
		ArrayList<Integer>list,	long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(arg1);
				buffer.Add(arg2);
				buffer.Add(arg3);
				buffer.Add((short)(list.size()));
				for (Integer integer : list) {
					buffer.Add(integer);
				}
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	private void broadcast(int msgId, long msg, long time) {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(msg);
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	public void broadcast(int msgId, int arg1, int arg2, int arg3, int arg4,
			int arg5, long time) {
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, msgId);
				buffer.Add(arg1);
				buffer.Add(arg2);
				buffer.Add(arg3);
				buffer.Add(arg4);
				buffer.Add(arg5);
				buffer.Add(time);
			/*	System.err.println("______________________________");*/
				LogRecord.Log("______________________發送一次");
				buffer.Send(user.getUser());
			}
		}
	}

	public RoomPlayer GetPlayer(MyUser p_user) {
		// TODO Auto-generated method stub
		if (p_user != null) {
			Iterator<RoomPlayer> it = this.m_players.iterator();
			while (it.hasNext()) {
				RoomPlayer rp = (RoomPlayer) it.next();
				if (rp.getRoleId() == p_user.GetRoleGID()) {
					return rp;
				}

			}
		}
		return null;
	}

	public void broadcast(int midBroadcastEat, ArrayList<Integer> list,
			long time) {
		// TODO Auto-generated method stub

		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, midBroadcastEat);
				Iterator<Integer> ite = list.iterator();
				while (ite.hasNext()) {
					Integer its = (Integer) ite.next();
					buffer.Add(its);

				}
				buffer.Add(time);
				buffer.Send(user.getUser());
			}
		}
	}

	public void broadcast(int msg, Team team, RoomPlayer player) {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.CENTER, msg);
				buffer.Add(player.getTeamID());
				buffer.Add(player.getRoleId());// 角色ＩＤ
				buffer.Add(player.getUser().getTickName());// 姓名ＩＤ
				buffer.Add(player.getUser().getPortrait());// 头像
				/* buffer.Add(time); */
				buffer.Send(user.getUser());
			}
		}
	}

	public void broadcast(int msg, Team team2) {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = m_players.iterator();
		LogRecord.Log("当前房间总人数" + m_players.size());
		LogRecord.Log("当前共有队伍" + m_allTeams.size());
		if (team2 != null) {
			while (it.hasNext()) {
				RoomPlayer user = it.next();
				if (user != null/* &&!team2.contain(user.getRoleId()) */) {

					SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
							.AddID(Reg.CENTER, msg);
					buffer.Add(rr.getTeamNum());
					buffer.Add(rr.getPalyerNum());

					Iterator<Team> itt = this.m_allTeams.iterator();
					buffer.Add((short) (this.m_allTeams.size() - 1));

					while (itt.hasNext()) {
						Team team = (Team) itt.next();
						if (team.getM_teamID() != user.getTeamID()) {
							team.packSize(buffer);
						}
						LogRecord.Log("本队伍共有" + team.m_allUsers.size() + "已打包");
					}
					LogRecord.Log("发送一次");
					buffer.Send(user.getUser());
				}
			}
		}
	}

	private void leftTime() {
		// TODO Auto-generated method stub

		m_leftTime = m_beginTime + 60 * 1000 * rr.getTime()
				- System.currentTimeMillis();
		/*
		 * System.out.println("++++++++++++++++++++++++++++++++++++"+m_leftTime);
		 */
		if (m_leftTime <= 0) {
			m_state = eGameState.GAME_OVER;
			/* gameEnd = true; */
			LogRecord.Log(null, "游戏结束，开始结算");
			gameOver();
			return;
		} else {
			broadcast(RoomInterface.MID_BROADCAST_LEFTTIME, (int) m_leftTime,
					System.currentTimeMillis());
			LogRecord.Log("发送当前倒计时"+m_leftTime);
		}
	}

	private void gameOver() {
		// TODO Auto-generated method stub
		// 结算积分
		Iterator<RoomPlayer> it = m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer player = (RoomPlayer) it.next();
			player.calGame();

		}
		// 广播
		Iterator<RoomPlayer> its = m_players.iterator();
		while (its.hasNext()) {

			RoomPlayer roomPlayer = (RoomPlayer) its.next();
			SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
					.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_GAMEOVER);

			buffer.Add((short) (m_players.size()));
			if (!isTeamGame) {
				for (int i = 0; i < m_players.size(); i++) {
					m_players.get(i).endpack(buffer);
					LogRecord.Log(null, "当前玩家" + m_players.get(i).getID()
							+ "名次" + m_players.get(i).getRanking());
				}
			} else {
				Collections.sort(m_allTeams);

				for (Team team : m_allTeams) {

					team.endPack(buffer);
				}

			}

			buffer.Add(roomPlayer.getRanking());
			roomPlayer.getGrade().endPack(buffer);
			buffer.Add(System.currentTimeMillis());
			buffer.Send(roomPlayer.getUser());

		}

		RoomManager.getInstance().removeRoom(m_roomId);
	}

	// 房间是否可加入
	public boolean canJoin() {

		if (m_players.size() >= 30) {
			return false;
		}

		;
		if (m_leftTime < 60 * 1000 * rr.getTime() / 2) {
			return false;
		}

		/*if(rr.getTeamNum()>0&&this.m_state!=eGameState.GAME_PREPARING){
			return false;	
		}*/
		return true; 
	}

	// 团队能否加入
	public boolean canTeamJoin(int size) {
		if (m_state == eGameState.GAME_PLAYING) {
			return false;
		}
		if (m_players.size() + size > rr.getAllNum()) {
			return false;
		}
		;
		if (m_leftTime < 60 * 1000 * rr.getTime() / 2) {
			return false;
		}

		if (this.m_allTeams.size() > rr.getTeamNum()) {
			return false;
		}
		if (this.m_allTeams.size() == rr.getTeamNum()) {
			Iterator<Team> it = this.m_allTeams.iterator();
			while (it.hasNext()) {
				Team team = (Team) it.next();
				if (team.m_users.size() + size <= rr.getPalyerNum()) {

					return true;
				}
			}
			return false;
		}
		return true;
	}

	// 加入团战玩家
	public RoomPlayer addTeamplayer(MyUser p_user, int teamID, int TeamName) {
		// TODO Auto-generated method stub

		RoomPlayer rp = new RoomPlayer();
		rp.init(p_user);
		this.AddPlayer(rp);
		rp.setSkin(TeamName);
		rp.setTeamName(TeamName);
		rp.setTeamID(teamID);
		System.out.println("房间加入一人");
		return rp;
	}

	public Team getTeam(long roleId) {
		// TODO Auto-generated method stub
		Iterator<Team> it = this.m_allTeams.iterator();
		while (it.hasNext()) {
			Team team = (Team) it.next();
			if (team.contain(roleId)) {
				return team;
			}
		}
		return null;
	}

	public void packTeam(SendMsgBuffer p) {
		// TODO Auto-generated method stub
		Iterator<Team> it = this.m_allTeams.iterator();
		p.Add((short) this.m_allTeams.size());
		while (it.hasNext()) {
			Team team = (Team) it.next();
			p.Add(team.getM_teamID());
			p.Add(team.m_users.size());

		}
	}

	
	
	
/*	public Team addtoTeam(Team team) {
		// TODO Auto-generated method stub
		// 先把队伍所有人加入房间
		
	
			Iterator<Team> it = this.m_allTeams.iterator();
			while (it.hasNext()) {
				Team team2 = (Team) it.next();
			
				if (team2.m_users.size() + 1 <= rr.getPalyerNum()) {
					team2.addTeam(team);
					team2.setM_roomID(this.m_roomId);
					this.AddPlayer(team, team2);
					 TeamManager.getInstance().destroyTeam(team); 
					return team2;
				}
				TeamManager.getInstance()
			this.m_allTeams.add(team);
			this.m_teams.put(team.getM_teamID(), team);
			team.setM_roomID(this.m_roomId);
			team.setTeamName(getNewTeamName());
			this.AddPlayer(team, team);
			return team;
		}
		return team;
	}*/
	
	public Team addTeam(Team team) {
		// TODO Auto-generated method stub
		// 先把队伍所有人加入房间
		
		if(!this.m_allTeams.contains(team)){
			Iterator<Team> it = this.m_allTeams.iterator();
			while (it.hasNext()) {
				Team team2 = (Team) it.next();
				if (team2.getM_teamID() == team.getM_teamID()) {
					return null;
				}
				if (team2.m_users.size() + team.m_users.size() <= rr.getPalyerNum()) {
					team2.addTeam(team);
					team2.setM_roomID(this.m_roomId);
					this.AddPlayer(team, team2);
					/* TeamManager.getInstance().destroyTeam(team); */
					return team2;
				}
			}
			this.m_allTeams.add(team);
			this.m_teams.put(team.getM_teamID(), team);
			team.setM_roomID(this.m_roomId);
			team.setTeamName(getNewTeamName());
			this.AddPlayer(team, team);
			return team;
		}
		return team;
	}
	public Team free_addTeam(Team team) {
		// TODO Auto-generated method stub
		if(!this.containsTeam(team)){
	
			this.m_allTeams.add(team);
			this.m_teams.put(team.getM_teamID(), team);
			team.setM_roomID(this.m_roomId);
			team.setTeamName(getNewTeamName());
			this.AddPlayer(team, team);
			return team;
		}
		return team;
	}
	public Team free_addUser(RoomPlayer user) {
		// TODO Auto-generated method stub
		Iterator<Team> it = this.m_allTeams.iterator();
		while (it.hasNext()) {
			Team team2 = (Team) it.next();	
			if (team2.m_users.size() + 1<= rr.getPalyerNum()) {
				TeamManager.getInstance().joinTeam(user.getUser(), team2.getM_teamID());
				user.setTeamID(team2.getM_teamID());

				return team2;
			}
		}
		Team team = TeamManager.getInstance().getNewteam();
		team.addUser(user.getUser());
		this.addTeam(team);
		return team;
	}
	
	
	
	private int getNewTeamName() {
		if (m_TeamNames.size() > 0) {
			return m_TeamNames.remove(0);
		}
		return 0;
		// TODO Auto-generated method stub

	}

	public void AddPlayer(Team team, Team team2) {
		// TODO Auto-generated method stub
		Iterator<Entry<Long, MyUser>> it = team.m_users.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<java.lang.Long, logic.MyUser> entry = (Map.Entry<java.lang.Long, logic.MyUser>) it
					.next();
			MyUser myUser = entry.getValue();
			RoomManager.getInstance().joinRoom(myUser, m_roomId);
			this.addTeamplayer(myUser, team2.getM_teamID(), team2.getTeamName());
		}
	}

	public boolean canStart() {

		LogRecord.Log("游戏人数" + this.m_players.size() + "队伍"
				+ this.m_allTeams.size());
		if (this.m_allTeams.size() >= rr.getTeamNum()
				&& this.m_players.size() >= rr.getAllNum()) {

			return true;
		}
		return false;
	}

	public void broadcastStart() {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> its = m_players.iterator();
		while (its.hasNext()) {

			RoomPlayer rp = (RoomPlayer) its.next();

			SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
					.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYER_ENTER);
			rp.packDataInit(buffer);// 该用户的数据;
			buffer.Add(System.currentTimeMillis());
			buffer.Send(rp.getUser());

			SendMsgBuffer p = PackBuffer.GetInstance().Clear()
					.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYERS);
			this.packData(p);
			p.Add((int) this.getM_leftTime());
			System.out.println("shengyuiu" + this.getM_leftTime() + "发送进入房间信息");
			p.Send(rp.getUser());

		}

		this.setM_state(eGameState.GAME_PLAYING);
	}

	// / 设置为团战房
	public void setTeamRoom() {
		// TODO Auto-generated method stub
		this.isTeamGame = true;
		this.rr.setM_type(eGameType.TEAM);
	}

	public void removeTeam(Team team) {
		// TODO Auto-generated method stub
		this.m_allTeams.remove(team);
		this.m_teams.remove(team.getM_teamID());
		this.m_TeamNames.add(team.getTeamName());
	}

	public void packSize(SendMsgBuffer p) {
		// TODO Auto-generated method stub
		/* p.Add(this.rr.getRoomName()); */
		p.Add(this.m_roomId);
		if (owner != null) {
			p.Add(owner.GetRoleGID());
			p.Add(owner.getTickName());

		} else {
			p.Add(0);
			p.Add(0);
		}
		p.Add(this.rr.getM_type().ID());
		p.Add(this.rr.getTime());
		p.Add(this.rr.getTeamNum());
		p.Add(this.rr.getPalyerNum());
		p.Add(this.m_players.size());
		p.Add(this.rr.getAllNum());
	}

	public void broadcastFree(int isTeam) {
		Iterator<RoomPlayer> its = m_players.iterator();
		while (its.hasNext()) {
			RoomPlayer rp = (RoomPlayer) its.next();
			// TODO Auto-generated method stub
			SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
					.AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_FREETEAM);
			
			buffer.Add(isTeam);
			buffer.Add(this.m_roomId);
			buffer.Add(this.rr.getTime());
			buffer.Add(owner.GetRoleGID());
			buffer.Add(owner.getTickName());
			buffer.Add(rr.getTeamNum());
			buffer.Add(rr.getPalyerNum());
			buffer.Add((short) m_players.size());
			Iterator<RoomPlayer> it = m_players.iterator();
			while (it.hasNext()) {
				RoomPlayer roomPlayer = (RoomPlayer) it.next();
				buffer.Add(roomPlayer.getTeamID());
				buffer.Add(roomPlayer.getTeamName());
				buffer.Add(roomPlayer.getRoleId());
				buffer.Add(roomPlayer.getUser().getPortrait());
				buffer.Add(roomPlayer.getUser().getTickName());
				buffer.Add(roomPlayer.getGrade().getM_level().ID());
				buffer.Add(roomPlayer.getGrade().getM_star());
			}

			buffer.Send(rp.getUser());
		}
	}

	public void setTeamName(Team team, int teamName) {
		// TODO Auto-generated method stub
		if(team.getTeamName()!=0){
			this.m_TeamNames.add(team.getTeamName());
			Iterator<Integer> it = this.m_TeamNames.iterator();
			while (it.hasNext()) {
				Integer integer = (Integer) it.next();
				if(integer==teamName){
					it.remove();
				}
			}
			
		}
		team.setTeamName(teamName);
	}

	public void dissolution() {
		// TODO Auto-generated method stub
		Iterator<RoomPlayer> it = this.m_players.iterator();
		while (it.hasNext()) {
			RoomPlayer user = it.next();
			if (user != null) {
				SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
						.AddID(Reg.ROOM, RoomInterface.MID_ROOM_Dissolution);
				buffer.Send(user.getUser());
			}
		}
	     	this.m_allTeams.clear();
			RoomManager.getInstance().removeRoom(m_roomId);
			this.destroy();
	}


}
