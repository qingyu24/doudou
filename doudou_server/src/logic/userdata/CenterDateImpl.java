package logic.userdata;

import core.DBMgr;
import core.detail.impl.socket.SendMsgBuffer;
import core.remote.*;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.loader.hui_userLoader;
import manager.LoaderManager;
import manager.RankingManager;
import manager.UserManager;

import java.util.ArrayList;


public class CenterDateImpl implements CenterDateInterface {

    @Override
    @RFC(ID = 1)
    public void getClass(@PU MyUser p_user) {
        long l = System.currentTimeMillis();

        UserManager.getInstance().getClassmates(p_user);

        System.out.println(System.currentTimeMillis() - l);
        ;
    }

    @Override
    @RFC(ID = 2)
    public void addFriend(@PU MyUser p_user, @PL long friendID) {
        // TODO Auto-generated method stub
        MyUser user = UserManager.getInstance().getUser(friendID);
        if (user != null && user.hasFriend(p_user.GetRoleGID()) == 0) {
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_ADDFRIEND);
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
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_AGRREADD);
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
            boolean b = p_user.buyShopping(p_user.GetRoleGID(), giftID, price);
            if (b) {
                p_user.sendSucess(CenterDateInterface.MID_SHOP_BUY, 1);
                return;
            }
        }
        //金币不足购买或购买失败
        p_user.sendSucess(CenterDateInterface.MID_SHOP_BUY, 0);


    }

    @Override
    @RFC(ID = 7)
    public void userHome(@PU MyUser p_user, @PL long targerID) {
        // TODO Auto-generated method stub
        if (targerID == 0) {
            SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_USER_HOME);
            boolean b = p_user.packBaseData(p);
            p.Send(p_user);
        } else {
            MyUser user = UserManager.getInstance().getUser(targerID);
            if (user != null) {
                SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_USER_HOME);
                boolean b = user.packBaseData(p);
                p.Send(p_user);
            }
        }

    }

    @Override
    @RFC(ID = 8)
    public void sendMessage(@PU MyUser p_user, @PL long targetID, @PS String message) {
        MyUser user = UserManager.getInstance().getUser(targetID);
        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_USER_MESSAGE);
        p_user.packDate(buffer);
        buffer.Add(message);
        buffer.Send(user);


    }

    /**
     * @param p_user
     * @param targetID
     */
    @Override
    @RFC(ID = 4)
    public void deleteFriends(@PU MyUser p_user, @PL long targetID) {
        boolean b = p_user.deleteFriend(targetID);
        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_USER_DELETE);

        buffer.Add(b);
        buffer.Send(p_user);

    }

    @Override
    @RFC(ID = 9)
    public void rankingClass(@PU MyUser p_user, @PI int list_type, @PI int number, @PI int size) {
        UserClass[] list = new UserClass[0];

        switch (list_type) {
            case 1://班级所有
                list = RankingManager.getInstance().getRankingByClass(list_type, p_user);
            case 2://班级在学校
                list = RankingManager.getInstance().getRankingByClass(list_type, p_user);

        }
        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_RANKING_CLASS);
        buffer.Add(list_type);
        buffer.Add((short) list.length);
        for (UserClass userClass : list) {
            userClass.packdate(buffer);
        }
        buffer.Send(p_user);

    }

    @Override
    @RFC(ID = 10)
    public void changeSkin(@PU MyUser p_user, @PI int type, @PI int number) {
        //type   1 头像 2皮肤
    /*    if (number==0){
        p_user.getskin(type);}*/
        if (type == 2 && number == 0) {
            ArrayList<Integer> list = p_user.getskinlist();
            SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_USER_SKIN);

            buffer.Add((short) list.size());
            for (Integer integer : list) {
                buffer.Add(integer);
            }
            buffer.Send(p_user);
            return;
        }

        boolean b = false;
        if (type == 1) {
            p_user.getCenterData().getM_account().portrait.Set(number);
            b = DBMgr.ExecuteSQL("UPDATE account SET  portrait =" + number + " Where roleid =" + p_user.GetRoleGID());
        }

        if (type == 2) {
            p_user.getCenterData().getM_account().Skin.Set(number);
            b = DBMgr.ExecuteSQL("UPDATE account SET  Skin =" + number + "Where roleid =" + p_user.GetRoleGID());

        }


    }

    @Override
    @RFC(ID = 11)
    public void searchUser(@PU MyUser p_user, @PS String UserName) {


    }

    @Override
    @RFC(ID = 12)
    public void rankingPerson(@PU MyUser p_user, @PI int list_type, @PI int number, @PI int size) {
        hui_userLoader loader = (hui_userLoader) LoaderManager.getInstance().getLoader(LoaderManager.hui_User);
        loader.sendRaning_All(list_type,p_user);

    }


}
