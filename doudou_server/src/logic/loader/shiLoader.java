package logic.loader;

import core.DBLoaderEx;
import logic.userdata.zz_sheng2;
import logic.userdata.zz_shi2;

import java.util.ArrayList;

public class shiLoader extends DBLoaderEx<zz_shi2> {

    private static ArrayList<String> m_codes = new ArrayList<String>();
    private static String sql_add = "insert into charge_record(RoleID, TargetRoleID, card)values(%d, %d, %d)";
    private static String sql_rank = "select * from zz_sheng order by winCount desc limit %d";
    private static String sql_query_rank = "SELECT * FROM (SELECT (@rownum:=@rownum+1) AS rownum, a.RoleID FROM `zz_sheng` a, (SELECT @rownum:= 0 ) r  ORDER BY a.`winCount` DESC) AS b  WHERE RoleID = %d";

    public shiLoader(zz_shi2 p_Seed) {
        super(p_Seed);
    }


    public String getShi(String  id) {
        for (zz_shi2 m_data : this.m_Datas) {
            if (m_data.c_id.Get()==(id)) {
                return m_data.c_nm.Get();
            }
        }
        return null;
    }


}
