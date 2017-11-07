/**
 * LoginSQLRun.java 2012-6-24上午10:47:42
 */
package logic.sqlrun;

import core.DBMgr;
import core.RootConfig;
import core.db.RoleIDUniqueID;
//import core.detail.impl.log.*;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.eLogicCloseUserReason;
import logic.module.log.eLogicSQLLogType;
import logic.module.log.sql.eLoginDebugLogType;
import logic.module.login.Login;
import logic.module.login.eLoginErrorCode;
import logic.userdata.logindata;
import logic.userdata.zz_huiyuan;
import manager.UserManager;
import utility.UniqueID;

/**
 * @author ddoq
 * @version 1.0.0
 *
 */
public class LoginSQLRun extends MySQLRun
{
	private static logindata m_LoginData = new logindata();	///<这个只当成模板用,没实际效果
	private String m_sUserName;
	private String m_sPassword;
	private String m_sServerToken;	// 服务器生成的accesstoken
	private boolean m_bForbid;
	private int m_nServerID;
	private String m_sDeviceIdentifier;
	private String m_sDeviceModel;
	private boolean m_bCreate;
	private String m_pass;
	private int m_icon;
	
	
	
//	private long stm = 0;
	private static UniqueID m_IDBuild = new UniqueID();
/*	private static final String m_user_query = "select * from zz_huiyuan where Name = '%s'";
	//这是只是注册账号的，以后还会有微信登录的；
	private static final String m_user_create = "insert into zz_huiyuan(RoleID, Name, Password, Icon) values (%d, '%s', '%s',%d)";//
*/	private static final String m_MaxRoleData = "SELECT * FROM zz_huiyuan ORDER BY id DESC LIMIT 1";
	private static final String m_user_query = "select * from zz_huiyuan where username = '%s'";
	private static final String m_user_create = "insert into zz_huiyuan(Id, username, Password) values (%d, '%s', '%s)";//
	public LoginSQLRun(MyUser p_user, 
			String p_sUserName, 
			String pass, 
			String p_sServerToken, 
			boolean forbid, 
			int serverid, 
			String deviceIdentifier, 
			String deviceModel, 
			boolean isCreate,
			int icon)
	{
		m_sUserName = p_sUserName;
		m_sPassword = pass;
		m_sServerToken = p_sServerToken;
		m_bForbid = forbid;
		m_nServerID = serverid;
		m_sDeviceIdentifier = deviceIdentifier.substring(0, Math.min(deviceIdentifier.length(), 60));
		m_sDeviceModel = deviceModel.substring(0, Math.min(deviceModel.length(), 60));
		m_bCreate = isCreate;
		m_icon = icon;
	}
	
	/* 
	 * 登录数据查询
	 */
	@Override
	public void Execute(MyUser p_User) throws Exception
	{
		_ExecuteUser(p_User);//todo switch;
	}
	
/*	private void _ExecuteUser(MyUser p_User) throws Exception
	{
		zz_huiyuan[] ds = DBMgr.ReadSQL(new zz_huiyuan(), String.format(m_user_query, m_sUserName));
		zz_huiyuan d = null;
		if (ds.length > 1)
		{
			//读取到多条数据,程序不处理,让玩家找GM协助
			eLoginDebugLogType.LOGINSQL_ERR_CREATE.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.UNKNOW);
			return;
		}
		
		else if (ds.length == 1)
		{
			d = ds[0];
		}
		
		_CheckLoginUserByPassword(d, p_User);
		
	}*/
	private void _ExecuteUser(MyUser p_User) throws Exception
	{
		zz_huiyuan[] ds = DBMgr.ReadSQL(new zz_huiyuan(), String.format(m_user_query, m_sUserName));
		zz_huiyuan d = null;
		if (ds.length > 1)
		{
			//读取到多条数据,程序不处理,让玩家找GM协助
			eLoginDebugLogType.LOGINSQL_ERR_CREATE.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.UNKNOW);
			return;
		}
		
		else if (ds.length == 1)
		{
			d = ds[0];
		}
		
