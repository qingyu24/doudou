package logic.module.room;

import core.remote.*;
import logic.MyUser;
import logic.Reg;

import java.util.ArrayList;

@RCC(ID = Reg.ROOM)
public interface RoomInterface {
    static final int MID_ENTER = 1;
    static final int MID_CREATE = 2;
    static final int MID_BODY_MOVE = 3; // 移动
    static final int MID_BODY_EAT = 4; // 吃敌人;
    static final int MID_THORN_BODYMOVE = 5; // 移動;
    static final int MID_BODY_SPLIT = 6; // 分割;
    static final int MID_BODY_COMPOSE = 7; // 合并;
    static final int MID_BODY_QIU = 17; // 吐球;
    static final int MID_REBIRTH = 22; // 复活;
    static final int MID_BODY_QIUPLACE = 19; // 吐出来的球的位置信息;
    static final int MID_BROADCAST_MOVE = 8; // 广播移动;
    static final int MID_BROADCAST_EAT = 9; // 广播吃;
    static final int MID_BROADCAST_DEATH = 10; // 玩家死亡;
    static final int MID_BROADCAST_SPLIT = 11; // 广播分离;
    static final int MID_BROADCAST_COMPOSE = 12; // 广播合并;
    static final int MID_BROADCAST_QIU = 18; // 广播吐球;
    static final int MID_BROADCAST_QIUPLACE = 20; // 广播吐球;
    static final int MID_BROADCAST_THORNBALL = 21; // 广播刺球球;
    static final int MID_BROADCAST_REBIRTH = 24; // 广播复活;
    static final int MID_BODY_EATFOOD = 13; // 吃食物;
    static final int MID_BROADCAST_BULK = 14; // 广播体积变化;
    static final int MID_BROADCAST_PLAYERS = 15; // 广播房间内的玩家信息;
    static final int MID_BROADCAST_PLAYER_ENTER = 16; // 广播玩家进入房间的消息;
    static final int MID_BROADCAST_SCORE = 25; // 广播玩家分数排行;
    static final int MID_BROADCAST_TEAMSCORE = 30; // 广播队伍分数排行;
    static final int MID_BROADCAST_lEAVE = 26; // 广播玩家离开房间;
    static final int MID_BROADCAST_LEFTTIME = 27; // 广播本局游戏剩余时间;
    static final int MID_BROADCAST_GAMEOVER = 28; // 广播游戏结束
    static final int MID_ROOM_GAMESATRT = 29; // 游戏开始 //自建房用
    static final int MID_BROADCAST_TIMECOUNT = 30; // 广播游戏开始的倒计时 //团战用
    static final int MID_TEAM_LEFTMATCH = 31;// 匹配時退出
    static final int MID_BROADCAST_FREETEAM = 32; // 广播自建房,加入房间，离开房间后 队伍内消息
    static final int MID_ROOM_CHOOSETEAM = 33; // 选择队伍
    static final int MID_ROOM_FRIENDLIST = 34; // 請求獲取好友列表
    static final int MID_ROOM_Dissolution = 35; // 解散房间
    static final int MID_ROOM_LEAVE = 36; // 离开房间房间
    static final int MID_ROOM_VISIT = 37; // 观战
    static final int MID_ROOM_SPEAKING = 38; // 语音

    @RFC(ID = MID_ENTER)
    void EnterRoom(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID,
                   @PL long time);

    @RFC(ID = MID_CREATE)
    void CreateRoom(@PU(Index = Reg.ROOM) MyUser p_user, @PI int arg,
                    @PL long time);

    @RFC(ID = MID_THORN_BODYMOVE)
    void ThornMove(@PU(Index = Reg.ROOM) MyUser p_user, @PVI ArrayList<Integer> list, @PL long time);

    @RFC(ID = MID_BODY_MOVE)
    void MoveBody(@PU(Index = Reg.ROOM) MyUser p_user, @PI int playerID,
                  @PVI ArrayList<Integer> list, @PL long time);

    @RFC(ID = MID_BODY_EAT)
    void EatBody(@PU(Index = Reg.ROOM) MyUser p_user, @PI int eatType,
                 @PI int playerId, @PI int bodyId, @PI int xpos, @PI int ypos,
                 @PI int TargetPlayerID, @PI int bodyID, @PI int targetxpos,
                 @PI int targetypos, @PL long time);

    @RFC(ID = MID_BODY_SPLIT)
    void SplitBody(@PU(Index = Reg.ROOM) MyUser p_user, @PI int playerID,
                   @PI int bodyID, @PI int Count, @PVI ArrayList<Integer> list, @PL long time);

    @RFC(ID = MID_BODY_COMPOSE)
    void ComposeBody(@PU(Index = Reg.ROOM) MyUser p_user,
                     @PVI ArrayList<Integer> list, @PL long time);

    @RFC(ID = MID_BODY_EATFOOD)
    void EatFood(@PU(Index = Reg.ROOM) MyUser p_user, @PI int bodyID,
                 @PI int score, @PL long time);

    @RFC(ID = MID_BODY_QIU)
    void SplitQiu(@PU(Index = Reg.ROOM) MyUser p_user, @PI int playerID,
                  @PVI ArrayList<Integer> list, @PL long time);

    @RFC(ID = MID_BODY_QIUPLACE)
    void SplitQiuPlace(@PU(Index = Reg.ROOM) MyUser p_user, @PI int qiuId,
                       @PI int playerId, @PI int xpos, @PI int ypos, @PL long time);

    @RFC(ID = MID_REBIRTH)
    void rebirth(@PU(Index = Reg.ROOM) MyUser p_user, @PI int playerId,
                 @PL long time);

    @RFC(ID = MID_TEAM_LEFTMATCH)
    void LeftMatch(@PU(Index = Reg.ROOM) MyUser p_user, @PL long playerId,
                   @PI int teamID, @PL long time);

    @RFC(ID = MID_ROOM_CHOOSETEAM)
    void chooseTeam(@PU(Index = Reg.ROOM) MyUser p_user, @PI int teamID, @PI int teamName);

    @RFC(ID = MID_ROOM_GAMESATRT)
    void gameStart(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID);

    @RFC(ID = MID_ROOM_FRIENDLIST)
    void getFriendList(@PU(Index = Reg.ROOM) MyUser p_user, @PI int needChange);

    @RFC(ID = MID_ROOM_Dissolution)
    void dissolution(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID);

    @RFC(ID = MID_ROOM_LEAVE)
    void leaveRoom(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID);

    @RFC(ID = MID_ROOM_VISIT)
    void visitGame(@PU(Index = Reg.ROOM) MyUser p_user, @PI int roomID);

    @RFC(ID = MID_ROOM_SPEAKING)
    void speaking (@PU(Index = Reg.ROOM) MyUser p_user, @PS String talking);

}
