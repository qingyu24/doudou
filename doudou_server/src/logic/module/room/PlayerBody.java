package logic.module.room;

import core.Root;
import core.Tick;
import core.detail.impl.socket.SendMsgBuffer;
import logic.LogRecord;
import logic.LogRecords;

public class PlayerBody {

    private int m_id;
    private int m_xpos;
    private int m_ypos;
    private int m_xspeed;
    private int m_yspeed;
    private int m_weight;
    private int m_angle;
  /*  long m_timeid = Root.GetInstance().AddLoopMilliTimer(this, 1000 / 60, null);*/

    public PlayerBody(int id, int xpos, int ypos) {
        m_xpos = xpos;
        m_ypos = ypos;
        m_xspeed = 0;
        m_yspeed = 0;
        m_id = id;
        m_weight = 10;
        m_angle = 0;
/*		LogRecord.Log("chushihua坐标"+m_xpos+"/t"+m_ypos);*/
    }

    public PlayerBody(Integer integer) {
        m_id = integer;
    }

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(m_id);
        buffer.Add(m_xpos);
        buffer.Add(m_ypos);
        buffer.Add(m_xspeed);
        buffer.Add(m_yspeed);
        buffer.Add(m_weight);
        buffer.Add(m_angle);


    }

    @Override
    public String toString() {
        return "PlayerBody{" +
                "m_id=" + m_id +
                ", m_xpos=" + m_xpos +
                ", m_ypos=" + m_ypos +
                ", m_xspeed=" + m_xspeed +
                ", m_yspeed=" + m_yspeed +
                ", m_weight=" + m_weight +
                ", m_angle=" + m_angle +
                '}';
    }

    public void changeSpeed(int xp, int yp) {
        this.m_xspeed = xp;
        this.m_yspeed = yp;
    }

    public void updatePosition() {
        m_xpos += m_xspeed / 1000.0f * 0.2;
        m_ypos += m_yspeed / 1000.0f * 0.2;
    }

    public void updatePosition(int id,int m_xpos, int m_ypos, int m_xspeed, int m_yspeed, int m_weight, int m_angle) {
        this.m_id=id;
        this.m_xpos = m_xpos;
        this.m_xspeed = m_xspeed;
        this.m_ypos = m_ypos;
        this.m_yspeed = m_yspeed;
        this.m_weight = m_weight;
        this.m_angle = m_angle;

    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
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

    public int getM_xspeed() {
        return m_xspeed;
    }

    public void setM_xspeed(int m_xspeed) {
        this.m_xspeed = m_xspeed;
    }

    public int getM_yspeed() {
        return m_yspeed;
    }

    public void setM_yspeed(int m_yspeed) {
        this.m_yspeed = m_yspeed;
    }

    public int getM_weight() {
        return m_weight;
    }

    public void setM_weight(int m_weight) {
        this.m_weight = m_weight;
        if (m_weight == 0) {
            LogRecords.Log(null, "收到体重为空");
        }
    }

    public boolean near(PlayerBody b2) {
        // TODO Auto-generated method stub
/*		(this.m_xpos-b2.m_xpos)^2+(this.)*/
        return false;
    }


/*
    @Override
    public void OnTick(long l) throws Exception {
        m_xpos = m_xpos + m_xspeed * 1000 / 60;
        m_ypos = m_ypos + m_yspeed * 1000 / 60;
    }
*/

    public void destroy() {
        /*Root.GetInstance().RemoveTimer(this.m_timeid);*/

    }
}
