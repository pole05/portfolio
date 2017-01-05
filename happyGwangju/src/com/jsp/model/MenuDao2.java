package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp.common.DBManager;
import com.jsp.dto.MenuDto;
import com.jsp.dto.ReviewDto;

public class MenuDao2 {

	public int insertMenu(MenuDto dto) { // 게시글 쓰기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO MENU");
			// sql.append(" (CATEGORY, NO, NAME, CALL, HOMEPAGE,"); // 13개
			// sql.append(" HOST, PLACE, STARTTIME, ENDTIME, M_TIME, MONEY,
			// CONTENT, HEART)");
			sql.append("   VALUES ");
			sql.append("       (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			/*
			 * SELECT IFNULL(MAX(NO), 0) + 1 FROM MENU WHERE CATEGORY = ? MENU
			 * 테이블의 ?? 카테고리의 항목들을 찾은 후 NO의 최대값행을 조회한다. 근데 만약 존재하지 않는다면(NULL) 0을
			 * 넣는다. NO=그 최대값에 1을 더한 것을 조회한다 ※ IFNULL(필드명, "대체할 값")
			 */

			stmt = con.prepareStatement(sql.toString());

			stmt.setInt(1, dto.getCategory());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getCall());
			stmt.setString(4, dto.getHomepage());
			stmt.setString(5, dto.getHost());
			stmt.setString(6, dto.getPlace());
			stmt.setString(7, dto.getStarttime());
			stmt.setString(8, dto.getEndtime());
			stmt.setString(9, dto.getM_time());
			stmt.setString(10, dto.getMoney());
			stmt.setString(11, dto.getContent());

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

	public List<MenuDto> selectBoard(int startRow, String order, String id) { // 전체 리스트 보여주기
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<MenuDto> list = new ArrayList<>();

		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT T.*");
			sql.append("	FROM (SELECT");
			sql.append("		(select");
			sql.append("			count(*)");
			sql.append("	from");
			sql.append("		menu_comment");
			sql.append("	where");
			sql.append("			no = M.NO) as comment_cnt,");
			sql.append("	(select count(*) from heart where no = M.NO) as heart_cnt,");
			sql.append("	(SELECT COUNT(*)");
			sql.append("       FROM HEART");
			sql.append("      WHERE ID = ?");
			sql.append("        AND NO = M.NO) as heart_yn,");
			sql.append("	CASE CATEGORY");
			sql.append("		WHEN 1 THEN '공연'");
			sql.append("		WHEN 2 THEN '전시'");
			sql.append("		WHEN 3 THEN '축제행사'");
			sql.append("		WHEN 4 THEN '영화'");
			sql.append("		WHEN 5 THEN '기타'");
			sql.append("	END AS CATE_S,");
			sql.append("	M.*");
			sql.append("	FROM");
			sql.append("		MENU M ) T");
			sql.append("	ORDER BY T." + order + " DESC	");
			sql.append("	LIMIT ?, 6;"); // 게시물 x번부터 10개까지 뽑아온다

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id); // list에서 startRow값이 정해짐
			stmt.setInt(2, startRow); // list에서 startRow값이 정해짐

			rs = stmt.executeQuery();

			// 리스트에 보여줄 것
			while (rs.next()) {
				MenuDto dto = new MenuDto();

				dto.setNo(rs.getInt("No"));
				dto.setCategory(rs.getInt("CATEGORY"));
				dto.setCategory_s(rs.getString("CATE_S"));
				dto.setName(rs.getString("NAME"));
				dto.setPlace(rs.getString("PLACE"));
				dto.setStarttime(rs.getString("STARTTIME"));
				dto.setEndtime(rs.getString("ENDTIME"));
				dto.setCnt(rs.getInt("COMMENT_CNT"));
				dto.setHeartYn(rs.getInt("HEART_YN"));
				dto.setHeartCnt(rs.getInt("HEART_CNT"));

				// dto.setP_poster_s(rs.getString("P_POSTER_S"));

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

	public List<MenuDto> categoryBoard(String id, int startRow, int category, String order) {	// 카테고리별 보기
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<MenuDto> list = new ArrayList<>();

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT T.*");
			sql.append("	FROM (SELECT");
			sql.append("		(select");
			sql.append("			count(*)");
			sql.append("	from");
			sql.append("		menu_comment");
			sql.append("	where");
			sql.append("			no = M.NO) as comment_cnt,");
			sql.append("	(select count(*) from heart where no = M.NO) as heart_cnt,");
			sql.append("	(SELECT COUNT(*)");
			sql.append("       FROM HEART");
			sql.append("      WHERE ID = ?");
			sql.append("        AND NO = M.NO) as heart_yn,");
//			sql.append("	CASE CATEGORY");
//			sql.append("		WHEN 1 THEN '공연'");
//			sql.append("		WHEN 2 THEN '전시'");
//			sql.append("		WHEN 3 THEN '축제행사'");
//			sql.append("		WHEN 4 THEN '영화'");
//			sql.append("		WHEN 5 THEN '기타'");
//			sql.append("	END AS CATE_S,");
			sql.append("	M.*");
			sql.append("	FROM");
			sql.append("		MENU M ");
			sql.append("	WHERE CATEGORY = ?) T");
			sql.append("	ORDER BY T." + order + " DESC	");
			sql.append("	LIMIT ?, 6;");	

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id); // list에서 startRow값이 정해짐
			stmt.setInt(2, category); // list에서 startRow값이 정해짐
			stmt.setInt(3, startRow);

			rs = stmt.executeQuery();

			// 리스트에 보여줄 것
			while (rs.next()) {
			MenuDto dto = new MenuDto();

			dto.setNo(rs.getInt("No"));
			dto.setCategory(rs.getInt("CATEGORY"));
//			dto.setCategory_s(rs.getString("CATE_S"));
			dto.setName(rs.getString("NAME"));
			dto.setPlace(rs.getString("PLACE"));
			dto.setStarttime(rs.getString("STARTTIME"));
			dto.setEndtime(rs.getString("ENDTIME"));
			dto.setCnt(rs.getInt("COMMENT_CNT"));
			dto.setHeartYn(rs.getInt("HEART_YN"));
			dto.setHeartCnt(rs.getInt("HEART_CNT"));

			// dto.setP_poster_s(rs.getString("P_POSTER_S"));

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
		
	public int categoryCount(int category) {	//TODO 카테고리별 totalCount
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM MENU WHERE CATEGORY = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, category); // list에서 startRow값이 정해짐
			
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
	
	public List<MenuDto> selectMainBoard(String id) {	// 메인에서 리스트 보여주기(4조회)
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<MenuDto> list = new ArrayList<>();
		
		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("select   (select count(*) from menu_comment where no = M.NO) as comment_cnt,");
			sql.append("	(select count(*) from heart where no = M.NO) as heart_cnt,");
			sql.append("	(SELECT COUNT(*) FROM HEART WHERE ID = ? AND NO = M.NO) as heart_yn,");
			sql.append("	M.* FROM MENU M where NO IN (SELECT MAX(NO) FROM MENU where category <= 4 AND CATEGORY >=1 GROUP BY CATEGORY)");
			sql.append("	ORDER BY NO limit 0,4;");			
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
						
			// 리스트에 보여줄 것
			while(rs.next()) {			
				MenuDto dto = new MenuDto();
				
				dto.setNo(rs.getInt("NO"));
				dto.setCategory(rs.getInt("CATEGORY"));
				dto.setName(rs.getString("NAME"));
				dto.setPlace(rs.getString("PLACE"));
				dto.setStarttime(rs.getString("STARTTIME"));
				dto.setEndtime(rs.getString("ENDTIME"));
				dto.setM_time(rs.getString("M_TIME"));
				dto.setCnt(rs.getInt("comment_CNT"));
				dto.setHeartYn(rs.getInt("heart_yn"));
				dto.setHeartCnt(rs.getInt("heart_cnt"));
				
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
	
	public MenuDto selectDetailBoard(String id, int no) { // 디테일 보여주기~
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		MenuDto dto = new MenuDto();

		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("	CASE CATEGORY");
			sql.append("	WHEN 1 THEN '공연'");
			sql.append("	WHEN 2 THEN '전시'");
			sql.append("	WHEN 3 THEN '축제행사'");
			sql.append("	WHEN 4 THEN '영화'");
			sql.append("	WHEN 5 THEN '기타'");
			sql.append("	END AS CATE_S, ");
			sql.append("	(select count(*) from menu_comment where no = M.NO) as comment_cnt,");
			sql.append("	(SELECT COUNT(*) FROM HEART WHERE ID = ? AND NO = M.NO) as heart_yn, ");
			sql.append("	M.*");
			sql.append("	FROM MENU M");
			sql.append("	WHERE NO = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);
			stmt.setInt(2, no);

			rs = stmt.executeQuery();

			// 받아온 데이터를 리스트에 넣기
			if (rs.next()) {
				dto.setNo(rs.getInt("No"));
				dto.setCategory(rs.getInt("CATEGORY"));
				dto.setCategory_s(rs.getString("CATE_S"));
				dto.setName(rs.getString("NAME"));
				dto.setStarttime(rs.getString("STARTTIME"));
				dto.setEndtime(rs.getString("ENDTIME"));
				dto.setCall(rs.getString("M_CALL"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setHomepage(rs.getString("HOMEPAGE"));
				dto.setHost(rs.getString("HOST"));
				dto.setMoney(rs.getString("MONEY"));
				dto.setPlace(rs.getString("PLACE"));
				dto.setM_time(rs.getString("M_TIME"));
				dto.setHeartYn(rs.getInt("heart_yn"));
				dto.setComment_cnt(rs.getInt("comment_cnt"));
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

	public int getTotalCount() { // 전체 게시물 개수 조회
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int totalCount = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM MENU");

			stmt = con.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("CNT");
				// totalCount = rs.getInt(1);
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

	public int getMaxSeqNo() { // 마지막 게시물 번호 찾아내기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int maxSeqNo = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MAX(NO) FROM MENU");

			stmt = con.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

			if (rs.next()) {
				maxSeqNo = rs.getInt(1);
				// totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return maxSeqNo;
	}

	public int ModifyMenu(MenuDto dto) { // 게시글 수정 (UPDATE)
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		// CATEGORY, NAME, CALL, HOMEPAGE, HOST, PLACE,
		// STARTTIME, ENDTIME, TIME, MONEY, CONTENT // 11개
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE MENU SET ");
			sql.append(" CATEGORY = ?, ");
			sql.append(" NAME = ?, ");
			sql.append(" M_CALL = ?, ");
			sql.append(" HOMEPAGE = ?, ");
			sql.append(" HOST = ?, ");
			sql.append(" PLACE = ?, ");
			sql.append(" STARTTIME = ?, ");
			sql.append(" ENDTIME = ?, ");
			sql.append(" M_TIME = ?, ");
			sql.append(" MONEY = ?, ");
			sql.append(" CONTENT = ? ");
			sql.append(" 	WHERE NO = ?");
			// UPDATE JSP_BOARD SET TITLE = '수정된 데이터^^' WHERE SEQ_NO = 1;

			stmt = con.prepareStatement(sql.toString());

			stmt.setInt(1, dto.getCategory());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getCall());
			stmt.setString(4, dto.getHomepage());
			stmt.setString(5, dto.getHost());
			stmt.setString(6, dto.getPlace());
			stmt.setString(7, dto.getStarttime());
			stmt.setString(8, dto.getEndtime());
			stmt.setString(9, dto.getM_time());
			stmt.setString(10, dto.getMoney());
			stmt.setString(11, dto.getContent());
			stmt.setInt(12, dto.getNo());

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

	public int deleteMenu(int no) { // 게시글 삭제
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM MENU");
			sql.append(" WHERE NO = ?");

			stmt = con.prepareStatement(sql.toString());

			stmt.setInt(1, no);

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

	public List<MenuDto> findMenu(String text, int startRow) {	// 게시글 검색
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<MenuDto> list = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();

			sql.append("SELECT ");
			sql.append("	CASE CATEGORY");
			sql.append("	WHEN 1 THEN '공연'");
			sql.append("	WHEN 2 THEN '전시'");
			sql.append("	WHEN 3 THEN '축제행사'");
			sql.append("	WHEN 4 THEN '영화'");
			sql.append("	WHEN 5 THEN '기타'");
			sql.append("	END AS CATE_S,");
			sql.append("	M.*");			
			sql.append("	 , (select count(*) from menu_comment where no = M.NO) as cnt");
			sql.append("  FROM MENU M");
			sql.append(" WHERE NAME LIKE CONCAT('%', ?, '%')");
			sql.append("    OR PLACE LIKE CONCAT('%', ?, '%')");
			sql.append("	OR HOST LIKE CONCAT('%', ?, '%')");
			sql.append("	OR CONTENT LIKE CONCAT('%', ?, '%')");
			sql.append(" ORDER BY NO DESC	");
			sql.append(" LIMIT ?, 6");
				
		try {
			con = DBManager.getInstance().open();
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, text);
			stmt.setString(2, text);
			stmt.setString(3, text);
			stmt.setString(4, text);
			stmt.setInt(5, startRow);
			
			rs = stmt.executeQuery();
			
			// 리스트에 보여줄 것
			//R_SEQ, R_TITLE, ID, R_CONTENT, R_DATE, R_PARENT_SEQ, R_ORDER, R_DEPTH
			while(rs.next()) {			
				MenuDto dto = new MenuDto();
				dto.setNo(rs.getInt("No"));
				dto.setCategory(rs.getInt("CATEGORY"));
				dto.setCategory_s(rs.getString("CATE_S"));
				dto.setName(rs.getString("NAME"));
				dto.setPlace(rs.getString("PLACE"));
				dto.setStarttime(rs.getString("STARTTIME"));
				dto.setEndtime(rs.getString("ENDTIME"));
				dto.setCnt(rs.getInt("CNT"));
				
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
	
	public int findCount(String text) {	// 검색 totalCount
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS CNT FROM MENU");
		sql.append("	WHERE NAME LIKE CONCAT('%', ?, '%')");
		sql.append("    	OR PLACE LIKE CONCAT('%', ?, '%')");
		sql.append("		OR HOST LIKE CONCAT('%', ?, '%')");
		sql.append("		OR CONTENT LIKE CONCAT('%', ?, '%')");
		
		try {
			con = DBManager.getInstance().open();
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, text);
			stmt.setString(2, text);
			stmt.setString(3, text);
			stmt.setString(4, text);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {			
				totalCount = rs.getInt("CNT");
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
}