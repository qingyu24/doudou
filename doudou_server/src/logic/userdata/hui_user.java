package logic.userdata;

import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.db.RoleDataBase;
import core.detail.impl.socket.SendMsgBuffer;
import logic.MyUser;
import manager.UserManager;

public class hui_user extends RoleDataBase {
    public DBLong RoleID;
    public DBInt schoolID;
    public DBString school;
    public DBInt grade;
    public DBInt banji;
    public DBInt score;
    public DBInt portrait;
    public DBString xm;
    public DBInt usertype;
    public DBString type;

    public void packDate(SendMsgBuffer buffer, MyUser p_user) {
        buffer.Add(this.RoleID.Get());  //id
        buffer.Add(this.xm.Get());//姓名
        buffer.Add(this.portrait.Get() == 0 ? 1 : this.portrait.Get());//头像ID
        buffer.Add(CountGrade.getInstance().getnewLevel(this.score.Get()));
        buffer.Add(CountGrade.getInstance().getnewStar(this.score.Get()));
        buffer.Add(this.grade.Get());
        buffer.Add(this.banji.Get());
        buffer.Add(UserManager.getInstance().hasFriend(p_user.GetRoleGID(), this.RoleID.Get()) ? 1 : 0);
        buffer.Add(this.usertype.Get() == 1 ? 1 : 0);

    }
    public void packDate(SendMsgBuffer buffer) {
        buffer.Add(RoleID.Get());
        buffer.Add(this.xm.Get());//姓名
        buffer.Add(this.portrait.Get() == 0 ? 1 : this.portrait.Get());//头像ID
        buffer.Add(CountGrade.getInstance().getnewLevel(this.score.Get()));
        buffer.Add(CountGrade.getInstance().getnewStar(this.score.Get()));
/*        buffer.Add(school.Get());*/
/*        buffer.Add(this.grade.Get());
        buffer.Add(this.banji.Get());*/

    }
}
