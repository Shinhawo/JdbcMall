package controller;
// 로그인된 사용자의 정보를 담기 위해 만들었다. DB와는 상관 없음

public class LoginUser {

	private int no;
	private String id;
	private String name;
	
	public LoginUser(int no, String id, String name) {
		super();
		this.no = no;
		this.id = id;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	
}
