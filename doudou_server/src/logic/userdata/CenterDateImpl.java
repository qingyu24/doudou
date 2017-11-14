package logic.userdata;

import core.detail.impl.socket.SendMsgBuffer;
import core.remote.*;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import manager.UserManager;


public class CenterDateImpl implements CenterDateInterface {

    @Override
    @RFC(ID = 1)
    public void getClass(@PU MyUser p_user) {

        UserManager.getInstance().getClassmates(p_user);


    }

    @Override
    @RFC(ID = 2)
    public void addFriend(@PU MyUser p_user, @PL long friendID) {
        // TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(friendID);
        if (user != null && user.hasFriend(p_user) == 0) {
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
                    .AddID(Reg.CENTERDATA, CenterDateInterface.MID_ADDFRIEND);
            p_user.packDate(buffer);
            buffer.Send(user);
        } else {
            //如果玩家不在线或者已经是好友返回错误信息
        }
    }

    @Override
    @RFC(ID = 3)
    public void agreeAdd(@PU MyUser p_user, @PL long f_ID) {
        // TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(f_ID);

        if (user != null) {
            p_user.addFriend(user);
            user.addFriend(p_user);
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
                    .AddID(Reg.CENTERDATA, CenterDateInterface.MID_AGRREADD);
            p_user.packDate(buffer);
            buffer.Send(user);
        }

        UserManager.getInstance().addFriends(p_user.GetRoleGID(), f_ID);

    }

    @Override
    @RFC(ID = 5)
    public void givePresent(@PU MyUser p_user, @PL long friendID, @PI int giftID, @PI int price) {
        // TODO Auto-generated method stub
        if (p_user.GetMoney() >= price) {
            p_user.buyShopping(friendID, giftID, price);
            MyUser user = UserManager.getInstance().getUser(friendID);


        } else {
            //金币不足购买
        }
    }

    @Override
    @RFC(ID = 6)
    public void buyShopping(@PU MyUser p_user, @PI int giftID, @PI int price) {
        // TODO Auto-generated method stub
        if (p_user.GetMoney() >= price) {
            p_user.buyShopping(p_user.GetRoleGID(), giftID, price);
            p_user.sendSucess(CenterDateInterface.MID_SHOP_BUY, 1);
        } else {
            //金币不足购买
            p_user.sendSucess(CenterDateInterface.MID_SHOP_BUY, 0);

        }
    }

    @Override
    @RFC(ID = 7)
    public void userHome(@PU MyUser p_user, @PI int giftID) {
        // TODO Auto-generated method stub

    }

    @Override
    @RFC(ID = 8)
    public void sendMessage(@PU MyUser p_user, @PL long targetID, @PS String message) {

    }

    @Override
    @RFC(ID = 4)
    public void deleteFriends(@PU MyUser p_user, @PL long targetID) {


    }

    @Override
    @RFC(ID = 9)
    public void rankingList(@PU MyUser p_user, @PI int list_type, @PI int number, @PI int size) {


    }


}
