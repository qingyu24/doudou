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
public class zz_school extends RoleDataBase {
    public DBInt id;//

    public DBString SchoolName;//

    public DBString quhao;//

    public DBInt MaxUser;//

    public DBInt Province;//

    public DBInt City;//

    public DBInt County;//

    public DBString Agentname;//

    public DBString addtime;//

    public DBInt creatorid;//

    public DBString str1;//

    public DBString str2;//

    public DBString str3;//

    public DBString logo;//

    public DBString banquan;//

    public DBString gsjs;//

    public DBString wenhua;//

    public DBString zhaopin;//

    public DBString lxwm;//

    public DBString zhaoshang;//

    public DBString xiaoshou;//

    public DBString yuming;//

    public DBString erweima;//

    public DBInt sftuijian;//

    public DBString longitude;//

    public DBString latitude;//

    public DBInt weixintype;//

    public DBString schoolallianceimage;//

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(id.Get());
        buffer.Add(SchoolName.Get());
        buffer.Add(quhao.Get());
        buffer.Add(MaxUser.Get());
        buffer.Add(Province.Get());
        buffer.Add(City.Get());
        buffer.Add(County.Get());
        buffer.Add(Agentname.Get());
        buffer.Add(addtime.Get());
        buffer.Add(creatorid.Get());
        buffer.Add(str1.Get());
        buffer.Add(str2.Get());
        buffer.Add(str3.Get());
        buffer.Add(logo.Get());
        buffer.Add(banquan.Get());
        buffer.Add(gsjs.Get());
        buffer.Add(wenhua.Get());
        buffer.Add(zhaopin.Get());
        buffer.Add(lxwm.Get());
        buffer.Add(zhaoshang.Get());
        buffer.Add(xiaoshou.Get());
        buffer.Add(yuming.Get());
        buffer.Add(erweima.Get());
        buffer.Add(sftuijian.Get());
        buffer.Add(longitude.Get());
        buffer.Add(latitude.Get());
        buffer.Add(weixintype.Get());
        buffer.Add(schoolallianceimage.Get());
    }
}