		_CheckLoginUserByPassword(d, p_User);
		
	}
	
	private void _CheckLoginUserByPassword(zz_huiyuan d, MyUser p_User)
	{
		if(d == null)
		{
			// 没有找到用户的数据，用户名尚未注册
			if(!m_bCreate)
			{
				// 没有请求创建新用户数据，则返回用户不存在
				
				System.out.println("* 用户不存在");
				eLoginDebugLogType.LOGIN_FAIL_USER_NOT_EXIST.Log(p_User);
				p_User.setLoginData(null, eLoginErrorCode.USERNAME_NOTEXIST);
				return;
				
			}
			else
			{
				// 需要创建用户
				d = _CreateUser(p_User);
				if(d == null)
					return;
			}
		}
		else
		{
			// 找到用户的数据，用户名已经注册
			if(m_bCreate)
			{
				// 如果请求创建新用户数据，则返回用户已被注册
				System.out.println("* 用户名已被注册");
				//eLoginDebugLogType.REGISTER_FAIL_USERNAME_EXIST.Log(p_User);
				p_User.setLoginData(null, eLoginErrorCode.USERNAME_ALREADYEXIST);
				return;
			}
			else
			{
				//验证密码
				if(!_CheckPassword(d, p_User))
					return;
			}
		}
		// 验证是否封禁
		//if(!_CheckForbid(d, p_User))
		//	return;
		// 一切OK
		//d.LastLoginTime.Set(System.currentTimeMillis());
		if(m_bCreate){
			p_User.setLoginData(null, eLoginErrorCode.REGISTER_OK);
			//_Log(p_User, d, eLoginErrorCode.REGISTER_OK);
		}else{
			p_User.setLoginData(null, eLoginErrorCode.LOGIN_OK);
			//_Log(p_User, d, eLoginErrorCode.LOGIN_OK);
		}

	}
	
	

	private  zz_huiyuan _CreateUser(MyUser p_User)
	{
		System.out.println("* 没有登陆数据,创建登陆数据");
		boolean c = false;
		RoleIDUniqueID build = DBMgr.GetCreateRoleUniqueID();
		zz_huiyuan[] maxrd = DBMgr.ReadSQL(new zz_huiyuan(), m_MaxRoleData);
		if (maxrd.length == 0)
		{
			build.SetBaseValue(0);
		}
		else
		{
			build.SetBaseValue(maxrd[0].RoleID.Get());
		}
		
		//这里我还会添加微信登录的接口：
		//我现在哪roleid当openid来用；
		long userID = build.Get();
		c = DBMgr.ExecuteSQL(String.format(m_user_create, userID, m_sUserName, m_sPassword));
		zz_huiyuan d = null;
		if ( c )
			d = _GetLoginData();
		
		if ( !c || d == null )
		{
			Login.GetInstance().SetLoginFail(p_User);
			System.out.println("* 创建登陆数据失败");
			//eLoginDebugLogType.LOGINSQL_ERR_CREATE.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.UNKNOW);
		}
		else
		{
			p_User.SetRoleGID(userID);
			p_User.SetNick(m_sUserName);
			System.out.println("* 获取角色,进入世界:" + p_User.GetRoleGID());
			boolean c1 = Login.GetInstance().SetLoginSuccess(p_User);
			if ( !c1 )
			{
				p_User.Close(eLogicCloseUserReason.UPDATE_LOGINDATA_FAIL.ID(), 0);
				System.out.println("* 用户加载成功,但是登陆配置出错,断开用户:" + p_User.GetRoleGID());
				
			}
			else
			{
				//Root.GetInstance().AttachUser(p_User);
				PackBuffer.GetInstance().Clear().AddID(Reg.LOGIN,2).
				Add(userID).
				Add(m_sUserName).
				Send(p_User);
				System.out.println("* 创建登陆数据成功");
			}
		}
		return d;
	}
	
	
	private boolean _CheckPassword(zz_huiyuan data, MyUser p_User)
	{
		String pass = "";
		zz_huiyuan p2 = (zz_huiyuan)data;
		
		long id = p2.RoleID.Get();
		//如果没有用户id
		if(p2.RoleID.Get()==0){
			RoleIDUniqueID build = DBMgr.GetCreateRoleUniqueID();
			zz_huiyuan[] maxrd = DBMgr.ReadSQL(new zz_huiyuan(), m_MaxRoleData);
			if (maxrd.length == 0)
			{
				build.SetBaseValue(0);
			}
			else
			{
				build.SetBaseValue(maxrd[0].RoleID.Get());
			}
			
			//这里我还会添加微信登录的接口：
			//我现在哪roleid当openid来用；
			long userID = build.Get();
			p2.RoleID.Set(userID);
			DBMgr.ExecuteSQL("update zz_huiyuan set roleid="+userID+" where username="+p2.username.Get() +" and password = "+p2.password.Get());
			
		}
		
		
		pass = p2.password.Get();
		//todo;
		
		if ( !pass.equals(m_sPassword))
		{
			System.out.println("* 密码错误");
			//eLoginDebugLogType.LOGINSQL_PASSWORD_CHECK.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.PASSWORD_ERR);
			//_Log(p_User, d, eLoginErrorCode.PASSWORD_ERR);
			Login.GetInstance().SetLoginFail(p_User);
			return false;
		}else{
			System.out.println("* 密码正确(重置)");
			//eLoginDebugLogType.LOGINSQL_PASSWORD_OK.Log(p_User);
			long roleId = 0;
			p_User.SetRoleGID(p2.RoleID.Get());
			p_User.SetNick(p2.username.Get());
			String roleName = data.username.Get();
			//
			boolean  ret = Login.GetInstance().SetLoginSuccess(p_User);
			if(ret){
				//Root.GetInstance().AttachUser(p_User);
				roleId = p2.RoleID.Get();
				
				System.out.println("* 获取角色,进入世界:" + p_User.GetRoleGID());
				PackBuffer.GetInstance().Clear().AddID(Reg.LOGIN,2).
				Add(roleId).
				Add(roleName).
				Send(p_User);
	
			}
			
			return true;
		}
		
		
	}
	
	
	
	private boolean _CheckForbid(logindata d, MyUser p_User)
	{
		if (m_bForbid)
		{
			d.Forbid.Set(1);
			DBMgr.UpdateUserNameData(d);
//			Log.out.Log(eSystemInfoLogType.SYSTEM_INFO_SQL_STATEMENT, System.currentTimeMillis() - stm, "_CheckForbid DBMgr.UpdateUserNameData");
			eLoginDebugLogType.LOGIN_FORBID_FAIL.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.LOGIN_FORBID);
			return false;
		}
		if (d.Forbid.Get() == 0 )
		{
			eLoginDebugLogType.LOGIN_FORBID_OK.Log(p_User);
//			p_User.GetLoginUserData().SetLoginData(d, LoginUserData.eLoginRes.OK);
			return true;
		}
		else
		{
			eLoginDebugLogType.LOGIN_FORBID_FAIL.Log(p_User);
			p_User.setLoginData(null, eLoginErrorCode.LOGIN_FORBID);
			return false;
		}
//		Log.out.Log(eSystemInfoLogType.SYSTEM_INFO_SQL_STATEMENT, System.currentTimeMillis() - stm, "_CheckForbid Finish");
	}
	
	private zz_huiyuan _GetLoginData()
	{
		
		zz_huiyuan[] ds = DBMgr.ReadSQL(new zz_huiyuan(), String.format(this.m_user_query, m_sUserName));
		if ( ds != null && ds.length == 1 )
		{
			return ds[0];
		}
		else
		{
			return null;
		}
	}

	private void _Log(MyUser p_User, logindata d, eLoginErrorCode c)
	{
		p_User.Log(eLogicSQLLogType.LOGIC_SQL_LOGIN, 
				RootConfig.GetInstance().ServerUniqueID,
				eLogicSQLLogType.GetCurrTime(),
				d != null ? d.UserID.Get() : 0,
				d != null ? d.UserName.Get() : "",
				p_User.GetLink().GetIP(),
				m_sDeviceIdentifier,
				m_sDeviceModel,
				c.ID());
	}
}
