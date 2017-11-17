package manager;

import core.DBMgr;
import logic.userdata.UserClass;

import java.util.ArrayList;


public class RankingManager {
    private static RankingManager instance;

    public static RankingManager getInstance() {

        if (instance == null) {
            instance = new RankingManager();
        }
        return instance;

    }

    public ArrayList getRankingByClass() {
        new ArrayList<>();
//�����а༶������
        String allClass = "SELECT b.school,b.grade,b.banji,avg(a.Garde) AS av_grade,c.SchoolName AS name FROM account AS a ,zz_huiyuan AS  b ,zz_school AS c WHERE a.RoleID=b.RoleID AND b.school=c.id GROUP BY b.banji ,b.school,b.grade  ORDER BY avg(a.Garde) DESC";
        UserClass[] classes = DBMgr.ReadSQL(new UserClass(), allClass);
//�༶��ѧУ����
        String inSchool="";

        return null;
    }


}


