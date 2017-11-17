package logic.loader;

import core.DBLoaderEx;
import logic.userdata.zz_huiyuan;
import logic.userdata.zz_sheng2;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class shengLoader extends DBLoaderEx<zz_sheng2> {


    public shengLoader(zz_sheng2 p_Seed) {
        super(p_Seed);
    }

    public shengLoader(zz_sheng2 p_Seed, boolean p_bSave) {
        super(p_Seed, p_bSave);
    }

    public ConcurrentLinkedQueue<zz_sheng2> getCenterDate() {
        // TODO Auto-generated method stub
        return m_Datas;

    }
    public String getSheng(String id) {
        for (zz_sheng2 m_data : this.m_Datas) {
            if (m_data.p_id.Get()==(id)) {
                return m_data.p_nm.Get();
            }
        }
        return "未知";
    }

    @Override
    public void OnTick(long p_lTimerID) throws Exception {

    }
}
