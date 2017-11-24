package manager;

import core.DBMgr;
import logic.MyUser;
import logic.userdata.UserClass;

import java.util.ArrayList;
import java.util.Collections;


public class RankingManager {
    private static RankingManager instance;


    public static RankingManager getInstance() {

        if (instance == null) {
            instance = new RankingManager();
        }
        return instance;

    }

    public UserClass[] geByClass(int type, MyUser p_user) {

        String all = "SELECT * FROM class";
        String inSchool = "select * from class Where school = " + p_user.getSchool();
        String inshi = "select * from class Where shi=" + p_user.getCenterData().getM_huiyuan().shi2.Get();
        switch (type) {
            case 1:
                return DBMgr.ReadSQL(new UserClass(), all);
            case 2:
                System.out.println(inshi);
             return  DBMgr.ReadSQL(new UserClass(),inshi);
            case 3:
                return DBMgr.ReadSQL(new UserClass(), inSchool);
        }

        return null;
    }
    public ArrayList<UserClass> getRankingByClass(int type, MyUser p_user) {
        ArrayList<UserClass> list = new ArrayList<>();
        UserClass[] userClasses = this.geByClass(type, p_user);

    if(list!=null) {
        for (UserClass userClass : userClasses) {
            if (userClass.av_grade.Get() != 0) {
                list.add(userClass);
            }
        }
    }
        Collections.sort(list);
        return list;
    }


}


