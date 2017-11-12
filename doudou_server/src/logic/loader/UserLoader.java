package logic.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import logic.LogRecords;
import utility.Debug;

import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.userdata.account;
import manager.LoaderManager;
import core.DBLoaderEx;
import core.DBMgr;
import core.User;
import core.detail.impl.OnlineUserSelector;
import core.detail.impl.socket.SendMsgBuffer;
import core.remote.PS;
import core.detail.impl.OnlineUserSelector;

public class UserLoader extends DBLoaderEx<account> {

	private static ArrayList<String> m_codes = new ArrayList<String>();
	private static String sql_add = "insert into charge_record(RoleID, TargetRoleID, card)values(%d, %d, %d)";
	private static String sql_rank = "select * from account order by winCount desc limit %d";
	private static String sql_query_rank = "SELECT * FROM (SELECT (@rownum:=@rownum+1) AS rownum, a.RoleID FROM `account` a, (SELECT @rownum:= 0 ) r  ORDER BY a.`winCount` DESC) AS b  WHERE RoleID = %d";
	public UserLoader(account p_Seed) {
		super(p_Seed);
	}
	
	public void packData(SendMsgBuffer buffer){
		Iterator<account> it = this.m_Datas.iterator();
		buffer.Add((short)this.m_Datas.size());
		while(it.hasNext()){
			account g = it.next();
			g.packData(buffer);
		}
	}
	
	public account getUser(String account){
		Iterator<account> it = this.m_Datas.iterator();
		while(it.hasNext()){
			account user = it.next();
			if(user.Name.Get().equals(account)){
				return user;
			}
		}
		return null;
	}
	
	public account getUser(long uid){
		Iterator<account> it = this.m_Datas.iterator();
		while(it.hasNext()){
			account user = it.next();
			if(user.RoleID.Get() == uid){
				return user;
			}
		}
		return null;
	}
	
	
	public void addUser(account user){
		this.m_Datas.add(user);
	}
	
	public void packUserList(SendMsgBuffer buffer, int page){
		ArrayList<account> list = new ArrayList<account>();
		Iterator<account> it = this.m_Datas.iterator();
		float pageCount = 10f;
		int count = 10;
		int maxPage = (int)(list.size() / pageCount);
		float temp = list.size() / pageCount;
	
		if(temp > maxPage){
			maxPage += 1;
		}
		if(page >= maxPage - 1){
			count = (int) (list.size() % pageCount);
			page = maxPage - 1;
		}
		count = Math.min(count, list.size());
		//buffer.Add((short)count);
		buffer.Add((short)count);
		//if(page < maxPage)
		{
			for(int i =  page * 10 ; i < page * 10 + count; ++ i){
				list.get(i).packData(buffer);
			}
		}
		buffer.Add(this.m_Datas.size());
	}

	public ConcurrentLinkedQueue<account> getCenterDate() {
		// TODO Auto-generated method stub
		return null;
	}
/*
    public boolean hasUser(String name ,String passwrd) {


		*/
/*	bs_user[] ds = DBMgr.ReadSQL(new bs_user(), String.format(m_user_query, name));
			*//*

        LogRecords.Log(null,"即将验证账户信息"+"电话名"+name+"密码"+passwrd);
       */
/*         this.m_Datas.iterator();*//*


    */
/*    while(its.hasNext()){
            bs_user user = its.next();
            if(user.Tel.Get().equals(name)&&user.Password.Get().equals(passwrd)){
                LogRecords.Log(null,"验证密码正确，即将被顶掉");
                return true;

            }
        }*//*

        for (account m_data : m_Datas) {

        }

        return false;

    }
*/


}
