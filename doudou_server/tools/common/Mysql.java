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
			// ����Class.forName()����������������
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("�ɹ�����MySQL������");
		} catch (ClassNotFoundException e1) {
			System.out.println("�Ҳ���MySQL����!");
			e1.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/qiuqiu"; // JDBC��URL
		// ����DriverManager�����getConnection()���������һ��Connection����
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "");
			// ����һ��Statement����
			/*
			 * Statement stmt = conn.createStatement(); //����Statement����
			 */System.out.print("�ɹ����ӵ����ݿ⣡");
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