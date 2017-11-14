package logic.loader;

import core.DBLoaderEx;
import core.DBMgr;
import core.detail.impl.socket.SendMsgBuffer;
import logic.LogRecords;
import logic.userdata.zz_huiyuan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HuiyuanLoader extends DBLoaderEx<zz_huiyuan> {

    private static ArrayList<String> m_codes = new ArrayList<String>();
    private static String sql_add = "insert into charge_record(RoleID, TargetRoleID, card)values(%d, %d, %d)";
    private static String sql_rank = "select * from zz_huiyuan order by winCount desc limit %d";
    private static String sql_query_rank = "SELECT * FROM (SELECT (@rownum:=@rownum+1) AS rownum, a.RoleID FROM `zz_huiyuan` a, (SELECT @rownum:= 0 ) r  ORDER BY a.`winCount` DESC) AS b  WHERE RoleID = %d";

    public HuiyuanLoader(zz_huiyuan p_Seed) {
        super(p_Seed);
    }

    public ConcurrentLinkedQueue<zz_huiyuan> getCenterDate() {
        // TODO Auto-generated method stub
        return m_Datas;

    }

    public void packData(SendMsgBuffer buffer) {
        Iterator<zz_huiyuan> it = this.m_Datas.iterator();
        buffer.Add((short) this.m_Datas.size());
        while (it.hasNext()) {
            zz_huiyuan g = it.next();
            g.packData(buffer);
        }
    }


    public zz_huiyuan getUser(String zz_huiyuan) {
        Iterator<zz_huiyuan> it = this.m_Datas.iterator();
        while (it.hasNext()) {
            zz_huiyuan user = it.next();
            if (user.username.Get().equals(zz_huiyuan)) {
                return user;
            }
        }
        return null;
    }

    public zz_huiyuan getUser(long uid) {
        Iterator<zz_huiyuan> it = this.m_Datas.iterator();
        while (it.hasNext()) {
            zz_huiyuan user = it.next();
            if (user.id.Get() == uid) {
                return user;
            }
        }
        return null;
    }


    public void addUser(zz_huiyuan user) {
        this.m_Datas.add(user);
    }

    public void packUserList(SendMsgBuffer buffer, int page) {
        ArrayList<zz_huiyuan> list = new ArrayList<zz_huiyuan>();
        Iterator<zz_huiyuan> it = this.m_Datas.iterator();
        float pageCount = 10f;
        int count = 10;
        int maxPage = (int) (list.size() / pageCount);
        float temp = list.size() / pageCount;

        if (temp > maxPage) {
            maxPage += 1;
        }
        if (page >= maxPage - 1) {
            count = (int) (list.size() % pageCount);
            page = maxPage - 1;
        }
        count = Math.min(count, list.size());
        //buffer.Add((short)count);
        buffer.Add((short) count);
        //if(page < maxPage)
        {
            for (int i = page * 10; i < page * 10 + count; ++i) {
                list.get(i).packData(buffer);
            }
        }
        buffer.Add(this.m_Datas.size());
    }


    public boolean hasUser(String name, String passwrd) {


		/*	bs_user[] ds = DBMgr.ReadSQL(new bs_user(), String.format(m_user_query, name));
            */
        LogRecords.Log(null, "即将验证账户信息" + "电话名" + name + "密码" + passwrd);

        String m_MaxRoleData = "SELECT * FROM zz_huiyuan ORDER BY id DESC LIMIT 1";
        zz_huiyuan[] zz_huiyuans = DBMgr.ReadSQL(new zz_huiyuan(), m_MaxRoleData);
        if (zz_huiyuans.length > 0) {
            if (zz_huiyuans[0].username.Get() == name && zz_huiyuans[0].password.Get() == passwrd) {
                return true;
            }
        }

        return false;

    }

}
