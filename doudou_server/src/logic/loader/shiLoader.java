package logic.loader;

import core.DBLoaderEx;
import logic.userdata.zz_huiyuan;
import logic.userdata.zz_sheng2;
import logic.userdata.zz_shi2;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class shiLoader extends DBLoaderEx<zz_shi2> {

    private static ArrayList<String> m_codes = new ArrayList<String>();

    public shiLoader(zz_shi2 p_Seed) {
        super(p_Seed);
    }

    public ConcurrentLinkedQueue<zz_shi2> getCenterDate() {
        // TODO Auto-generated method stub
        return m_Datas;

    }

    public shiLoader(zz_shi2 p_Seed, boolean p_bSave) {
        super(p_Seed, p_bSave);
    }

    public String getShi(String  id) {
        for (zz_shi2 m_data : this.m_Datas) {
            if (m_data.c_id.Get()==(id)) {
                return m_data.c_nm.Get();
            }
        }
        return "未知";
    }
    @Override
    public void OnTick(long p_lTimerID) throws Exception {

    }

}
