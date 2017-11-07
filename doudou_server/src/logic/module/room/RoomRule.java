package logic.module.room;

import logic.eGameType;

public class RoomRule {
	private eGameType m_type;// 游戏类型
	private int time; // 游戏时间
	private int teamNum; // 队伍数
	private int palyerNum; // 每队人数
	private String roomName; // 房间名称
	private boolean isFree; //是否是自建房
	public RoomRule(int type, int time, int teamNum, int palyerNum) {
		super();
		this.m_type = (type==1)?eGameType.TEAM:eGameType.SOLO;
		this.time = time;
		this.teamNum = teamNum;
		this.palyerNum = palyerNum;
		isFree=true;
	
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public RoomRule() {
		this.m_type=eGameType.SOLO;
		this.time=2;
		this.teamNum=2;
		this.palyerNum=2;
		isFree=false;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public eGameType getM_type() {
		return m_type;
	}

	public void setM_type(eGameType m_type) {
		this.m_type = m_type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	public int getPalyerNum() {
		return palyerNum;
	}

	public void setPalyerNum(int palyerNum) {
		this.palyerNum = palyerNum;
	}

	public int  getAllNum(){
		
		
		return this.palyerNum*this.teamNum;
	}
}
