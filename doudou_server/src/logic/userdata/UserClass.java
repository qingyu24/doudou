package logic.userdata;

import core.db.DBFloat;
import core.db.DBInt;
import core.db.DBString;

public class UserClass {
/*    private DBString sheng;
    private DBString shi;
    private DBString qu;*/
    public DBInt school;
    public DBInt grade;
    public DBInt banji;
    public DBFloat av_grade;//Ãû´Î
    public  DBString  name;

    @Override
    public String toString() {
        return "UserClass{" + "school=" + school + ", grade=" + grade + ", banji=" + banji + ", av_grade=" + av_grade + ", name=" + name.Get() + '}';
    }
  public void   packdate(){

  }
}
