package logic.module.room;

import core.detail.impl.socket.SendMsgBuffer;

public class RoomQiu {
	private int m_id;
	private int m_playerId;
	private int m_xpos;
	private int m_ypos;

	public RoomQiu(int m_id, int m_playerId, int m_xpos, int m_ypos) {
		super();
		this.m_id = m_id;
		this.m_playerId = m_playerId;
		this.m_xpos = m_xpos;
		this.m_ypos = m_ypos;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getM_playerId() {
		return m_playerId;
	}
	public void setM_playerId(int m_playerId) {
		this.m_playerId = m_playerId;
	}
	public int getM_xpos() {
		return m_xpos;
	}
	public void setM_xpos(int m_xpos) {
		this.m_xpos = m_xpos;
	}
	public int getM_ypos() {
		return m_ypos;
	}
	public void setM_ypos(int m_ypos) {
		this.m_ypos = m_ypos;
	}
	public void packDate(SendMsgBuffer buffer) {
		// TODO Auto-generated method stub
		buffer.Add(m_id);
		buffer.Add(m_playerId);
		buffer.Add(m_xpos);
		buffer.Add(m_ypos);
		
	}
	
}
