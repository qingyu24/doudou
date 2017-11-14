package logic.userdata;

import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

public class shopping extends RoleDataBase {
    public DBLong RoleID;//用户id

    public DBInt shopID;//商品
    public DBString time;//购买时间

    public DBInt price;//价钱

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(RoleID.Get());
        buffer.Add(shopID.Get());
        buffer.Add(time.Get());
        buffer.Add(price.Get());


    }
}