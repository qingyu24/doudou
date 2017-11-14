package logic.userdata;

import core.db.DBInt;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

/**
 * @author niuhao
 * @version 0.0.1
 * @create by zb_mysql_to_class.py
 * @time:Oct-25-17 12:17:36
 **/
public class zz_sheng2 extends RoleDataBase {
    public DBInt id;//

    public DBString p_id;//

    public DBString p_nm;//

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(id.Get());
        buffer.Add(p_id.Get());
        buffer.Add(p_nm.Get());
    }
}

