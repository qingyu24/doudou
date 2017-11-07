package logic.module.room;

import java.util.ArrayList;
import java.util.Iterator;

import core.detail.impl.socket.SendMsgBuffer;
import core.remote.PI;
import core.remote.PL;
import core.remote.PU;
import core.remote.PVI;
import core.remote.RFC;
import logic.LogRecord;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.eGameState;
import logic.module.center.CenterInterface;
import logic.userdata.Team;
import manager.RoomManager;
import manager.TeamManager;

public class RoomImpl implements RoomInterface {

	@Override
	@RFC(ID = 1)
	public void EnterRoom(@PU(Index = 1) MyUser p_user, @PI int roomID, @PL long time) {
		// TODO Auto-generated method stub
		LogRecord.Log(null, "接收到进入房间请求");
		Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if (room != null&&room.getID()==roomID) {
			LogRecord.Log(null, "用户重复发送进入房间忽略");
			/*if(r)*/
			return;
		}
		Room room2 = RoomManager.getInstance().getRoom(roomID);
		//进入自由房
		if (room2!=null  &&!room2.getRr().isFree()) {
			Room r = RoomManager.getInstance().getFreeRoom();
			RoomPlayer rp = r.AddPlayer(p_user);

			SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ENTER);
			buffer.Add(r.getID());

			buffer.Send(p_user);
			// 告诉你房间里面都有谁;1
			r.enbroadcast(RoomInterface.MID_BROADCAST_PLAYER_ENTER, rp, time);
			RoomManager.getInstance().joinRoom(p_user, r.getID());
			// 2
			SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYERS);
			r.packData(p);
			p.Add((int) r.getM_leftTime());
			p.Send(p_user);
			//
		}else if(room2!=null&&room2.getRr().isFree()){ //进入自建房
		/*	Room room2 = RoomManager.getInstance().getRoom(roomID);*/
			if(room2.getRr().isFree()){
		
				room2.AddPlayer(p_user);
				room2.broadcastFree(room2.getRr().getM_type().ID());
			}
		}
	

	}

	@Override
	@RFC(ID = 2)
	public void CreateRoom(@PU(Index = 1) MyUser p_user, @PI int arg, @PL long time) {
		// TODO Auto-generated method stub
		Room r = RoomManager.getInstance().createRoom(p_user);
		r.AddPlayer(p_user);
		if (r != null) {
			SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, 2);
			r.packData(p);
		}
		/* r */

	}

	@Override
	@RFC(ID = 5)
	public void InitBody(@PU(Index = 1) MyUser p_user, @PL long time) {
		// TODO Auto-generated method stub

	}

	@Override
	@RFC(ID = 3)
	public void MoveBody(@PU(Index = 1) MyUser p_user, @PI int playerID, @PVI ArrayList<Integer> list, @PL long time) {
		// TODO Auto-generated method stub
		long millis = System.currentTimeMillis();
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if (r != null) {
			RoomPlayer rp = r.GetPlayer(playerID);
			if (rp != null) {
				rp.updatePlace(list);
				/* LogRecord.Log(p_user, "发来为止信息"+list.toString()); */
				r.broadcast(RoomInterface.MID_BROADCAST_MOVE, rp, time);
			} else {

			}
		}

		LogRecord.writePing("MOVEBODY执行时间", System.currentTimeMillis() - millis);

	}

	@Override
	@RFC(ID = 6)
	public void SplitBody(@PU(Index = 1) MyUser p_user, @PI int playerID, @PVI int m_xpos, @PI int m_ypos,
			@PL long time) {
		// TODO Auto-generated method stub
		long millis = System.currentTimeMillis();
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		RoomPlayer rp = r.GetPlayer(playerID);
		if (rp != null) {
			rp.splitBody();
			r.broadcast(RoomInterface.MID_BROADCAST_SPLIT, playerID, m_xpos, m_ypos, time);
		}
		LogRecord.writePing("SplitBody执行时间", System.currentTimeMillis() - millis);
	}

	@Override
	@RFC(ID = 7)
	public void ComposeBody(@PU(Index = 1) MyUser p_user, @PVI ArrayList<Integer> list, @PL long time) {
		// TODO Auto-generated method stub
	}

	@Override
	@RFC(ID = 4)

	public void EatBody(@PU(Index = Reg.ROOM) MyUser p_user, @PI int eatType, @PI int playerId, @PI int bodyId,
			@PI int xpos, @PI int ypos, @PI int TargetPlayerID, @PI int targetbodyID, @PI int targetxpos,
			@PI int targetypos, @PL long time) {
		// TODO Auto-generated method stub
		long millis = System.currentTimeMillis();

		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if (r != null) {
			r.eatfood(eatType, playerId, TargetPlayerID, targetbodyID);
			ArrayList<Integer> list = new ArrayList<>();
			list.add(eatType);
			list.add(playerId);
			list.add(bodyId);
			list.add(xpos);
			list.add(ypos);
			list.add(TargetPlayerID);
			list.add(targetbodyID);
			list.add(targetxpos);
			list.add(targetypos);

			r.broadcast(RoomInterface.MID_BROADCAST_EAT, list, time);
		}
		LogRecord.writePing("EatBody执行时间", System.currentTimeMillis() - millis);
	}

	@Override
	@RFC(ID = 13)
	public void EatFood(@PU(Index = 1) MyUser p_user, @PI int bodyID, @PI int score, @PL long time) {
		// TODO Auto-generated method stub

	}

	@Override
	@RFC(ID = 17)
	public void SplitQiu(@PU MyUser p_user, @PI int playerID, @PI int xpos, @PI int ypos, @PL long time) {
		// TODO Auto-generated method stub
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		/* RoomPlayer rp = r.GetPlayer(playerID); */
		logic.LogRecord.Log(null, "收到玩家吐球消息");
		r.broadcast(RoomInterface.MID_BROADCAST_QIU, playerID, xpos, ypos, time);
	}

	@Override
	@RFC(ID = 19)
	public void SplitQiuPlace(@PU MyUser p_user, @PI int qiuId, @PI int playerId, @PI int xpos, @PI int ypos,
			@PL long time) {
		// TODO Auto-generated method stub
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if (r != null) {
			r.addQiu(qiuId, playerId, xpos, ypos);
		}
	}

	@Override
	@RFC(ID = 22)
	public void rebirth(MyUser p_user, int playerId, @PL long time) {
		long millis = System.currentTimeMillis();
		// TODO Auto-generated method stub
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		RoomPlayer rp = r.GetPlayer(playerId);
		if (rp != null) {
			PlayerBody body = rp.reset();
			r.broadcast(RoomInterface.MID_BROADCAST_REBIRTH, rp, time);
		}
		LogRecord.writePing("EatBody执行时间", System.currentTimeMillis() - millis);
	}

	@Override
	@RFC(ID = 31)
	public void LeftMatch(@PU(Index = Reg.ROOM) MyUser p_user, @PL long playerId, @PI int teamID, @PL long time) {
		Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		Team team = TeamManager.getInstance().getTeam(teamID);
		if (r != null && team != null) {

			r.RemovePlayer(p_user, time);
			team.removeUser(p_user);
			r.broadcast(CenterInterface.MID_BROADCAST_NEWTEAM, team);
		}
		if (team != null) {
			Iterator<MyUser> iterator = team.m_allUsers.iterator();
			while (iterator.hasNext()) {
				MyUser myUser = (MyUser) iterator.next();
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

	@Override
	@RFC(ID = 33)
	public void chooseTeam(@PU(Index = Reg.ROOM) MyUser p_user ,@PI int teamID){
		Team team = TeamManager.getInstance().getTeam(teamID);
		Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if(team==null){
			 team = TeamManager.getInstance().getNewteam();
		}
		if(team!=null&&room!=null&&team.m_allUsers.size()<room.getRr().getPalyerNum()){
			team.addUser(p_user);
			room.addTeam(team);
			room.broadcastFree(room.getRr().getM_type().ID());
		}else{
			//队伍人满 加不进去 返回错误信息
		}
	}

	@Override
	@RFC(ID = 29)
	public void gameStart(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID) {
		// TODO Auto-generated method stub
		Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if(room!=null){
			room.setM_state(eGameState.GAME_READY);
		}
	}
	@Override
	@RFC(ID = 34)
	public void getFriendList(@PU(Index = Reg.ROOM) MyUser p_user) {
		// TODO Auto-generated method stub
		SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ROOM_FRIENDLIST);
		p_user.packFriends(p);
		p.Send(p_user);
		
	}
/*	@Override
	@RFC(ID = 35)
	public void invoteFriend(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID,@PL long friendID){
		// TODO Auto-generated method stub
		
		p_user.packFriends(p);
		Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		if(room!=null){
			SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ROOM_INVOTEFRIEND);
	
	
		p.Add(p_user.GetRoleGID());
		p.Add(p_user.getTickName());
		p.Add(p_user.getPortrait());// 玩家头像
        p.Add(room.getRr().getTime());
     	p.Add(room.getID());
       
		p.Send(p_user);
		}*/
/*		
	}*/
	
}
