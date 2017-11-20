package logic.userdata;

import core.remote.*;
import logic.MyUser;
import logic.Reg;

@RCC(ID = Reg.CENTERDATA)
public interface CenterDateInterface {

    static final int MID_CLASS = 1;//獲取班級列表
    static final int MID_ADDFRIEND = 2;//添加好友
    static final int MID_AGRREADD = 3;//同意添加好友
    static final int MID_USER_DELETE = 4;//删除好友
    static final int MID_SHOP_PRESENT = 5;//增送礼物
    static final int MID_SHOP_BUY = 6;//购买礼物
    static final int MID_USER_HOME = 7;//个人主页
    static final int MID_USER_MESSAGE = 8;//发送消息
    static final int MID_RANKING_CLASS = 9;//班级排行榜消息
    static final int MID_RANKING_PERSON = 12;//班级排行榜消息
    static final int MID_USER_SKIN = 10;// 更换头像 皮肤
    static final int MID_USER_SEARCH = 11;//查找好友




    @RFC(ID = MID_CLASS)
    void getClass(@PU(Index = Reg.ROOM) MyUser p_user);

    @RFC(ID = MID_ADDFRIEND)
    void addFriend(@PU(Index = Reg.ROOM) MyUser p_user, @PL long friendID);

    @RFC(ID = MID_AGRREADD)
    void agreeAdd(@PU(Index = Reg.ROOM) MyUser p_user, @PL long f_ID);

    @RFC(ID = MID_SHOP_PRESENT)
    void givePresent(@PU(Index = Reg.ROOM) MyUser p_user, @PL long friendID, @PI int giftID, @PI int price);

    @RFC(ID = MID_SHOP_BUY)
    void buyShopping(@PU(Index = Reg.ROOM) MyUser p_user, @PI int giftID, @PI int price);

    @RFC(ID = MID_USER_HOME)
    void userHome(@PU(Index = Reg.ROOM) MyUser p_user, @PL long targerID);

    @RFC(ID = MID_USER_MESSAGE)
    void sendMessage(@PU(Index = Reg.ROOM) MyUser p_user, @PL long targetID, @PS String message);

    @RFC(ID = MID_USER_DELETE)
    void deleteFriends(@PU(Index = Reg.ROOM) MyUser p_user, @PL long targetID);

    @RFC(ID = MID_RANKING_CLASS)
    void rankingClass(@PU(Index = Reg.ROOM) MyUser p_user, @PI int list_type, @PI int number, @PI int size);

    @RFC(ID = MID_USER_SKIN)
    void changeSkin(@PU(Index = Reg.ROOM) MyUser p_user, @PI int type, @PI int number);

    @RFC(ID = MID_USER_SEARCH)
    void searchUser(@PU(Index = Reg.ROOM) MyUser p_user,  @PS String UserName);


    @RFC(ID = MID_RANKING_PERSON)
    void rankingPerson(@PU(Index = Reg.ROOM) MyUser p_user, @PI int list_type, @PI int number, @PI int size);



}
