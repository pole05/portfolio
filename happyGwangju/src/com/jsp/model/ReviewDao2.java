package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp.common.DBManager;
import com.jsp.dto.ReviewDto;

public class ReviewDao2 {
	
	public int insertReview(ReviewDto dto) {		// 게시글 쓰기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO REVIEW");
			sql.append("       (R_SEQ, R_TITLE, ID, R_CONTENT, R_DATE, R_PARENT_SEQ, R_ORDER, R_DEPTH, R_HIT)");	// 8개
			sql.append("   VALUES ");
			sql.append("       (NULL, ?, ?, ?, NOW(), ?, ?, ?, 0)");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setString(1, dto.getTitle());
			stmt.setString(2, dto.getId());
			stmt.setString(3, dto.getContent());
			stmt.setInt(4, dto.getParent_seq());
			stmt.setInt(5, dto.getOrder());
			stmt.setInt(6, dto.getDepth());
			
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
	
	public List<ReviewDto> selectReview(int startRow) {	// 리스트로 보여주기(조회)
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<ReviewDto> list = new ArrayList<>();
		
		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT *");
			sql.append("  FROM REVIEW");
			sql.append(" ORDER BY R_PARENT_SEQ DESC, R_ORDER ASC");
			sql.append(" LIMIT ?, 10");	// 게시물 x번부터 10개까지 뽑아온다
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, startRow);	// list에서 startRow값이 정해짐
			
			rs = stmt.executeQuery();
			
			// 리스트에 보여줄 것
			//R_SEQ, R_TITLE, ID, R_CONTENT, R_DATE, R_PARENT_SEQ, R_ORDER, R_DEPTH
			while(rs.next()) {			
				ReviewDto dto = new ReviewDto();
				dto.setSeq(rs.getInt("R_SEQ"));
				dto.setTitle(rs.getString("R_TITLE"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("R_CONTENT"));
				dto.setDate(rs.getString("R_DATE"));
				dto.setParent_seq(rs.getInt("R_PARENT_SEQ"));
				dto.setOrder(rs.getInt("R_ORDER"));
				dto.setDepth(rs.getInt("R_DEPTH"));
				dto.setHit(rs.getInt("R_HIT"));
				
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
	
	public List<ReviewDto> findReview(String text, String searchtype, int startRow) {	// 게시글 검색
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ReviewDto> list = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();

		if(searchtype.equals("title")) {
			sql.append("SELECT *");
			sql.append("  FROM REVIEW");
			sql.append("  WHERE R_TITLE LIKE CONCAT('%', ?, '%')");
			sql.append(" ORDER BY R_PARENT_SEQ DESC, R_ORDER ASC");	
			sql.append(" LIMIT ?, 10");
		} else if(searchtype.equals("content")) {		
			sql.append("SELECT *");
			sql.append("  FROM REVIEW");
			sql.append("  WHERE R_CONTENT LIKE CONCAT('%', ?, '%')");
			sql.append(" ORDER BY R_PARENT_SEQ DESC, R_ORDER ASC");
			sql.append(" LIMIT ?, 10");
		} else if (searchtype.equals("id")){
			sql.append("SELECT *");
			sql.append("  FROM REVIEW");
			sql.append("  WHERE ID LIKE CONCAT('%', ?, '%')");
			sql.append(" ORDER BY R_PARENT_SEQ DESC, R_ORDER ASC");
			sql.append(" LIMIT ?, 10");
		}
		
		try {
			con = DBManager.getInstance().open();
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, text);
			stmt.setInt(2, startRow);
			
			rs = stmt.executeQuery();
			
			// 리스트에 보여줄 것
			//R_SEQ, R_TITLE, ID, R_CONTENT, R_DATE, R_PARENT_SEQ, R_ORDER, R_DEPTH
			while(rs.next()) {			
				ReviewDto dto = new ReviewDto();
				dto.setSeq(rs.getInt("R_SEQ"));
				dto.setTitle(rs.getString("R_TITLE"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("R_CONTENT"));
				dto.setDate(rs.getString("R_DATE"));
				dto.setParent_seq(rs.getInt("R_PARENT_SEQ"));
				dto.setOrder(rs.getInt("R_ORDER"));
				dto.setDepth(rs.getInt("R_DEPTH"));
				dto.setHit(rs.getInt("R_HIT"));
				
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
	
	public List<ReviewDto> getMyboard(String id) {	// 내 게시물 관리
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ReviewDto> list = new ArrayList<>();
		
		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT *");
			sql.append("  FROM REVIEW");
			sql.append("  WHERE ID = ?");
			sql.append(" ORDER BY R_PARENT_SEQ DESC, R_ORDER ASC");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);	// list에서 startRow값이 정해짐
			
			rs = stmt.executeQuery();
			
			// 리스트에 보여줄 것
			//R_SEQ, R_TITLE, ID, R_CONTENT, R_DATE, R_PARENT_SEQ, R_ORDER, R_DEPTH
			while(rs.next()) {			
				ReviewDto dto = new ReviewDto();
				dto.setSeq(rs.getInt("R_SEQ"));
				dto.setTitle(rs.getString("R_TITLE"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("R_CONTENT"));
				dto.setDate(rs.getString("R_DATE"));
				dto.setParent_seq(rs.getInt("R_PARENT_SEQ"));
				dto.setOrder(rs.getInt("R_ORDER"));
				dto.setDepth(rs.getInt("R_DEPTH"));
				dto.setHit(rs.getInt("R_HIT"));
				
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
	
	public int updateReviewByHit(int seq) { // 조회수 UPDATE
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REVIEW SET");
			sql.append("       R_HIT = R_HIT + 1 ");
			sql.append(" WHERE R_SEQ = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq);

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

	/**
	 * 게시물 작성 후 Parent_Seq 수정
	 */
	public int updateReviewByParentSeq(int seq) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REVIEW SET");
			sql.append("       R_PARENT_SEQ = ?");
			sql.append(" WHERE R_SEQ = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq);
			stmt.setInt(2, seq);
			
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
	
	/**
	 * 답변글 작성시 현재 order 이상인 게시글 order + 1로 수정
	 */
	public int updateReviewByOrderPlus(int seq, int pSeq, int order) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REVIEW SET");
			sql.append("       R_ORDER = R_ORDER + 1");
			sql.append(" WHERE R_PARENT_SEQ = ? AND R_ORDER >= ? AND R_SEQ != ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, pSeq);
			stmt.setInt(2, order);
			stmt.setInt(3, seq);
			
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
	
	public ReviewDto selectReviewDetail(int seq) { // detail 보기 SELECT
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		ReviewDto dto = new ReviewDto();

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM REVIEW");
			sql.append(" WHERE R_SEQ = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, seq);

			rs = stmt.executeQuery();

			if (rs.next()) {
				dto.setSeq(rs.getInt("R_SEQ"));
				dto.setTitle(rs.getString("R_TITLE"));
				dto.setId(rs.getString("ID"));
				dto.setContent(rs.getString("R_CONTENT"));
				dto.setDate(rs.getString("R_DATE"));
				dto.setParent_seq(rs.getInt("R_PARENT_SEQ"));
				dto.setOrder(rs.getInt("R_ORDER"));
				dto.setDepth(rs.getInt("R_DEPTH"));
				dto.setHit(rs.getInt("R_HIT"));
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
	
	public int getTotalCount(String id) { 	// 전체 게시물 개수 조회
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM REVIEW");
			if(id==null||id.equals("")){
				sql.append(";");
				stmt = con.prepareStatement(sql.toString());
			}else{
				sql.append("	where ID = ?;");
				stmt = con.prepareStatement(sql.toString());
				stmt.setString(1, id);
			}
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				totalCount = rs.getInt("CNT");	//TODO CNT = COUNT(*)
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return totalCount;
	}
	
	public int getTotalCount() { 	// 전체 게시물 개수 조회
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM REVIEW;");
			
			stmt = con.prepareStatement(sql.toString());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				totalCount = rs.getInt("CNT");	//TODO CNT = COUNT(*)
//				totalCount = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return totalCount;
	}
	
	public int getMaxSeqNo() { 		// 마지막 게시물 번호 찾아내기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int maxSeqNo = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MAX(R_SEQ) FROM REVIEW");
			
			stmt = con.prepareStatement(sql.toString());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				maxSeqNo = rs.getInt(1);
//				totalCount = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return maxSeqNo;
	}

	public int deleteReview(int seq) {			// 게시글 삭제
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM REVIEW");
			sql.append(" WHERE R_SEQ = ?");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setInt(1, seq);
			
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
	
	public int checkReply(int seq) {			// 답변의 유무 확인
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT *");
			sql.append("	FROM REVIEW");
			sql.append("	WHERE R_PARENT_SEQ = (SELECT R_PARENT_SEQ");
			sql.append("			FROM REVIEW R WHERE R_SEQ = ?)");
			sql.append("	AND R_ORDER > (SELECT R_ORDER");
			sql.append("			FROM REVIEW R WHERE R_SEQ = ?)");
			sql.append("	AND R_DEPTH > (SELECT R_DEPTH");
			sql.append("			FROM REVIEW R WHERE R_SEQ = ?)");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setInt(1, seq);
			stmt.setInt(2, seq);
			stmt.setInt(3, seq);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {			
				result = 1;
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

	public int editReview(ReviewDto dto) { // 게시글 수정 (UPDATE) // TODO
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REVIEW SET ");
			sql.append(" R_TITLE = ?, ");
			sql.append(" R_CONTENT = ? ");
			sql.append(" 	WHERE R_SEQ = ?");
			// UPDATE JSP_BOARD SET TITLE = '수정된 데이터^^' WHERE SEQ_NO = 1;

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, dto.getTitle());
			stmt.setString(2, dto.getContent());
			stmt.setInt(3, dto.getSeq());
			
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