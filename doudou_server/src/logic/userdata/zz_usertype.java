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
public class zz_usertype extends RoleDataBase {
    public DBInt ID;//

    public DBString title;//

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(ID.Get());
        buffer.Add(title.Get());
    }
}

