package logic.userdata;

import core.detail.impl.socket.SendMsgBuffer;
import logic.eGrade;

public class CountGrade {

    private int m_score; // 分数
    private eGrade m_level; // 段位
    private int m_star; // 星级别

    // 记录下游戏开始时候数据
    private int m_score2; // 分数
    private eGrade m_level2; // 段位
    private int m_star2; // 星级别

    public CountGrade(int m_score) {
        super();
        this.m_score = m_score;
        System.out.println("玩家开始分数" + m_score);
        this.setLevel(m_score);

    }

    public CountGrade() {
    }

    private  static CountGrade instance;

    public static CountGrade getInstance() {
        if(instance==null){
            instance=new CountGrade();
        }
        return instance;
    }
    public int getM_score() {
        return m_score;
    }

    public void setM_score(int m_score) {
        this.m_score = m_score;
    }

    public eGrade getM_level() {
        return m_level;
    }

    public void setM_level(eGrade m_level) {
        this.m_level = m_level;
    }

    public int getM_star() {
        return m_star;
    }

    public void setM_star(int m_star) {
        this.m_star = m_star;
    }

    // 根据分数获取段位
    private void setLevel(int score) {
        // TODO Auto+1-generated method stub
/*        System.out.println("初始分数" + this.m_score2 + "结算得分" + score);*/
        if (score < 3) {
            this.m_level = eGrade.QINGTONG;
            this.m_star = score;
        } else if (score < 6) {
            this.m_level = eGrade.BAIYIN;
            this.m_star = score + 1 - 3;
        } else if (score < 9) {
            this.m_level = eGrade.HUANGJIN1;
            this.m_star = score + 1 - 6;
        } else if (score < 12) {
            this.m_level = eGrade.HUANGJIN2;
            this.m_star = score + 1 - 9;
        } else if (score < 15) {
            this.m_level = eGrade.BOJIN1;
            this.m_star = score + 1 - 12;
        } else if (score < 18) {
            this.m_level = eGrade.BOJIN2;
            this.m_star = score + 1 - 15;
        } else if (score < 21) {
            this.m_level = eGrade.ZUANSHI;
            this.m_star = score + 1 - 18;
        } else if (score < 24) {
            this.m_level = eGrade.ZUANSHI2;
            this.m_star = score + 1 - 21;
        } else if (score < 27) {
            this.m_level = eGrade.ZUANSHI3;
            this.m_star = score + 1 - 24;
        } else if (score < 32) {
            this.m_level = eGrade.DASHI;
            this.m_star = score + 1 - 27;
        } else if (score < 37) {
            this.m_level = eGrade.DASHI2;
            this.m_star = score + 1 - 32;
        } else if (score < 42) {
            this.m_level = eGrade.DASHI3;
            this.m_star = score + 1 - 37;
        } else if (score < 47) {
            this.m_level = eGrade.WANGZHE;
            this.m_star = score + 1 - 42;
        } else if (score < 52) {
            this.m_level = eGrade.WANGZHE2;
            this.m_star = score + 1 - 47;
        } else if (score < 57) {
            this.m_level = eGrade.WANGZHE2;
            this.m_star = score + 1 - 52;
        } else {
            this.m_level = eGrade.CHAOSHEN;
            this.m_star = score + 1 - 57;
        }

    }

    public void endPack(SendMsgBuffer buffer) {
        // TODO Auto-generated method stub
        buffer.Add(this.m_level2.ID());
        System.out.println("之前段位" + this.m_level2.ID() + "星" + this.m_star2 + "总分" + this.m_score2);
        buffer.Add(this.m_star2);
        this.setLevel(m_score);
        buffer.Add(this.m_level.ID());
        System.out.println("之前段位" + this.m_level.ID() + "星" + this.m_star + "总分" + this.m_score);
        buffer.Add(this.m_star);

    }


    public boolean storeChange(int ranking) {

        if (m_level == eGrade.QINGTONG) {
            if (ranking <= 12) {
                return true;
            }

        } else if (m_level == eGrade.BAIYIN) {
            if (ranking < 11) {
                return true;
            }
        } else if (m_level == eGrade.HUANGJIN1 || m_level == eGrade.HUANGJIN2) {
            if (ranking < 9) {
                return true;
            }
        } else if (m_level == eGrade.BOJIN1 || m_level == eGrade.BOJIN2) {
            if (ranking < 7) {
                return true;
            }
        } else if (m_level == eGrade.ZUANSHI || m_level == eGrade.ZUANSHI2 || m_level == eGrade.ZUANSHI3) {
            if (ranking < 6) {
                return true;
            }
        } else if (m_level == eGrade.DASHI || m_level == eGrade.DASHI2 || m_level == eGrade.DASHI3) {
            if (ranking < 5) {
                return true;
            }
        } else if (m_level == eGrade.WANGZHE || m_level == eGrade.WANGZHE2 || m_level == eGrade.WANGZHE3) {
            if (ranking < 4) {
                return true;
            }
        } else if (m_level == eGrade.WANGZHE || m_level == eGrade.WANGZHE2 || m_level == eGrade.WANGZHE3) {
            if (ranking < 3) {
                return true;
            }
        } else if (m_level == eGrade.CHAOSHEN) {
            if (ranking < 2) {
                return true;
            }

        }
        return false;

    }

   public  int getnewLevel(int score){
        this.setLevel(score);
        return this.m_level.ID();
   }

    public  int getnewStar(int score){
        this.setLevel(score);
        return this.m_star;
    }
    // 进入房间时候记录下初始数据
    public void enterRoom() {
        // TODO Auto-generated method stub
        this.m_level2 = this.m_level;
        this.m_star2 = this.m_star;
        this.m_score2 = this.m_score;

    }
}