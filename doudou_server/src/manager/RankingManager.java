package manager;

import core.DBMgr;
import logic.MyUser;
import logic.userdata.UserClass;
import logic.userdata.hui_user;


public class RankingManager {
    private static RankingManager instance;


    public static RankingManager getInstance() {

        if (instance == null) {
            instance = new RankingManager();
        }
        return instance;

    }

    public UserClass[] getRankingByClass(int type, MyUser p_user) {

        String all = "SELECT * FROM class";
        String inSchool = "select * from class Where school = " + p_user.getSchool();
        switch (type) {
            case 1:
                return DBMgr.ReadSQL(new UserClass(), all);
            case 2:
                return DBMgr.ReadSQL(new UserClass(), inSchool);

        }

        return null;
    }


 /*   public hui_user getRankingPerson() {


    }*/
}


