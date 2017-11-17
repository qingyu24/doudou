package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
	private static Mysql instance;

    private static  Connection conn;
	public static Mysql getInstance() {
		if (instance == null) {
			instance = new Mysql();
		}
		return instance;

	}


	public Connection getConnect(){
	    if(conn==null){
	        conn=this.NewConnect();
        }
        return conn;
    }

	public  Connection NewConnect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功连接数据库");
		} catch (ClassNotFoundException e1) {
			System.out.println("数据库连接异常!");

			e1.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/qiuqiu";

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "");

            System.out.print("获取连接");
		
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}


}