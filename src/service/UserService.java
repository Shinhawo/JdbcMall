package service;

import controller.LoginUser;
import dao.UserDao;
import vo.User;

public class UserService {

	private UserDao userDao = new UserDao();
	

	public void registerUser(User user)  {
		User savedUser = userDao.getUserById(user.getId());
		if (savedUser != null) {
			throw new RuntimeException("["+user.getId()+"]는 이미 사용중인 아이디입니다.");
		}
		
		userDao.insertUser(user);
	}
	
	public LoginUser login(String userId, String password) {
		
		User savedUser = userDao.getUserById(userId);
		if(savedUser == null) {
			throw new RuntimeException("["+userId+"] 가입되어 있지 않은 아이디 입니다.");
		}
		
		if(!savedUser.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		} 

		return new LoginUser(savedUser.getNo(), savedUser.getId(), savedUser.getName());
	}
	
	
}
