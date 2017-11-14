package manager;

import core.DBMgr;
import core.detail.impl.socket.SendMsgBuffer;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.loader.HuiyuanLoader;
import logic.userdata.CenterDateInterface;
import logic.userdata.zz_huiyuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UserManager {

    private static UserManager _instance;
    private static String addFriends = "insert into friends (RoleID,FriendID)values %d,%d ";
    private ArrayList<MyUser> users = new ArrayList<MyUser>();
    private HashMap<Long, MyUser> m_users = new HashMap<Long, MyUser>();

    public static UserManager getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return _instance = new UserManager();
    }

    public void addUser(MyUser user) {
        // TODO Auto-generated method stub
        users.add(user);
        m_users.put(user.GetRoleGID(), user);
        ArrayList<MyUser> friends = user.getFriends();
        if (friends != null) {
            Iterator<MyUser> it = friends.iterator();
            while (it.hasNext()) {
                MyUser myUser2 = (MyUser) it.next();
                myUser2.friendsOnline(user);
            }
        }
    }

    public MyUser getUser(Long id) {
        // TODO Auto-generated method stub
        return m_users.get(id);
    }

    public void removerUer(MyUser myUser) {
        // TODO Auto-generated method stub
        users.remove(myUser);
        m_users.remove(myUser.GetRoleGID());
        ArrayList<MyUser> friends = new ArrayList<MyUser>();
        if (myUser.getFriends() != null) {
            friends.addAll(myUser.getFriends());

            if (friends != null) {
                Iterator<MyUser> it = friends.iterator();
                while (it.hasNext()) {
                    MyUser myUser2 = (MyUser) it.next();

                    myUser2.friendsOffline(myUser);
                }
            }
        }
    }

    // 测试类 全部加为好友
    public void testFriend() {
        // TODO Auto-generated method stub

        HuiyuanLoader loader = new HuiyuanLoader(new zz_huiyuan());
        try {
            loader.OnLoad();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Iterator<zz_huiyuan> it = loader.getCenterDate().iterator();

        while (it.hasNext()) {
            zz_huiyuan h1 = (zz_huiyuan) it.next();
            Iterator<zz_huiyuan> it2 = loader.getCenterDate().iterator();
            while (it2.hasNext()) {
                zz_huiyuan h2 = (zz_huiyuan) it2.next();

                if (h1.RoleID.Get() != h2.RoleID.Get()) {
                    String s = "insert into friends(roleid,friendid) values(%d,%d)";
                    DBMgr.ExecuteSQL(String.format(s, h1.RoleID.Get(), h2.RoleID.Get()));
                }

            }

        }

    }

    //同伴同學
    public void getClassmates(MyUser p_user) {
        // TODO Auto-generated method stub
        List<Integer> nianJi = p_user.getNianJi();
        ArrayList<MyUser> list = new ArrayList<MyUser>();


        for (MyUser user : users) {
            List<Integer> nianJi2 = user.getNianJi();
            if (nianJi.equals(nianJi2) && p_user.GetRoleGID() != user.GetRoleGID()) {
                list.add(user);
            }
        }

        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear()
                .AddID(Reg.CENTERDATA, CenterDateInterface.MID_CLASS);

        buffer.Add((short) list.size());
        for (MyUser myUser : list) {

            myUser.packDate(buffer);
            buffer.Add(p_user.hasFriend(myUser));
            /*	buffer.Add(myUser.isTeacher());*/

        }
        buffer.Send(p_user);


    }

    public void addFriends(long l, long f_id) {
        DBMgr.ExecuteSQL(String.format(addFriends, l, f_id));
        DBMgr.ExecuteSQL(String.format(addFriends, f_id, l));
    }
}
