
package manager;

import sun.security.jca.GetInstance;
import utils.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class RankingManager {
    private static RankingManager instance;

    public static RankingManager getInstance() {

        if(instance==null){
            instance=new RankingManager();
        }
        return instance;

    }
   public ArrayList getRankingByClass(){
        String sql="";
       Connection connect = Mysql.getInstance().getConnect();
       try {

           PreparedStatement statement = connect.prepareStatement(sql);

       } catch (SQLException e) {
           e.printStackTrace();
       }

       return null;
   }



}


