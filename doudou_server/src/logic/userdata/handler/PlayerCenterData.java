package logic.userdata.handler;

import java.util.ArrayList;

import javax.management.relation.Role;

import core.DBMgr;
import core.SQLRun;
import core.UserData;
import core.detail.impl.socket.SendMsgBuffer;
import logic.MyUser;
import logic.sqlrun.MySQLRun;
import logic.userdata.account;
import logic.userdata.Friends;
import logic.userdata.zz_huiyuan;
import manager.UserManager;;

public class PlayerCenterData implements UserData {

	private boolean m_dataReady;
	private MyUser m_user;
	private account m_account;
	private zz_huiyuan m_huiyuan;

	public PlayerCenterData(MyUser user) {
		this.m_user = user;
	}

	private int m_gid;
	private int m_gid2;

	public int getGid() {
		int baseId = m_user.getBaseRoleID(m_gid);
		m_gid++;
		return baseId;
	}

	public int getGid2() {
		int baseId = m_user.getBaseRoleID(m_gid2);
		m_gid2++;
		return baseId;
	}

	public void updateGrade() {

	}

	public account getM_account() {
		return m_account;
	}

	public void setM_account(account m_account) {
		this.m_account = m_account;
	}

	public void packData(SendMsgBuffer buffer) {
		m_account.packData(buffer);
		
	}

	public void packDatas(SendMsgBuffer buffer) {

		/* buffer.Add(p_data) */

	}
	
	public String getNianJi(){
	return 	this.m_huiyuan.grade.Get()+"年级"+this.m_huiyuan.banji.Get()+"班";
	}

	@Override
	public boolean DataReady() throws Exception {
		// TODO Auto-generated method stub
		return this.m_dataReady;
	}

	@Override
	public SQLRun GetSQLRun() throws Exception {
		// TODO Auto-generated method stub
		return new PlayerSqlRun();
	}

	@Override
	public void SaveToDB() throws Exception {
		// TODO Auto-generated method stub
		if (this.m_account != null) {
			DBMgr.UpdateRoleData(this.m_account);
		}
		if (this.m_huiyuan != null) {
			DBMgr.UpdateRoleData(this.m_huiyuan);
		}
	}

	public class PlayerSqlRun extends MySQLRun {
		@Override
		public void Execute(MyUser p_User) throws Exception {
			// TODO Auto-generated method stub‘

			System.out.println("++++++++++++没有+++++++++++");
			m_dataReady = false;
			long gid = p_User.GetRoleGID();
			System.out.println("ss" + gid);
			account[] data = DBMgr.ReadRoleIDData(gid, new account());
			zz_huiyuan[] datas = DBMgr.ReadRoleIDData(gid, new zz_huiyuan());

			if (datas.length > 0) {
				m_huiyuan = datas[0];
				long rid = m_huiyuan.RoleID.Get();

				if (data.length > 0) {
					m_account = data[0];

				} else {
					System.out.println(String.format("insert into account (roleid,tickname) values (%d,%s)", rid,
							m_huiyuan.xm.Get()));
					boolean sql = DBMgr.ExecuteSQL(String
							.format("insert into account (roleid,tickname) values (%d,'%s')", rid, m_huiyuan.xm.Get()));

					if (sql) {
						data = DBMgr.ReadRoleIDData(gid, new account());
						if (data.length > 0) {
							m_account = data[0];

						} else {
							System.out.println("shibai");
						}
					}

				}
                   //好友信息
				Friends[] Friends = DBMgr.ReadRoleIDData(gid, new Friends());
				ArrayList<MyUser> list = new ArrayList<MyUser>();
				for (Friends Friends2 : Friends) {
					MyUser user = UserManager.getInstance().getUser(Friends2.FriendID.Get());
					if (user != null&&user.GetRoleGID()!=gid) {
						list.add(user);
						System.out.println(user.getTickName()+"好友名字");
					}
				}
				m_user.setFriends(list);
			}

			
			
			UserManager.getInstance().addUser(p_User);
			m_dataReady = true;

		}
	}

	public int getMoney() {
		// TODO Auto-generated method stub

		return this.m_account.Money.Get();
	}

	public int getShool() {
		// TODO Auto-generated method stub
		return this.m_huiyuan.school.Get();
	}

	public int getPortrait() {
		// TODO Auto-generated method stub
		return this.m_account.portrait.Get();
	}

}
