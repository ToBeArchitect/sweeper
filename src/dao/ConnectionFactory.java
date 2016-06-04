package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL = "jdbc:sqlserver://localhost:1434;DatabaseName=sweeper";
	static String userName = "sa";
	static String userPwd = "123456";

	public static Connection getConnection() {
		try {
			Class.forName(driverName);
			System.out.println("加载驱动成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载驱动失败！");
		}
		try {
			System.out.println("连接数据库成功！");
			return DriverManager.getConnection(dbURL, userName, userPwd);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("SQL Server连接失败！");
			return null;
		}
	}
}
