package controller;

import java.sql.SQLException;

import service.CartService;
import service.OrderService;
import service.ProductService;
import service.UserService;
import util.KeyboardReader;
import vo.User;

public class MallController {

	private KeyboardReader keyboardReader = new KeyboardReader();
	
	private CartService cartService = new CartService();
	private OrderService orderService = new OrderService();
	private ProductService productService = new ProductService();
	private UserService userService = new UserService();
	
	// 인증된 사용자정보가 저장된다.(loginUser가 null 아니면 현재 로그인된 상태다.)
	private LoginUser loginUser = null;
	
	public void menu() {
		System.out.println("-----------------------------------------------------");
		if (loginUser == null) {
			System.out.println("1.상품조회  2.로그인  3.회원가입  0.종료");			
		} else {
			System.out.println("1.쇼핑  2.장바구니  3.주문  4.내정보  5.로그아웃  0.종료");
			System.out.println("-----------------------------------------------------");
			System.out.println("["+loginUser.getName()+"]님 환영합니다⊹꒰⍢⑅ ꒱꙳");
		}
		System.out.println("-----------------------------------------------------");
		
		System.out.println();
		System.out.print("### 메뉴선택: ");
		int menu = keyboardReader.readInt();
		System.out.println();
		
		try {
			if (menu == 0) {
				System.out.println("<< 프로그램 종료 >>");
				System.out.println("### 프로그램을 종료합니다.");
				System.exit(0);
			}
			
			if (loginUser == null) {
				if (menu == 1) {
					상품조회();
				} else if (menu == 2) {
					로그인();
				} else if (menu == 3) {
					회원가입();
				}
			} else {
				if (menu == 1) {
					쇼핑();
				} else if (menu == 2) {
					장바구니();
				} else if (menu == 3) {
					주문();
				} else if (menu == 4) {
					내정보();
				} else if (menu == 5) {
					로그아웃();
				}
			}
		} catch (RuntimeException ex) {
			System.out.println("[오류발생] " + ex.getMessage());
			ex.printStackTrace(System.out);
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		menu();
	}
	
	private void 쇼핑() {
		System.out.println("<< 쇼핑 >>");
		System.out.println("---------------------------------------------");
		System.out.println("1.상품조회  2.바로구매하기  3.장바구니담기  0.종료");
		System.out.println("---------------------------------------------");
		
		System.out.println();
		System.out.print("### 쇼핑메뉴 선택: ");
		int menu = keyboardReader.readInt();
		System.out.println();
		
		if (menu == 1) {
			상품조회();
		} else if (menu == 2) {
			바로구매하기();
		} else if (menu == 3) {
			장바구니담기();
		} else if (menu == 0) {
			System.out.println("### 메인 메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println();
		System.out.println();
		쇼핑();
	}
	
	private void 장바구니() {
		System.out.println("<< 장바구니 >>");
		System.out.println("---------------------------------------------");
		System.out.println("1.장바구니보기  2.주문하기  3.비우기  0.종료");
		System.out.println("---------------------------------------------");
		
		System.out.println();
		System.out.print("### 장바구니메뉴 선택: ");
		int menu = keyboardReader.readInt();
		System.out.println();
		
		if (menu == 1) {
			장바구니보기();
		} else if (menu == 2) {
			장바구니에서구매하기();
		} else if (menu == 3) {
			장바구니비우기();
		} else if (menu == 0) {
			System.out.println("### 메인 메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println();
		System.out.println();
		장바구니();
	}
	
	private void 주문() {
		System.out.println("<< 주문 >>");
		System.out.println("---------------------------------------------");
		System.out.println("1.내역보기  2.상세정보보기 3.포인트내역보기  0.종료");
		System.out.println("---------------------------------------------");
		
		System.out.println();
		System.out.print("### 주문메뉴 선택: ");
		int menu = keyboardReader.readInt();
		System.out.println();
		
		if (menu == 1) {
			내주문내역조회();
		} else if (menu == 2) {
			주문상제정보조회();
		} else if (menu == 3) {
			포인트변경이력조회();
		} else if (menu == 0) {
			System.out.println("### 메인 메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println();
		System.out.println();
		주문();
	}
	
	private void 내정보() {
		System.out.println("<< 내 정보 >>");
		System.out.println("---------------------------------------------");
		System.out.println("1.내정보보기  0.종료");
		System.out.println("---------------------------------------------");
		
		System.out.println();
		System.out.print("### 내 정보메뉴 선택: ");
		int menu = keyboardReader.readInt();
		System.out.println();
		
		if (menu == 1) {
			내정보보기();
		} else if (menu == 0) {
			System.out.println("### 메인 메뉴로 돌아갑니다.");
			return;
		} 
		
		System.out.println();
		System.out.println();
		내정보();
	}
	
	
	
	
	
	private void 회원가입()  {
		System.out.println("<< 회원가입 >>");
		
		System.out.println("### 아이디, 비밀번호, 이름을 입력하여 회원가입을 하세요.");
		System.out.println();
		
		System.out.println("### 아이디 입력 :");
		String Id = keyboardReader.readString();
		System.out.println("### 비밀번호 입력 :");
		String password = keyboardReader.readString();
		System.out.println("### 이름 입력 :");
		String name = keyboardReader.readString();
		
		// 생성해야 담을 수 있다.
		User user = new User();
		user.setId(Id);
		user.setPassword(password);
		user.setName(name);
		userService.registerUser(user);
		
		System.out.println("### 신규 사용자 등록이 완료되었습니다.");
	}
	
	private void 로그인() {
		System.out.println("<< 로그인 >>");
		
		System.out.println("### 아이디와 비밀번호를 입력하여 로그인하세요.");
		System.out.println();
		
		System.out.println("### 아이디 입력 :");
		String Id = keyboardReader.readString();
		System.out.println("### 비밀번호 입력 :");
		String password = keyboardReader.readString();
		
		// 로그인이 완료되면 LoginUser객체 하나를 획득한다.
		// LoginUser 타입의 멤버변수에 저장시킨다.
		loginUser = userService.login(Id, password);
		
		System.out.println("### 로그인이 완료되었습니다.");
		
	}
	
	private void 로그아웃() {
		System.out.println("<< 로그아웃 >>");
		
		// 인증된 사용자 정보를 삭제 >> loginUser에 있음
		loginUser = null;
		System.out.println("### 로그아웃되었습니다.");
	}
	
	private void 내정보보기() {
		System.out.println("<< 내 정보 보기 >>");
		
		// 이미 로그인 되어 있는 상태에서 확인하는 것이기 때문에 loginUser.getId()
		User user = userService.getUserDetail(loginUser.getId());
		System.out.println("### 사용자정보를 확인하세요.");
		System.out.println("-----------------------------------------------------");
		System.out.println("사용자 번호 : "+user.getNo());
		System.out.println("사용자 아이디 : "+user.getId());
		System.out.println("사용자 이름 : "+user.getName());
		System.out.println("사용자 포인트 : "+user.getPoint());
		System.out.println("가입일자 : "+user.getCreateDate());
		System.out.println("-----------------------------------------------------");
	}
	
	private void 상품조회() {
		System.out.println("<< 상품조회 >>");
		
	}
	
	private void 장바구니보기() {
		System.out.println("<< 장바구니 보기 >>");
		
	}
	
	private void 장바구니담기() {
		System.out.println("<< 장바구니 담기 >>");
		
	}
	
	private void 장바구니비우기() {
		System.out.println("<< 장바구니 비우기 >>");
		
	}
	
	private void 장바구니에서구매하기() {
		System.out.println("<< 장바구니에서 구매하기 >>");
		
	}
	
	private void 바로구매하기() {
		System.out.println("<< 바로 구매하기 >>");
		
	}
	
	private void 내주문내역조회() {
		System.out.println("<< 내 주문내역 조회 >>");
		
	}
	
	private void 주문상제정보조회() {
		System.out.println("<< 주문 상제정보 조회 >>");
		
	}
	
	private void 포인트변경이력조회() {
		System.out.println("<< 포인트 변경이력 조회 >>");
		
	}
	
	public static void main(String[] args) {
		new MallController().menu();
	}
}

