package logic.userdata;

import core.db.DBInt;
import core.db.DBLong;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

/**
 * @author niuhao
 * @version 0.0.1
 * @create by zb_mysql_to_class.py
 * @time:Oct-25-17 12:17:36
 **/
public class Friends extends RoleDataBase {
    public DBLong RoleID;//玩家ＩＤ

    public DBLong FriendID;//好友ＩＤ

    public DBLong time;//加好友的时间

    public DBInt Agree;//是否同意添加

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(RoleID.Get());
        buffer.Add(FriendID.Get());
        buffer.Add(time.Get());
        buffer.Add(Agree.Get());
    }
}

