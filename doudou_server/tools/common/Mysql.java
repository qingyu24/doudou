package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
	private static Mysql instance;

	public static Mysql getInstance() {
		if (instance == null) {
			instance = new Mysql();
		}
		return instance;

	}

	public  Connection getConnect() {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");
		} catch (ClassNotFoundException e1) {
			System.out.println("找不到MySQL驱动!");
			e1.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/qiuqiu"; // JDBC的URL
		// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "");
			// 创建一个Statement对象
			/*
			 * Statement stmt = conn.createStatement(); //创建Statement对象
			 */System.out.print("成功连接到数据库！");
			/*
			 * stmt.close(); conn.close();
			 */
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}