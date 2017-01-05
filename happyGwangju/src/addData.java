import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jsp.common.DBManager;

public class addData {
	public static void main(String[] args) {

		// 메뉴 글 넣기
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		int result = 0;

		for (int i = 0; i <= 2; i++) {
			try {
				con = DBManager.getInstance().open();
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO MENU");
				// sql.append(" (CATEGORY, NO, NAME, CALL, HOMEPAGE,"); // 13개
				// sql.append(" HOST, PLACE, STARTTIME, ENDTIME, M_TIME, MONEY,
				// CONTENT, HEART)");
				sql.append("   VALUES ");
				sql.append("       (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

				stmt = con.prepareStatement(sql.toString());

				stmt.setInt(1, 1);
				stmt.setString(2, "name");
				stmt.setString(3, "000-0000-0000");
				stmt.setString(4, "http://");
				stmt.setString(5, "주최");
				stmt.setString(6, "장소");
				stmt.setString(7, "2016-05-05");
				stmt.setString(8, "2016-05-05");
				stmt.setString(9, "15:00");
				stmt.setInt(10, 3000);
				stmt.setString(11, "내용이지요 ㅎㅎㅎㅎ");
				// stmt.setInt(12, dto.getHeart());

				result = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				DBManager.getInstance().close(con, stmt, rs);
			}
		}

	}
}
