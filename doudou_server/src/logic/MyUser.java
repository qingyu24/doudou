/**
 * MyUser.java 2012-6-11下午10:06:52
 */
package logic;
import java.util.ArrayList;
import java.util.Iterator;

import core.Root;
import core.RootConfig;
import core.Tick;
import core.detail.UserBase;
import core.detail.impl.socket.SendMsgBuffer;
import logic.module.center.CenterInterface;
import logic.module.character.CharacterImpl;
import logic.module.login.Login;
import logic.module.login.eLoginErrorCode;
import logic.module.room.Room;
import logic.userdata.CountGrade;
import logic.userdata.account;
import logic.userdata.logindata;	
import logic.userdata.handler.PlayerCenterData;
import manager.ConfigManager;
import manager.RoomManager;
import manager.TeamManager;
import manager.UserManager;
/**
 * @author ddoq
 * @version 1.0.0
 *
 * 逻辑层定义的User对象
 */
public class MyUser extends UserBase implements Tick /*,Comparable<MyUser>*/
{

	public boolean client_DataReady = false;
	public boolean server_DataReady = false;
	private eLoginErrorCode m_errorCode;
	private logindata m_loginData;
	private PlayerCenterData m_center;
	private int roomId =-1;
	private CountGrade grade;
	private  ArrayList<MyUser> friends;



	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public boolean CheckPayMark(String p_Mark)
	{
		return true;
	}
	public CountGrade getGrade() {
		if(grade==null){
			grade=new CountGrade(m_center.getM_account().Garde.Get());
		}
		return grade;
	}

	public ArrayList<MyUser> getFriends() {
		if(friends==null){
			friends=new ArrayList<MyUser>();
		}

		return friends;
	}

	public void setGrade(CountGrade grade) {
		this.grade = grade;
	}

	public void friendsOnline(MyUser user) {
		// TODO Auto-generated method stub
		if(!friends.contains(user)){
			this.friends.add(user);}

		this.friend_borcast();

	}
	public void friendsOffline(MyUser user) {
		// TODO Auto-generated method stub
		this.friends.remove( user);
		this.friend_borcast();
	}

	public  void packDate(SendMsgBuffer buffer) {
		buffer.Add(this.GetRoleGID());  //id
		buffer.Add(this.m_center.getM_account().TickName.Get());//姓名
		buffer.Add(this.getPortrait());//头像ID 
		buffer.Add(this.getGrade().getM_level().ID());
		buffer.Add(this.getGrade().getM_star());
	}

	public MyUser()
	{					
		//在此添加所有的UserData注册
		this.m_center = new PlayerCenterData(this);
		AddToUserData(this.m_center);
		/*		grade=new CountGrade(m_center.getM_account().Garde.Get());*/


	}

	public PlayerCenterData getCenterData(){
		return this.m_center;
	}

	public void setFriends(ArrayList<MyUser> f){
		this.friends=f;
		if(f.size()>0){
			System.out.println(this.getTickName()+"好友爲"+f.get(0).getTickName());
		}
	}
	//----------------------玩家需要计算的属性;

	public eLoginErrorCode getLoginRes(){
		return m_errorCode;
	}

	//获得角色id位移之前的数据;
	public int getBaseRoleID(int origLen){
		long serverId = RootConfig.GetInstance().ServerUniqueID;
		long uid = serverId << (64 - 12);
		long roleId = this.GetRoleGID();
		int baseId = (int)((roleId - uid) / 10);
		return (int) (baseId + origLen + 100000);// 0 - 100000是留给机器人的;

	}


	public void setLoginData(logindata ld, eLoginErrorCode code){
		m_loginData = ld;
		m_errorCode = code;
	}

	public boolean packBaseData(SendMsgBuffer buffer){
		m_center.packData(buffer);
		return true;
	}

	/* (non-Javadoc)
	 * @see core.UserBase#ExecuteKeyDataSQLRun()
	 */
	@Override
	public void ExecuteKeyDataSQLRun() throws Exception
	{
		//TODO;
	}

