package logic.userdata;

import core.DBMgr;
import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

/**
 * @author niuhao
 * @version 0.0.1
 * @create by zb_mysql_to_class.py
 * @time:Aug-01-17 14:39:15
 **/
public class account extends RoleDataBase {
    public DBLong RoleID;//用户id

    public DBString Name;//

    public DBString Password;//

    public DBString Icon;//

    public DBInt Money;//

    public DBInt Garde;//段位

    public DBLong TeamID;//战队

    public DBString TickName;//网名

    public DBInt portrait;//头像ＩＤ

    public DBInt Skin;//皮肤

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(RoleID.Get());

        buffer.Add(TickName.Get());
        buffer.Add(portrait.Get());
        if(Skin.Get()==0){
            Skin.Set((int) Math.random()*3+101);
            DBMgr.ExecuteSQL("UPDATE  account SET  Skin ="+Skin.Get()+"Where RoleID="+RoleID.Get());
        }
        buffer.Add(Skin.Get());
        CountGrade grade = new CountGrade(Garde.Get());
        buffer.Add(grade.getM_level().ID());
        buffer.Add(grade.getM_star());
        buffer.Add(Money.Get());


    }
}

