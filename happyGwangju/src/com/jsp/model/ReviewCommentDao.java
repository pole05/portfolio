package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.jsp.common.DBManager;
import com.jsp.dto.ReviewCommentDto;

public class ReviewCommentDao {
	public int insertComment(ReviewCommentDto dto) {		// 댓글 쓰기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO REVIEW_COMMENT");
			sql.append("       (R_SEQ, C_NO, ID, C_CONTENT, C_TIME)");	// 5개
			// R_SEQ: 글 번호			C_NO: 댓글 번호 (게시글마다 1부터 시작해야 한다)
			sql.append("   VALUES ");
			sql.append("       (?, (SELECT IFNULL(MAX(R.C_NO), 0) + 1 ");
			sql.append("				 FROM REVIEW_COMMENT R WHERE R.R_SEQ = ?), ?, ?, NOW()) ");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setInt(1, dto.getSeq());
			stmt.setInt(2, dto.getSeq());		// TODO: 쿼리문에 들어가야 할 값은 R_SEQ
			stmt.setString(3, dto.getId());
			stmt.setString(4, dto.getContent());
			
			// (SELECT IFNULL(MAX(C_NO), 0) + 1 FROM MENU WHERE R_SEQ = ?)
			
			result = stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}

	public List<ReviewCommentDto> selectComment(int seq) { 		// 댓글 보여주기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ReviewCommentDto> list = new ArrayList<>();
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT R_SEQ, C_NO, ID, C_CONTENT, C_TIME");
			sql.append("  FROM REVIEW_COMMENT");
			sql.append(" WHERE R_SEQ = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq);
			
			rs = stmt.executeQuery();
			
			// R_SEQ, C_NO, ID, C_CONTENT, C_TIME
			while(rs.next()) {
				ReviewCommentDto dto = new ReviewCommentDto();
				dto.setSeq(rs.getInt("R_SEQ"));
				dto.setC_no(rs.getInt("C_NO"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("C_CONTENT"));
				dto.setTime(rs.getString("C_TIME"));

				list.add(dto);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;
	}

	public int selectCommentForCount(int seq) { 		// 댓글수
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*)");
			sql.append("  FROM REVIEW_COMMENT");
			sql.append(" WHERE R_SEQ = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq);
			
			rs = stmt.executeQuery();
			
			// R_SEQ, C_NO, ID, C_CONTENT, C_TIME
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}
	
	public int deleteComment(int seq, int c_no) {					// 댓글 삭제
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM REVIEW_COMMENT");
			sql.append(" WHERE R_SEQ = ? AND C_NO = ?");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setInt(1, seq);
			stmt.setInt(2, c_no);
			
			result = stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}

	public int countComment(int seq) {
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM REVIEW_comment WHERE R_SEQ = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq); // list에서 startRow값이 정해짐
			
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