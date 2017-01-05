package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp.common.DBManager;
import com.jsp.dto.MemberInfoDto;
import com.jsp.dto.MenuDto;

// Data Access Object
public class MemberInfoDao {
	public int insertPersonInfo(MemberInfoDto mDto) {		// 회원 가입...
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO MEMBERINFO");
			// sql.append("		(ID, NICKNAME, PW, NAME, EMAIL)");
			sql.append("     VALUES (?, NULL, ?, ?, ?)");

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, mDto.getId());
			stmt.setString(2, mDto.getPw());
			stmt.setString(3, mDto.getName());
			stmt.setString(4, mDto.getEmail());

			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}


	public MemberInfoDto SelectPersonInfo(String id) { // 회원 정보 조회
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		MemberInfoDto dto = new MemberInfoDto();

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("		ID, NICKNAME, PW, NAME, EMAIL");
			sql.append("     	FROM MEMBERINFO WHERE ID = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			// 리스트에 보여줄 것
			while (rs.next()) {
				dto.setId(rs.getString("ID"));
				dto.setPw(rs.getString("PW"));
				dto.setNickname(rs.getString("NICKNAME"));
				dto.setName(rs.getString("NAME"));
				dto.setEmail(rs.getString("EMAIL"));				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return dto;
	}

	public int UpdateMemberInfo(MemberInfoDto dto) { // 회원 정보 수정
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE MEMBERINFO");
			sql.append("	SET");
			sql.append("	NICKNAME = ?,");
			sql.append("	EMAIL = ?");
			sql.append("      	WHERE ID = ?");

			stmt = con.prepareStatement(sql.toString());
			
			stmt.setString(1, dto.getNickname());
			stmt.setString(2, dto.getEmail());
			stmt.setString(3, dto.getId());

			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}
	
	
	public int UpdatePw(String pw, String id) { // 비밀번호만 수정
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE MEMBERINFO");
			sql.append("	SET");
			sql.append("	PW = ?");			
			sql.append("    WHERE ID = ?");

			stmt = con.prepareStatement(sql.toString());
			
			stmt.setString(1, pw);
			stmt.setString(2, id);

			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}

	
	// 가입폼에서 중복 아이디 찾기
	public int checkId(String id) throws ClassNotFoundException, SQLException {
		Connection conn = DBManager.getInstance().open();
		String sql = "SELECT * FROM MEMBERINFO" + "	WHERE ID = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);

		ResultSet rs = stmt.executeQuery(); // select

		int re = 0;
		while (rs.next()) {
			// String id = rs.getString("ID");
			re = 1; // 아이디가 존재할 경우 ??
		}
		rs.close();
		stmt.close();
		conn.close();
		return re;
	}

	public String selectMemberInfoById(String id) {
		String pw = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DBManager.getInstance().open();
			String sql = "SELECT PW FROM MEMBERINFO" + " WHERE ID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			rs = stmt.executeQuery(); // select

			while (rs.next()) {
				pw = rs.getString("PW");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(conn, stmt, rs);
		}

		return pw;
	}

	public String findnick(String id) { // TODO 별명 불러오기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String nick = null;
		MenuDto dto = new MenuDto();

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NICKNAME FROM MEMBERINFO WHERE ID = ?;");

			stmt = con.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

			if (rs.next()) {
				nick = rs.getString("NICKNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return nick;
	}
	
	public int deleteMember(String id) { // 회원 탈퇴
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM MEMBERINFO");
			sql.append("	WHERE ID = ?");

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, id);

			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}
	
}