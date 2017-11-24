package logic.loader;

import core.DBLoaderEx;
import core.detail.impl.socket.SendMsgBuffer;
import jxl.read.biff.SharedDateFormulaRecord;
import logic.MyUser;
import logic.PackBuffer;
import logic.Reg;
import logic.userdata.CenterDateInterface;
import logic.userdata.hui_user;

import java.util.ArrayList;
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
        ArrayList<hui_user> list = new ArrayList<hui_user>();

        Iterator<hui_user> iterator = this.getCenterDate().iterator();
        int size = 0;//一共发送前几名
        int isbegin = 1;
        while (iterator.hasNext()) {
            hui_user next = iterator.next();
            if (match(next, list_type, p_user)) {
                list.add(next);
                if (size++ > 30) break;//一共发送多少人
                if (list.size() == 30) {

                    this.rankBuffer(list, CenterDateInterface.MID_RANKING_PERSON, isbegin, p_user, list_type);
                    list.clear();
                    isbegin = 0;
                }
            }
        }

/*        if (list.size() > 0)
            this.rankBuffer(list, CenterDateInterface.MID_RANKING_PERSON, isbegin, p_user, list_type);*/
    }

    private boolean match(hui_user next, int list_type, MyUser p_user) {

        switch (list_type) {
            case 1:
                return true;
            case 2:
                if (next.schoolID.Get() == p_user.getSchool()) return true;
                break;
            case 3:
                if (next.schoolID.Get() == p_user.getSchool() && next.banji.Get() == p_user.getNianJi().get(2))
                    return true;

        }
        return false;
    }

    public void rankBuffer(ArrayList<hui_user> list, int msg, int isbegin, MyUser user, int type) {
        SendMsgBuffer buffer = PackBuffer.GetInstance().Clear().AddID(Reg.CENTERDATA, msg);
/*        buffer.Add(isbegin);//是否*/
        buffer.Add(type);
        buffer.Add((short) (list.size()));
        for (hui_user hui_user : list) {
            hui_user.packDate(buffer);
        }
        buffer.Send(user);

    }

}
