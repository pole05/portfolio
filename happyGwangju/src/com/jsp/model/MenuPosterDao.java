package com.jsp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp.common.DBManager;
import com.jsp.dto.MenuDto;
import com.jsp.dto.MenuPosterDto;

// dao는 하나만 만들고 controller에서 폼의 갯수만큼 써주면 된다

public class MenuPosterDao {
	public int insertImage(MenuPosterDto dto) {		// 이미지 넣기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO MENU_POSTER");
			sql.append("       (NO, P_NO, P_POSTER_O, P_POSTER_S)");
			// NO: 글 번호 	P_NO: 포스터 번호 (게시글마다 1부터 시작해야 한다)
			sql.append("   VALUES ");
			sql.append("       (?, (SELECT IFNULL(MAX(C.P_NO), 0) + 1 ");
			sql.append("						FROM MENU_POSTER C WHERE NO = ?), ?, ?) ");
			
			stmt = con.prepareStatement(sql.toString());
						
			stmt.setInt(1, dto.getNo());
			stmt.setInt(2, dto.getNo());
			stmt.setString(3, dto.getP_poster_o());
			stmt.setString(4, dto.getP_poster_s());
			
			// (SELECT IFNULL(MAX(P_NO), 0) + 1 FROM MENU WHERE NO = ?)
			
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

	public int updateImage(MenuPosterDto dto) { // 게시글 수정
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE MENU_POSTER SET P_POSTER_O = ? ,P_POSTER_S = ? WHERE NO = ? AND WHERE P_NO=?;");
			
			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, dto.getP_poster_o());
			stmt.setString(2, dto.getP_poster_s());
			stmt.setInt(3, dto.getNo());
			stmt.setInt(4, dto.getP_no());

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
	
	
	public List<MenuPosterDto> selectImageBySeqNo(int no) {	// 이미지 보여 주기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<MenuPosterDto> list = new ArrayList<>();
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NO, P_NO, P_POSTER_O, P_POSTER_S");
			sql.append("	FROM MENU_POSTER");
			sql.append("	WHERE NO = ?");
			sql.append("	ORDER BY P_NO;");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, no);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				MenuPosterDto dto = new MenuPosterDto();
				
				dto.setNo(rs.getInt("NO"));
				dto.setP_no(rs.getInt("P_NO"));
				dto.setP_poster_o(rs.getString("P_POSTER_O"));
				dto.setP_poster_s(rs.getString("P_POSTER_S"));
				
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
	
	
	public MenuPosterDto selectImage(int no, int p_no) {	// 이미지 '하나씩' 보여 주기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		MenuPosterDto dto = new MenuPosterDto();
		
		try {
			con = DBManager.getInstance().open();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NO, P_NO, P_POSTER_O, P_POSTER_S");
			sql.append("	FROM MENU_POSTER");
			sql.append("	WHERE NO = ? AND P_NO = ?");
			
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, no);
			stmt.setInt(2, p_no);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				dto.setP_poster_o(rs.getString("P_POSTER_O"));				
				dto.setP_poster_s(rs.getString("P_POSTER_S"));				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return dto;
	}
}