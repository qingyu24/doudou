package logic.module.center;

import core.detail.impl.socket.SendMsgBuffer;
import core.remote.PI;
import core.remote.PL;
import core.remote.PU;
import core.remote.RFC;
import logic.*;
import logic.module.room.Room;
import logic.module.room.RoomRule;
import logic.userdata.Team;
import manager.RoomManager;
import manager.TeamManager;
import manager.UserManager;

public class CenterImpl implements CenterInterface {


    @Override
    @RFC(ID = 1)
    public void CreatTeam(@PU MyUser p_user) {
        // TODO Auto-generated method stu
        Team team = TeamManager.getInstance().getNewteam();
        TeamManager.getInstance().joinTeam(p_user, team.getM_teamID());
        SendMsgBuffer p = PackBuffer.GetInstance().Clear()
                .AddID(Reg.CENTER, CenterInterface.MID_BROADCAST_JOIN);
        p.Add(team.getM_teamID());
        p.Add(team.getCreaterID());
        System.out.println();
        p.Add((short) 1);
        p_user.packDate(p);
        p_user.packFriends(p);
        p.Send(p_user);
    }


    @Override
    @RFC(ID = 3)
    public void InvitationFriend(@PU MyUser p_user, @PL long p_ID,
                                 @PI int p_teamID, @PL long m_friendID, @PI int roomID, @PI int ifFree) {
        // TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(m_friendID);
        Team team = TeamManager.getInstance().getTeam(p_teamID);
        boolean s = roomID == 0 && team != null && !team.contain(m_friendID);
        boolean b = (user.getRoomId() != roomID && roomID != 0);

        if (user != null && (b || s)) {
            SendMsgBuffer p = PackBuffer
                    .GetInstance()
                    .Clear().AddID(Reg.CENTER, CenterInterface.MID_BROADCAST_INVITATION);
            p.Add(p_ID);
            p.Add(p_user.getTickName());
            p.Add(p_user.getPortrait());// 玩家头像
            p.Add(12);// 游戏时长
            p.Add(p_teamID);
            p.Add(roomID);
            p.Add(ifFree);
            p.Send(user);
        }

    }

    @Override
    // 接受邀请 加入房间
    @RFC(ID = 4)
    public void JoinTeam(@PU MyUser p_user, @PI int p_teamID,
                         @PL long m_friendID) {
        Team team = TeamManager.getInstance().joinTeam(p_user, p_teamID);
        if (team != null) {
            team.boradcast(p_user, true);
        } else {

            p_user.sendError(eErrorCode.Error_2);
        }

    }

    ;

    @Override
    @RFC(ID = 10)
    public void TeamGameMatch(@PU MyUser p_user, @PI int p_teamID) {

        Team team = TeamManager.getInstance().getTeam(p_teamID);
        if (team != null) {
            Room room = RoomManager.getInstance().getTeamRoom(
                    team.m_users.size());
            if (team != null && room != null) {
                Team team2 = room.addTeam(team);
                if (team2 != null) {
                    team2.bracatstTeam(room);
                }
                room.broadcast(CenterInterface.MID_BROADCAST_NEWTEAM, team2);
            }

            if (room.canStart()) {
                LogRecord.Log("判断开始游戏");
                room.setM_state(eGameState.GAME_READY);

            } else {
                LogRecord.Log("判断不能开始游戏");
            }
        }
    }

    @Override
    @RFC(ID = 11)
    public void RetreatTeam(@PU MyUser p_user, @PL long roleID, @PI int p_teamID) {
        // 离开队伍
        Team team = TeamManager.getInstance().getTeam(p_teamID);
        TeamManager.getInstance().removeUser(p_user);
        if (team != null) {
            team.boradcast(p_user, false);
        }

    }

    ;

    @Override
    @RFC(ID = 13)
    // 自建房 创建房间规则
    public void creatRoomRule(@PU MyUser p_user, @PI int isTeam,
                              @PI int gameTime, @PI int teNumber, @PI int eachSize) {
gameTime=1;
        RoomRule rule = new RoomRule(isTeam, gameTime, teNumber, eachSize);

        Room room = RoomManager.getInstance().createFreeRoom(p_user, rule);
        if (isTeam == 1) {
            Team team = TeamManager.getInstance().getNewteam();
            TeamManager.getInstance().joinTeam(p_user, team.getM_teamID());
            room.addTeam(team);
            room.broadcastFree(1);
        } else {
            room.AddPlayer(p_user);
            room.broadcastFree(0);

        }
        /* room.addTeam(team) */
    }

    @Override
    @RFC(ID = 14)
    // 进入自建房主界面
    public void EnterFreeRoomCenter(@PU MyUser p_user, @PI int isEnter,
                                    @PI int isTeam) {
        // TODO Auto-generated method stub
        SendMsgBuffer p = PackBuffer.GetInstance().Clear()
                .AddID(Reg.CENTER, CenterInterface.MID_BROADCAST_FREEROOM);
        p.Add(isEnter);
        RoomManager.getInstance().packFreeRoom(p, isTeam);
        p.Send(p_user);

    }

}
