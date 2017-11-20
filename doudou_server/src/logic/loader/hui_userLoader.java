package logic.loader;

import core.DBLoaderEx;
import core.detail.impl.socket.SendMsgBuffer;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.userdata.CenterDateInterface;
import logic.userdata.hui_user;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class hui_userLoader extends DBLoaderEx<hui_user> {


    public hui_userLoader(hui_user p_Seed) {
        super(p_Seed);
    }

    public hui_userLoader(hui_user p_Seed, boolean p_bSave) {
        super(p_Seed, p_bSave);
    }

    public ConcurrentLinkedQueue<hui_user> getCenterDate() {
        // TODO Auto-generated method stub
        return m_Datas;

    }


    public void sendRaning_All(int list_type, MyUser p_user) {
        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, CenterDateInterface.MID_RANKING_PERSON);

        Iterator<hui_user> it = this.getCenterDate().iterator();
        while (it.hasNext()) {
            hui_user next = it.next();
            next.packDate(buffer,p_user);
        }
    }
}
