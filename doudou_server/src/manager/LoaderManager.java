package manager;

import core.DBLoader;
import core.DBMgr;
import core.db.RoleIDUniqueID;
import logic.loader.*;
import logic.userdata.*;

import java.util.HashMap;
import java.util.Map;


public class LoaderManager {

    public static String Users = "Users";
    public static String Huiyuan = "huiyuan";
    public static String zz_school = "zz_school";
    public static String zz_sheng2 = "zz_sheng2";
    public static String zz_shi2 = "zz_shi2";
    public static String zz_qu2 = "zz_qu2";
    public static String zz_usertype = "zz_usertype";




    private static LoaderManager _instance;
    private static Map<String, DBLoader> m_list = new HashMap<String, DBLoader>();

    public static LoaderManager getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return _instance = new LoaderManager();
    }

 /*   public void loadAll() {
	*//*	if(needBegin()){*//*
        UserLoader users = new UserLoader(new account());
    *//*    HuiyuanLoader huiyuan = new HuiyuanLoader(new zz_huiyuan());*//*
        shengLoader shengLoader = new shengLoader(new zz_sheng2());
*//*        SchoolLoader schoolLoader = new SchoolLoader(new zz_school());
        shengLoader shengLoader = new shengLoader(new zz_sheng2());
        shiLoader shiLoader = new shiLoader(new zz_shi2());*//*
        m_list.put(Users, users);
    *//*    m_list.put(Huiyuan, huiyuan);*//*
*//*        m_list.put(zz_school,schoolLoader);
        m_list.put(zz_shi2,shiLoader);
        m_list.put(zz_sheng2, shengLoader);*//*  m_list.put(zz_sheng2, shengLoader);


*//*		}*//*
    }*/
    public void loadAll() {
        shengLoader shengLoader = new shengLoader(new zz_sheng2());
        shiLoader shiLoader = new shiLoader(new zz_shi2());
        SchoolLoader schoolLoader = new SchoolLoader(new zz_school());
        m_list.put(zz_school,schoolLoader);
        m_list.put(zz_shi2,shiLoader);
        m_list.put(zz_sheng2, shengLoader);


    }

    public String  getName(int type ,int ID,String sID){


        if(type==1){
            shengLoader shengLoader = (shengLoader) m_list.get(zz_sheng2);
            return shengLoader.getSheng(sID);
        }else if(type==2){
            shiLoader dbLoader = (shiLoader) m_list.get(zz_shi2);
            return  dbLoader.getShi(sID);
        }else if(type==3){
            SchoolLoader dbLoader = (SchoolLoader) m_list.get(zz_school);
        return  dbLoader.getSchool(ID);
        }

        return  null;

    }

    public DBLoader getLoader(String name) {
        return m_list.get(name);
    }



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

                DBMgr.ExecuteSQL(String.format(add_roleID, userID, zzHuiyuan.id.Get()));
            }
        }
    }
}

