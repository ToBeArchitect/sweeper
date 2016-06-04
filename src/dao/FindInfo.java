package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import sweeper.MinesweeperGame;

public class FindInfo {
	//查询
	public static User findUser(String username) {
		User user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "select * from sweeper where userName=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setName(rs.getString(1));
				user.setPwd(rs.getString(2));
				user.setInfo(rs.getInt(3));
				MinesweeperGame.dblevel = rs.getInt(3);
				MinesweeperGame.name = rs.getString(1);
				System.out.println(user.getName() + ":" + user.getPwd());
				System.out.println(user.getInfo());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClose.close(rs, pstmt, conn);
		}
		return user;
	}
	
	//获取level
	public static int findLevel(String username) {
		User user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "select userInfo from sweeper where userName=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClose.close(rs, pstmt, conn);
		}
		return -1;
	}

	// 用户注册
	public static void addUser(User user) {
		System.out.println(user.getName() + "." + user.getPwd());
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();
			pstmt = conn.prepareStatement("insert into sweeper values(?,?,?)");
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPwd());
			pstmt.setInt(3, user.getInfo());	
			pstmt.executeUpdate();
			//pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClose.close(rs, pstmt, conn);
		}
	}

	// 刷新用户关数
	public static void addLevel(User u,int level) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "update sweeper set userInfo=? where userName=?";
			pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, username);
			// rs=pstmt.executeQuery();
			pstmt.setString(2, u.getName());
			pstmt.setInt(1, level);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClose.close(rs, pstmt, conn);
		}
	}
}
