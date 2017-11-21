package logic.loader;

import core.DBLoaderEx;
import logic.userdata.zz_huiyuan;
import logic.userdata.zz_school;
import logic.userdata.zz_school;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SchoolLoader extends DBLoaderEx<zz_school> {


    public SchoolLoader(zz_school p_Seed) {
        super(p_Seed);
    }



    public ConcurrentLinkedQueue<zz_school> getCenterDate() {
        // TODO Auto-generated method stub
        return m_Datas;

    }

    public SchoolLoader(zz_school p_Seed, boolean p_bSave) {
        super(p_Seed, p_bSave);
    }



    public String getSchool(int id) {
        for (zz_school m_data : this.m_Datas) {
            if (m_data.id.Get()==(id)) {
                return m_data.SchoolName.Get();
            }
        }
        return "未知֪";
    }
    @Override
    public void OnTick(long p_lTimerID) throws Exception {

    }
}