	/* (non-Javadoc)
	 * @see core.UserBase#OnDisconnect()
	 */
	@Override
	public void OnDisconnect() throws Exception
	{
		//当玩家断开的时候要将所有的数据释放掉。
		UserManager.getInstance().removerUer(this);//好友系统
		super.OnDisconnect();

		m_errorCode = eLoginErrorCode.UNKNOW;
		Login.GetInstance().OnDisconnect(this);
		ConfigManager.getInstance().OnDisconnect(this);
		Room room = RoomManager.getInstance().getRoom(this.GetRoleGID());

		TeamManager.getInstance().removeUser(this);


		RoomManager.getInstance().removeRoomUser(this.GetRoleGID());
		if(null != room){
			room.RemovePlayer(this,System.currentTimeMillis());

			/*			this.roomId=-1;*/

		}
		//Echo.GetInstance().OnDisconnect(this);
		if ( this.IsKeyDataReady() ) //如果这个玩家的标示还存在就释放数据；
		{
			//释放数据；
		}

		long uid = 0;
		client_DataReady = false;
	}

	@Override
	public void OnRelease() throws Exception
	{
		super.OnRelease();
	}

	/* (non-Javadoc)
	 * @see core.detail.UserBase#OnAllDataLoadFinish()
	 */
	@Override
	public void OnAllDataLoadFinish() throws Exception
	{
		System.out.println("*User的数据加载完成:" + this);
		Root.GetInstance().AddLoopTimer(this, 60 * 5, this); // 开启主循环;
		//设置防沉迷，是不是要踢掉这个玩家；
		//设置屏蔽，是不是要踢掉这个玩家；
		//向客户端发一下基础的角色数据,
		CharacterImpl.GetInstance().ServerDataReady(this);

	}

	private void friend_borcast() {
		// TODO Auto-generated method stub
		SendMsgBuffer p = PackBuffer.GetInstance().Clear().AddID(Reg.CENTER,
				CenterInterface.MID_TEAM_FRIENDS);

		Iterator<MyUser> iterator = this.friends.iterator();

		p.Add((short)this.friends.size());
		while (iterator.hasNext()) {
			MyUser myUser = (MyUser) iterator.next();
			myUser.packDate(p);	
		}
		p.Send(this);
	}


	/* (non-Javadoc)
	 * @see core.Tick#OnTick(long)
	 */
	@Override
	public void OnTick(long p_lTimerID) throws Exception
	{
		if ( IsDisabled() )
		{
			return;
		}
		AddSaveTask(false);
	}

	public String getTickName(){

		return	this.getCenterData().getM_account().TickName.Get();


	}

	public account getCount() {
		// TODO Auto-generated method stub
		return this.getCenterData().getM_account();

	}
	//分数查询和改变
	public int changeScore(int i) {
		// TODO Auto-generated method stub
		if(i==0){
			return	this.m_center.getM_account().Garde.Get();
		}else{
			int get = this.m_center.getM_account().Garde.Get();
			this.m_center.getM_account().Garde.Set(get+i);
			this.grade.setM_score(get+i);
			return 	this.m_center.getM_account().Garde.Get();
		}

	}

	public void changeMoney(int i) {
		// TODO Auto-generated method stub
		int get = this.m_center.getM_account().Money.Get();
		this.m_center.getM_account().Money.Set(get+i);


	}

	public int getSchool() {
		// TODO Auto-generated method stub
		return	this.m_center.getShool();

	}

	public int getPortrait() {
		// TODO Auto-generated method stub
		return this.m_center.getPortrait();
	}

	public void packFriends(SendMsgBuffer p) {
		// TODO Auto-generated method stub
		if(this.friends!=null){
			p.Add((short)friends.size());
			Iterator<MyUser> it = this.friends.iterator();
			while (it.hasNext()) {
				MyUser myUser = (MyUser) it.next();
				myUser.packDate(p);
			}
		}
	}



}
