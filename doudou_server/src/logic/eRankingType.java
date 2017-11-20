package logic;

public enum eRankingType {
    ALL_CLASS, SCHOOL_CLASS, PERSON_INCLASS, PERCON_INSCHOOL, PERSON_INALL;

    public int ID() {
        switch (this) {
            case ALL_CLASS:
                return 1;
            case SCHOOL_CLASS:
                return 2;
            case PERSON_INCLASS:
                return 3;
            case PERCON_INSCHOOL:
                return 4;
            case PERSON_INALL:
                return 5;
        }
        return 0;
    }

}
