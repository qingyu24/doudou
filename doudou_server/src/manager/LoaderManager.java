package manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.midi.Synthesizer;

import core.DBLoader;
import core.DBMgr;
import core.db.RoleIDUniqueID;
import logic.loader.HuiyuanLoader;
import logic.loader.UserLoader;
import logic.userdata.account;
import logic.userdata.zz_huiyuan;


public class LoaderManager {

    private static LoaderManager _instance;
    private static Map<String, DBLoader> m_list = new HashMap<String, DBLoader>();
    public static String Users = "Users";
    public static String Huiyuan = "huiyuan";


    public static LoaderManager getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return _instance = new LoaderManager();
    }

    public void loadAll() {

	/*	if(needBegin()){*/
        UserLoader users = new UserLoader(new account());
        HuiyuanLoader huiyuan = new HuiyuanLoader(new zz_huiyuan());
        m_list.put(Users, users);
        m_list.put(Huiyuan, huiyuan);
/*		}*/
    }

    public DBLoader getLoader(String name) {
        return m_list.get(name);
    }

    //是否需要加载
    private boolean needBegin() {
        // TODO Auto-generated method stub

        String num2 = "SELECT * FROM zz_huiyuan where roleid is null or roleid=0";
        String num = "SELECT * FROM zz_huiyuan where roleid = 0";
        zz_huiyuan[] zz_huiyuans = DBMgr.ReadSQL(new zz_huiyuan(), num2);
        if (zz_huiyuans.length > 0) {
            System.out.println("=============");
            return true;
        } else {
            System.out.println("不需要数据同化");
        }
        return false;
    }
    //把所有的学生登录信息同步到account表中
	/*public void Synchronization() {
		int i=0;
		if(this.needBegin()){
	
			System.out.println("开始数据同步");
//			String m_user_create = "insert into account(RoleID, Name, Password, Icon,Tickname) values (%d, '%s','%s',%d,'%s')";
			String add_roleID = "update zz_huiyuan set RoleID=%d where id=%d";
//			String m_MaxRoleData = "SELECT * FROM account ORDER BY ROLEID DESC LIMIT 1";
			// TODO Auto-generated method stub
			HuiyuanLoader huiyuan = (HuiyuanLoader) this.getInstance().getLoader(Huiyuan);
			UserLoader user = (UserLoader) this.getInstance().getLoader(Users);
			ConcurrentLinkedQueue<zz_huiyuan> hu = huiyuan.getCenterDate();
			ConcurrentLinkedQueue<account> users = user.getCenterDate();
			Iterator<zz_huiyuan> it = hu.iterator();
			System.out.println(hu.size());
			while (it.hasNext()) {
				zz_huiyuan hui = (zz_huiyuan) it.next();
				System.out.println("==="+hui.RoleID.Get());
				if(hui.RoleID.Get()==0){
					//创建ROID
					RoleIDUniqueID build = DBMgr.GetCreateRoleUniqueID();
					account[] maxrd = DBMgr.ReadSQL(new account(), m_MaxRoleData);
					if (maxrd.length == 0)
					{
						build.SetBaseValue(0);
					}
					else
					{
						build.SetBaseValue(maxrd[0].RoleID.Get());
					}

					long userID = build.Get();
					//生成的ID是 
					System.out.println("生成ID"+userID);
					String s="无名士:"+i++;
					if(hui.xm!=null&&hui.xm.Get()!=null&&hui.xm.Get()!="??"&&!hui.xm.Get().equals("无名氏")){
						System.out.println("玩家姓名"+hui.xm.Get());
						s=hui.xm.Get();
					}
					DBMgr.ExecuteSQL(String.format(m_user_create, userID,hui.username.Get(),hui.password.Get(),0,s));
					DBMgr.ExecuteSQL(String.format(add_roleID, userID,hui.id.Get()));
					System.out.println("成功添加一条用户数据");

				}
			}}

	}*/
    public void setRoleID() {

        String add_roleID = "update zz_huiyuan set RoleID=%d where id=%d";
        HuiyuanLoader loader = (HuiyuanLoader) LoaderManager.getInstance().getLoader(LoaderManager.Huiyuan);
        for (zz_huiyuan zzHuiyuan : loader.getCenterDate()) {
            if (zzHuiyuan.RoleID.Get() == 0) {
                zzHuiyuan.id.Get();
                RoleIDUniqueID build = DBMgr.GetCreateRoleUniqueID();
                String m_MaxRoleData = "SELECT * FROM zz_huiyuan ORDER BY ROLEID DESC LIMIT 1";
                zz_huiyuan[] maxrd = DBMgr.ReadSQL(new zz_huiyuan(), m_MaxRoleData);
                if (maxrd.length == 0) {
                    build.SetBaseValue(0);
                } else {
                    build.SetBaseValue(maxrd[0].RoleID.Get());
                }

                long userID = build.Get();
                //生成的ID是
                System.out.println("生成ID" + userID);

                DBMgr.ExecuteSQL(String.format(add_roleID, userID,zzHuiyuan.id.Get()));
            }
        }
    }
}

