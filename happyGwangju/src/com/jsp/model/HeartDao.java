package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp.common.DBManager;
import com.jsp.dto.HeartDto;
import com.jsp.dto.MenuDto;

public class HeartDao {

	public int insertheart(HeartDto dto) { // 찜하기 눌렀을 때 찜 게시판에 등록
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO HEART");
			sql.append("       (ID, NO, H_ORDER)");
			sql.append("   VALUES ");
			sql.append("       (?, ?, (SELECT IFNULL(MAX(H.H_ORDER), 0) + 1 ");
			sql.append("       FROM HEART H WHERE ID = ?))");

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, dto.getId());
			stmt.setInt(2, dto.getNo());
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

		
	public List<HeartDto> selectBoard(int startRow, String id) { // 찜 리스트 보여주기
		// startRow: x페이지일 때 게시물 시작 번호
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<HeartDto> list = new ArrayList<>();

		// x개 묶어서 한페이지에 보여 주기
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("	H.H_ORDER, H.ID, M.* ");
			sql.append("	FROM HEART H JOIN MENU M ");
			sql.append("	ON H.NO = M.NO WHERE ID = ? ");
			sql.append("	ORDER BY H.H_ORDER DESC ");
			sql.append("	LIMIT ?, 3;");
			

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);
			stmt.setInt(2, startRow);

			rs = stmt.executeQuery();

			// 리스트에 보여줄 것
			while (rs.next()) {
				HeartDto dto = new HeartDto();

				dto.setNo(rs.getInt("No"));
				dto.setName(rs.getString("NAME"));
				

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
	
//	public List<HeartDto> selectheart(String id, int startRow) { // 찜 리스트로 보여주기(조회)
//		// startRow: x페이지일 때 게시물 시작 번호
//		Connection con = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//
//		List<HeartDto> list = new ArrayList<>();
//
//		// x개 묶어서 한페이지에 보여 주기
//		try {
//			con = DBManager.getInstance().open();
//			StringBuffer sql = new StringBuffer();
//			sql.append("SELECT *");
//			sql.append("  FROM HEART ");
//			sql.append("  WHERE ID = ?");
//			sql.append(" ORDER BY H_ORDER DESC");
//			sql.append(" LIMIT ?, 3"); // 게시물 x번부터 10개까지 뽑아온다
//
//			stmt = con.prepareStatement(sql.toString());
//			stmt.setString(1, id);
//			stmt.setInt(2, startRow); // list에서 startRow값이 정해짐
//
//			rs = stmt.executeQuery();
//
//			// 리스트에 보여줄 것
//			// ID, CATEGORY, NO, H_ORDER
//			while (rs.next()) {
//				HeartDto dto = new HeartDto();
//				dto.setId(rs.getString("ID"));
//				dto.setNo(rs.getInt("NO"));
//				dto.setH_order(rs.getInt("H_ORDER"));
//
//				list.add(dto);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			DBManager.getInstance().close(con, stmt, rs);
//		}
//		return list;
//	}

	public int getTotalCount(String id) { // 전체 게시물 개수 조회
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int totalCount = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS CNT FROM HEART WHERE ID = ?;");
		
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
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

	public int findHeart(String id, int no) { 	// 해당 아이디와 no에 해당하는 값이 db에 존재하는지 찾기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM HEART WHERE ID=? AND NO = ?");

			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, id);
			stmt.setInt(2, no);

			rs = stmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("No");			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return result;
	}

	public int getMaxSeqNo() { // 마지막 게시물 번호 찾아내기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int maxSeqNo = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MAX(H_ORDER) FROM HEART");

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

	public int deleteheart(HeartDto dto) { // 찜 삭제
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM HEART");
			sql.append("	WHERE ID = ? AND NO = ?");

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, dto.getId());
			stmt.setInt(2, dto.getNo());

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