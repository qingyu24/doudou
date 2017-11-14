package logic.userdata;

import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;

/**
 * @author niuhao
 * @version 0.0.1
 * @create by zb_mysql_to_class.py
 * @time:Oct-25-17 12:17:36
 **/
public class zz_huiyuan extends RoleDataBase {
    public DBLong RoleID;

    public DBInt id;//

    public DBString username;//

    public DBString password;//

    public DBString addtime;//

    public DBString uptime;//

    public DBString ip;//

    public DBInt dl;//

    public DBString xm;//

    public DBString jiazhangname;//

    public DBInt usertype;//

    public DBInt userstatus;//

    public DBInt xb;//

    public DBInt school;//

    public DBInt grade;//

    public DBInt banji;//

    public DBString StartDate;//

    public DBString StopDate;//

    public DBString BirthDate;//

    public DBString dh;//

    public DBString sj;//

    public DBString QQ;//

    public DBString email;//

    public DBString dizhi;//

    public DBString yb;//

    public DBInt dengji;//

    public DBInt jjkaitong;//

    public DBInt jjcishu;//

    public DBInt iosid;//

    public DBInt pcid;//

    public DBInt pccishu;//

    public DBInt azid;//

    public DBInt azcishu;//

    public DBInt creatorid;//

    public DBInt creatortype;//

    public DBInt xueshengid;//

    public DBString courseclass;//

    public DBString sheng2;//

    public DBString shi2;//

    public DBString qu2;//

    public DBInt xuexiao2;//

    public DBInt addday;//

    public DBInt xuexiaoid;//

    public DBInt online;//

    public DBInt jiaofeiyueshu;//

    public DBString tikuanjine;//

    public DBString userheaderimg;//

    public DBInt jibie;//

    public DBInt jifen;//

    public DBInt gongsi;//

    public DBInt sheng;//

    public DBInt shi;//

    public DBString signintime;//

    public DBInt shifouyikayi;//

    public DBInt istixian;//

    public DBString login_token;//

    public DBString yuxile;//

    public DBString mingxiangsi;//

    public DBString tongbuketang;//

    public DBString zuoye_shuxue;//

    public DBString zuoye_yuwen;//

    public DBString zuoye_yingyu;//

    public DBString zuoye_wuli;//

    public DBString zuoye_huaxue;//

    public DBString zuoye_dili;//

    public DBString zuoye_lishi;//

    public DBString zuoye_shengwu;//

    public DBString zuoye_zhengzhi;//

    public DBString zuoye_kexue;//

    public DBString zuoye_lizong;//

    public DBString zuoye_wenzong;//

    public DBString qichuang;//

    public DBString shuijiao;//

    public DBString mingrizhixing;//

    public DBString wangzhe;//

    public DBString shengzici;//

    public DBString guoguan;//

    public DBString jishiyouxi;//

    public DBString qitazonghe;//

    public DBString jiazhangusername;//

    public DBInt isjiaqian;//

    public void packData(SendMsgBuffer buffer) {
        buffer.Add(id.Get());
        buffer.Add(username.Get());
        buffer.Add(password.Get());
        buffer.Add(addtime.Get());
        buffer.Add(uptime.Get());
        buffer.Add(ip.Get());
        buffer.Add(dl.Get());
        buffer.Add(xm.Get());
        buffer.Add(jiazhangname.Get());
        buffer.Add(usertype.Get());
        buffer.Add(userstatus.Get());
        buffer.Add(xb.Get());
        buffer.Add(school.Get());
        buffer.Add(grade.Get());
        buffer.Add(banji.Get());
        buffer.Add(StartDate.Get());
        buffer.Add(StopDate.Get());
        buffer.Add(BirthDate.Get());
        buffer.Add(dh.Get());
        buffer.Add(sj.Get());
        buffer.Add(QQ.Get());
        buffer.Add(email.Get());
        buffer.Add(dizhi.Get());
        buffer.Add(yb.Get());
        buffer.Add(dengji.Get());
        buffer.Add(jjkaitong.Get());
        buffer.Add(jjcishu.Get());
        buffer.Add(iosid.Get());
        buffer.Add(pcid.Get());
        buffer.Add(pccishu.Get());
        buffer.Add(azid.Get());
        buffer.Add(azcishu.Get());
        buffer.Add(creatorid.Get());
        buffer.Add(creatortype.Get());
        buffer.Add(xueshengid.Get());
        buffer.Add(courseclass.Get());
        buffer.Add(sheng2.Get());
        buffer.Add(shi2.Get());
        buffer.Add(qu2.Get());
        buffer.Add(xuexiao2.Get());
        buffer.Add(addday.Get());
        buffer.Add(xuexiaoid.Get());
        buffer.Add(online.Get());
        buffer.Add(jiaofeiyueshu.Get());
        buffer.Add(tikuanjine.Get());
        buffer.Add(userheaderimg.Get());
        buffer.Add(jibie.Get());
        buffer.Add(jifen.Get());
        buffer.Add(gongsi.Get());
        buffer.Add(sheng.Get());
        buffer.Add(shi.Get());
        buffer.Add(signintime.Get());
        buffer.Add(shifouyikayi.Get());
        buffer.Add(istixian.Get());
        buffer.Add(login_token.Get());
        buffer.Add(yuxile.Get());
        buffer.Add(mingxiangsi.Get());
        buffer.Add(tongbuketang.Get());
        buffer.Add(zuoye_shuxue.Get());
        buffer.Add(zuoye_yuwen.Get());
        buffer.Add(zuoye_yingyu.Get());
        buffer.Add(zuoye_wuli.Get());
        buffer.Add(zuoye_huaxue.Get());
        buffer.Add(zuoye_dili.Get());
        buffer.Add(zuoye_lishi.Get());
        buffer.Add(zuoye_shengwu.Get());
        buffer.Add(zuoye_zhengzhi.Get());
        buffer.Add(zuoye_kexue.Get());
        buffer.Add(zuoye_lizong.Get());
        buffer.Add(zuoye_wenzong.Get());
        buffer.Add(qichuang.Get());
        buffer.Add(shuijiao.Get());
        buffer.Add(mingrizhixing.Get());
        buffer.Add(wangzhe.Get());
        buffer.Add(shengzici.Get());
        buffer.Add(guoguan.Get());
        buffer.Add(jishiyouxi.Get());
        buffer.Add(qitazonghe.Get());
        buffer.Add(jiazhangusername.Get());
        buffer.Add(isjiaqian.Get());
    }
}

