package logic.module.room;

import core.detail.impl.socket.SendMsgBuffer;
import logic.LogRecord;
import logic.LogRecords;

public class ThornBall {
    private int thId;
    private int xpos;
    private int ypos;
    private int weight;

    public ThornBall(int thId) {
        LogRecord.Log("新生成刺球"+thId);
        this.thId = thId;
        this.xpos = (int) (-100000 + Math.random() * 200000);
        this.ypos = (int) (-100000 + Math.random() * 200000);
        this.weight = (int) (Math.random() * 50 + 150);
    }

    public void packData(SendMsgBuffer s) {
        // TODO Auto-generated method stub
        s.Add(thId);
        s.Add(xpos);
        s.Add(ypos);
        s.Add(weight);

    }

    public int getThId() {
        return thId;
    }

    public void setThId(int thId) {
        this.thId = thId;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
