package logic.userdata;

import core.db.DBFloat;
import core.db.DBInt;
import core.db.DBLong;
import core.db.DBString;
import core.detail.impl.socket.SendMsgBuffer;

public class UserClass implements Comparable<UserClass>{

    public DBInt school;
    public DBInt grade;
    public DBInt banji;
    public DBFloat av_grade;//名次
    public DBString schoolname;
    public DBString tname;
    public DBLong RoleID;
    public DBInt por;//队长头像
public  DBInt score;

    @Override
    public String toString() {
        return "UserClass{" +
                "school=" + school.Get() +
                ", grade=" + grade.Get() +
                ", banji=" + banji.Get() +
                ", av_grade=" + av_grade.Get() +
                ", schoolname=" + schoolname.Get() +
                ", tname=" + tname.Get() +
                ", RoleID=" + RoleID.Get() +
                ", por=" + por.Get() +
                '}';
    }

    public void packdate(SendMsgBuffer buffer) {
        buffer.Add(schoolname.Get());
        buffer.Add(grade.Get());
        buffer.Add(banji.Get());

        buffer.Add(por.Get()==0?1:por.Get());//
        buffer.Add(tname.Get());//名字
        buffer.Add(CountGrade.getInstance().getnewLevel(score.Get()));
        buffer.Add(CountGrade.getInstance().getnewStar(score.Get()));

        buffer.Add((int) av_grade.Get());//分数


  /*      buffer.Add(av_grade.Get());*/


    }

    @Override
    public int compareTo(UserClass o) {
        if(this.av_grade.Get()>o.av_grade.Get()){
            return -1;
        }else if(this.av_grade.Get()<o.av_grade.Get()){
            return  1;
        }
        return 0;
    }
}
