package logic.module.room;

import core.detail.impl.socket.SendMsgBuffer;
import core.remote.*;
import logic.*;
import logic.module.center.CenterInterface;
import logic.userdata.Team;
import manager.RoomManager;
import manager.TeamManager;

import java.util.ArrayList;

public class RoomImpl implements RoomInterface {

    @Override
    @RFC(ID = 1)
    public void EnterRoom(@PU(Index = 1) MyUser p_user, @PI int roomID, @PL long time) {
        // TODO Auto-generated method stub
        LogRecord.Log(null, "接收到进入房间请求");
        Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());

        Room room2 = RoomManager.getInstance().getRoom(roomID);
        //进入自由房
        if (room2 == null && roomID == 0  /*&&!room2.getRr().isFree()*/) {

            Room r = RoomManager.getInstance().getFreeRoom();
            r.setM_state(eGameState.GAME_PLAYING);
            /*RoomManager.getInstance().joinRoom(p_user, roomID);*/

            RoomPlayer rp = r.AddPlayer(p_user);

            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ENTER);
            buffer.Add(r.getID());
            buffer.Send(p_user);
            // 告诉你房间里面都有谁;1
            r.enbroadcast(RoomInterface.MID_BROADCAST_PLAYER_ENTER, rp, time);    // 2
            SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYERS);
            r.packData(p);
            p.Add((int) r.getM_leftTime());
            p.Send(p_user);
            //
        } else if (room2 != null && room2.getRr().isFree()) { //进入自建房
            /*	Room room2 = RoomManager.getInstance().getRoom(roomID);*/
            if (room2.canJoin()) {
                RoomManager.getInstance().joinRoom(p_user, room2.getID());
                RoomPlayer player = room2.AddPlayer(p_user);
                if (room2.getRr().isTeam()) {
                    room2.free_addUser(player);
                }
                room2.broadcastFree(room2.getRr().getM_type().ID());
            } else {
                p_user.sendError(eErrorCode.Error_1);//房間已滿 或遊戲開始
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
    public void ThornMove(@PU(Index = Reg.ROOM) MyUser p_user, @PVI ArrayList<Integer> list, @PL long time) {
        // TODO Auto-generated method stub
        LogRecord.Log("++++++++++++++++接收到移動+++++++++++++++++++++++++++++++");
        Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (r != null) {

            r.broadcastst(RoomInterface.MID_THORN_BODYMOVE, list, time);
        }

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
          /*      LogRecord.Log("收到位置"+list.toString());*/
                /* LogRecord.Log(p_user, "发来为止信息"+list.toString()); */
/*				r.broadcast(RoomInterface.MID_BROADCAST_MOVE, playerID,rp, time);*/
                r.broadcast(RoomInterface.MID_BROADCAST_MOVE, rp, time);
            } else {
            }
        }
        LogRecord.writePing("MOVEBODY执行时间", System.currentTimeMillis() - millis);

    }

    @Override
    @RFC(ID = 6)
    public void SplitBody(@PU(Index = 1) MyUser p_user, @PI int playerID, @PVI int m_xpos, @PI int m_ypos,
                          @PVI ArrayList<Integer> list, @PL long time) {
        // TODO Auto-generated method stub
        long millis = System.currentTimeMillis();
        Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (r != null) {
            RoomPlayer rp = r.GetPlayer(playerID);
            if (rp != null) {
                rp.splitBody();
                r.broadcast(RoomInterface.MID_BROADCAST_SPLIT, playerID, m_xpos, m_ypos, list, time);
            }
            LogRecord.writePing("SplitBody执行时间", System.currentTimeMillis() - millis);
        }
    }

    @Override
    @RFC(ID = 7)
    public void ComposeBody(@PU(Index = 1) MyUser p_user, @PVI ArrayList<Integer> list, @PL long time) {
        // TODO Auto-generated method stub


        long t = System.currentTimeMillis() - time;
        LogRecord.Log("ping+++++++++++++++++++++++时间" + t);

        SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, MID_BODY_COMPOSE);
        System.out.println("收到 返回");
        p.Add(System.currentTimeMillis());
        p.Send(p_user);


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
    public void SplitQiu(@PU MyUser p_user, @PI int playerID, @PVI ArrayList<Integer> list, @PL long time) {
        // TODO Auto-generated method stub
        Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
		/* RoomPlayer rp = r.GetPlayer(playerID); */
        logic.LogRecord.Log(null, "收到玩家吐球消息");
        if (r != null)
            r.broadcast(RoomInterface.MID_BROADCAST_QIU, playerID, list, time);
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
        LogRecord.Log("收到复活消息" + p_user.GetRoleGID());
        Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
       if(r!=null){
        RoomPlayer rp = r.GetPlayer(playerId);
        if (rp != null) {
            PlayerBody body = rp.reset();
            r.broadcast(RoomInterface.MID_BROADCAST_REBIRTH, rp, time);
            LogRecord.Log("收到复活消息并广播结束");
        }
       }
        LogRecord.writePing("EatBody执行时间", System.currentTimeMillis() - millis);
    }

    @Override
    @RFC(ID = 31)
    public void LeftMatch(@PU(Index = Reg.ROOM) MyUser p_user, @PL long playerId, @PI int teamID, @PL long time) {
        Room r = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        Team team = TeamManager.getInstance().getTeam(teamID);
        //退出 队伍
        if (r != null && team != null && !r.getRr().isFree()) {
            r.RemovePlayer(p_user, time);
            team.removeUser(p_user);
            r.broadcast(CenterInterface.MID_BROADCAST_NEWTEAM, team);
            team.bracatstTeam(r);
        }

        //退出自建房
        if (r != null && r.getRr().isFree()) {

            r.RemovePlayer(p_user, time);
            if (team != null) {
                team.removeUser(p_user);
            }
            r.broadcastFree(r.getRr().getM_type().ID());

        }

    }

    @Override
    @RFC(ID = 33)
    public void chooseTeam(@PU(Index = Reg.ROOM) MyUser p_user, @PI int teamID, @PI int teamName) {
        Team team = TeamManager.getInstance().getTeam(teamID);
        Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (team == null) {
            team = TeamManager.getInstance().getNewteam();
        }
        if (team != null && room != null && team.m_allUsers.size() < room.getRr().getPalyerNum()) {

            RoomPlayer player = room.GetPlayer(p_user);
            if (player != null) {
                Team team2 = TeamManager.getInstance().getTeam(player.getTeamID());
                if (team2 != null && team2.getM_teamID() != team.getM_teamID()) {
                    team2.removeUser(p_user);
                }
                player.setTeamID(team.getM_teamID());
            }
		/*	team.addUser(p_user);*/
            TeamManager.getInstance().joinTeam(p_user, team.getM_teamID());
            room.free_addTeam(team);
			/*room.addTeam(team);*/
            room.setTeamName(team, teamName);
            room.broadcastFree(room.getRr().getM_type().ID());
        } else {
            //队伍人满 加不进去 返回错误信息
            p_user.sendError(eErrorCode.Error_2);
        }
    }

    @Override
    @RFC(ID = 29)
    public void gameStart(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID) {
        // TODO Auto-generated method stub
        Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (room != null) {
            room.setM_state(eGameState.GAME_READY);
        }
		/*	RoomManager.getInstance().freeStart();*/
    }

    @Override
    @RFC(ID = 34)
    public void getFriendList(@PU(Index = Reg.ROOM) MyUser p_user, @PI int needChange) {
        // TODO Auto-generated method stub
        SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ROOM_FRIENDLIST);
        p_user.packFriends(p);
        p.Add(needChange);
        p.Send(p_user);
    }

    @Override
    @RFC(ID = 35)
    public void dissolution(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID) {
        // TODO Auto-generated method stub
        Room room = RoomManager.getInstance().getRoom(roomID);
        if (room != null) {
            room.dissolution();
        }

    }

    @Override
    @RFC(ID = 36)
    public void leaveRoom(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID) {
        // TODO Auto-generated method stub
        Room room = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (room != null) {
			/*		room.RemovePlayer(user, time);*/
            room.RemovePlayer(p_user, System.currentTimeMillis());
        }

    }

    @Override
    @RFC(ID = 37)
    public void visitGame(@PU MyUser p_user, @PL long roleID) {
        Room room2 = RoomManager.getInstance().getRoom(p_user.GetRoleGID());
        if (room2 != null) {
            room2.RemovePlayer(p_user, System.currentTimeMillis());
        }
        Room room = RoomManager.getInstance().getRoom(roleID);
        if (room != null) {
            RoomPlayer rps = room.GetPlayer(roleID);

            if (room != null && rps != null) {
       /*   room.addVisit(p_user);*/
                RoomManager.getInstance().joinRoom(p_user, room.getID());
                RoomPlayer rp = new RoomPlayer();
                rp.init(p_user);
                rp.setVisit(roleID);
                room.AddVisit(rp);
                SendMsgBuffer ps = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ROOM_VISIT);

                ps.Add(rps.getRoleId());

                ps.Send(p_user);
                SendMsgBuffer p2 = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYER_ENTER);
                rps.packDataInit(p2);
                p2.Add(rps.getRoleId());
                p2.Send(p_user);

                SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_BROADCAST_PLAYERS);
                room.packData(p);
                p.Add((int) room.getM_leftTime());
                p.Send(p_user);
                return;
            }
        }
                SendMsgBuffer ps = PackBuffer.GetInstance().Clear().AddID(Reg.ROOM, RoomInterface.MID_ROOM_VISIT);

                ps.Add(Long.parseLong("0"));

                ps.Send(p_user);


    }
    @Override
    @RFC(ID = 38)
    public void speaking(@PU MyUser p_user, @PS String talking) {

        Room room = RoomManager.getInstance().getRoom(p_user.getRoomId());
        if (room != null) {
            room.broadcast(RoomInterface.MID_ROOM_SPEAKING, talking, p_user);
        }
    }
}
