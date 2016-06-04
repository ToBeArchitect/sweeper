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
			System.out.println("���������ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��������ʧ�ܣ�");
		}
		try {
			System.out.println("�������ݿ�ɹ���");
			return DriverManager.getConnection(dbURL, userName, userPwd);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("SQL Server����ʧ�ܣ�");
			return null;
		}
	}
}
