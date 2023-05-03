package dao;

import java.security.cert.TrustAnchor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtil;
import vo.User;

public class UserDao {

	/*
	 * 단순 사용자정보 추가하기 - INSERT
	 * 
	 * 반환타입 : void
	 * 메서드명 : insertUser
	 * 매개변수 : User
	 * 아이디, 비밀번호, 이름이 포함된 User객체를 전달받아서 SAMPLE_USERS 테이블에 저장한다.
	 */
	
	public void insertUser(User user){
		
		// 1.insert 구문 SQL을 작성하시오.
		String sql = "INSERT INTO SAMPLE_USERS "
				+"(USER_NO, USER_ID, USER_PASSWORD, USER_NAME) "
				+"VALUES "
				+"(SAMPLE_USERS_SEQ.NEXTVAL, ?,?,?)";
		
		try {
			// 2.Connection을 획득하세요.
			Connection conn = ConnUtil.getConnection();
			
			// 3.PreparedStatement를 획득하세요.
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			
			// 4.?에 값을 바인딩하세요.
			ptsmt.setString(1, user.getId());
			ptsmt.setString(2, user.getPassword());
			ptsmt.setString(3, user.getName());
			
			// 5.SQL을 dbms로 전송해서 실행시키세요.
			ptsmt.executeUpdate();
			
			// 6.자원을 반납하세요.
			ptsmt.close();
			conn.close();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	} //insertUser

	public User getUserById(String userId) {
		String sql = "select * "
				        +  "from sample_users "
				        +  "where user_id = ?";

		try {
			User user = null;
			Connection conn = ConnUtil.getConnection();
			PreparedStatement ptmst = conn.prepareStatement(sql);
			
			// 전달받은 userId를 사용
			ptmst.setString(1, userId);
			
			ResultSet rs = ptmst.executeQuery();
			while(rs.next()) {
				// 객체를 생성해야 담을 수 있다.
				user = new User();
				user.setNo(rs.getInt("user_no"));
				user.setId(rs.getString("user_id"));
				user.setPassword(rs.getString("user_password"));
				user.setName(rs.getString("user_name"));
				user.setPoint(rs.getInt("user_point"));
				user.setCreateDate(rs.getDate("user_create_date"));
			}
			
			rs.close();
			ptmst.close();
			conn.close();
			
			return user;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public User getUserByNo(int userNo) {
		String sql = "select * "
				   + "from sample_users "
				   + "where user_no = ? ";
		
		try {
			User user = null;
			Connection conn = ConnUtil.getConnection();
			PreparedStatement ptmst = conn.prepareStatement(sql);
			
			// 전달받은 userId를 사용
			ptmst.setInt(1, userNo);
			
			ResultSet rs = ptmst.executeQuery();
			while(rs.next()) {
				// 객체를 생성해야 담을 수 있다.
				user = new User();
				user.setNo(rs.getInt("user_no"));
				user.setId(rs.getString("user_id"));
				user.setPassword(rs.getString("user_password"));
				user.setName(rs.getString("user_name"));
				user.setPoint(rs.getInt("user_point"));
				user.setCreateDate(rs.getDate("user_create_date"));
			}
			
			rs.close();
			ptmst.close();
			conn.close();
			
			return user;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
