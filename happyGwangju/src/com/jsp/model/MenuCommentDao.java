package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.jsp.common.DBManager;
import com.jsp.dto.MenuCommentDto;

public class MenuCommentDao {
	public int insertComment(MenuCommentDto dto) { // 댓글 쓰기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO MENU_COMMENT");
			sql.append("       (NO, C_NO, ID, C_CONTENT, C_TIME)");
			// NO: 글 번호 C_NO: 댓글 번호 (게시글마다 1부터 시작해야 한다)
			sql.append("   VALUES ");
			sql.append("       (?, (SELECT IFNULL(MAX(C.C_NO), 0) + 1 ");
			sql.append("	FROM MENU_COMMENT C WHERE NO = ?), ?, ?, ?) ");

			stmt = con.prepareStatement(sql.toString());

			stmt.setInt(1, dto.getNo());
			stmt.setInt(2, dto.getNo());
			stmt.setString(3, dto.getId());
			stmt.setString(4, dto.getContent());
			stmt.setString(5, dto.getTime());

			/*
			 * C_NO값 구하기 (SELECT IFNULL(MAX(C.C_NO), 0) + 1 FROM MENU_COMMENT C
			 * WHERE NO = ?)
			 */
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

	public List<MenuCommentDto> selectComment(int no) { // 댓글들 보여주기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<MenuCommentDto> list = new ArrayList<>();

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("	(select count(*) from menu_comment where no = M.NO) as cnt,");			
			sql.append("	NO, C_NO, ID, C_CONTENT, C_TIME");
			sql.append("	FROM MENU_COMMENT M");
			sql.append("	WHERE NO = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, no);

			rs = stmt.executeQuery();

			while (rs.next()) {
				MenuCommentDto dto = new MenuCommentDto();
				dto.setNo(rs.getInt("NO"));
				dto.setC_no(rs.getInt("C_NO"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("C_CONTENT"));
				dto.setTime(rs.getString("C_TIME"));
				dto.setC_count(rs.getInt("CNT"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;
	}

	public int deleteComment(int no, int c_no) { // 댓글 삭제
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM MENU_COMMENT");
			sql.append("	WHERE NO = ? AND C_NO = ?");

			stmt = con.prepareStatement(sql.toString());

			stmt.setInt(1, no);
			stmt.setInt(2, c_no);

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
	
	public int countComment(int no) {
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM MENU_comment WHERE NO = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, no); // list에서 startRow값이 정해짐
			
			rs = stmt.executeQuery();
			
			// 리스트에 보여줄 것
			while (rs.next()) {				
				totalCount = rs.getInt("CNT");
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return totalCount;
	}	

}