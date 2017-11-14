package logic.module.center;

import core.remote.*;
import logic.MyUser;
import logic.Reg;

@RCC(ID = Reg.CENTER)
public interface CenterInterface {

    static final int MID_TEAM_CREATE = 1; // 创建队伍;
    static final int MID_TEAM_FRIENDS = 2; // 輄播好友列表;
    static final int MID_TEAM_INVITATION = 3; // 邀请好友;
    static final int MID_TEAM_JOIN = 4;//对收到的邀请进如队伍

    static final int MID_BROADCAST_JOIN = 5; //  广播给 队伍信息  当有玩家进入或离开
    /*static final int MID_BROADCAST_ENTERTEAM = 6; // 广播给所有玩家  新加入玩家的信息
     */
    static final int MID_BROADCAST_MATCHPLAYERS = 7; /*// 广播给新进入队伍 队伍信息 及 其他队伍信息
     */
    static final int MID_BROADCAST_NEWTEAM = 11; // 广播给其他玩家新进入队伍的信息

    static final int MID_BROADCAST_INVITATION = 9;//广播收到好友邀请
    static final int MID_BROADCAST_GAMESTRAT = 8; // 各队伍准备就绪 开始游戏
    static final int MID_TEAM_MATCH = 10; // 开始匹配团队 ，进入匹配界面
    static final int MID_TEAM_RETREAT = 12; // 退出队伍
    static final int MID_TEAM_OWNERCHANGE = 16; // 退出队伍

    static final int MID_ROOM_RULE = 13; // 房间规则
    static final int MID_ROOM_FREEROOMCENTER = 14; // 进入自建房列表
    static final int MID_BROADCAST_FREEROOM = 15; // 广播自建房间列表


    @RFC(ID = MID_TEAM_CREATE)
    void CreatTeam(@PU MyUser p_user);

    @RFC(ID = MID_TEAM_INVITATION)
    void InvitationFriend(@PU MyUser p_user, @PL long p_ID, @PI int p_teamID, @PL long m_friendID, @PI int roomID, @PI int ifFree);

    @RFC(ID = MID_TEAM_JOIN)
    void JoinTeam(@PU MyUser p_user, @PI int p_teamID, @PL long m_friendID);

    @RFC(ID = MID_TEAM_MATCH)
    void TeamGameMatch(@PU MyUser p_user, @PI int p_teamID);

    @RFC(ID = MID_TEAM_RETREAT)
    void RetreatTeam(@PU MyUser p_user, @PL long roleID, @PI int p_teamID);

    @RFC(ID = MID_ROOM_RULE)
    void creatRoomRule(@PU MyUser p_user, @PI int isTeam, @PI int gameTime, @PI int teNumber, @PI int eachSize);

    @RFC(ID = MID_ROOM_FREEROOMCENTER)
    void EnterFreeRoomCenter(@PU MyUser p_user, @PI int isEnter, @PI int isTeam);

}
