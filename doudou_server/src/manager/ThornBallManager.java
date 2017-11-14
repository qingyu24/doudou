package manager;

import logic.module.room.ThornBall;

import java.util.ArrayList;

public class ThornBallManager {
    private static ThornBallManager _instance;
    private int ballNumber;


    public ThornBallManager() {
        ballNumber = 0;
    }

    public static ThornBallManager getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return _instance = new ThornBallManager();
    }

    public ThornBall getNewThroBall() {
        // TODO Auto-generated method stub
        ballNumber++;
        return new ThornBall(ballNumber);
    }

    public ArrayList<ThornBall> getNewlist() {
        // TODO Auto-generated method stub
        ArrayList<ThornBall> list = new ArrayList<ThornBall>();
        while (list.size() < 10) {
            list.add(getNewThroBall());
        }
        return list;
    }


}
