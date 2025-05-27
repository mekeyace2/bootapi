package kr.it.ecms.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class se_Controller {

	@Autowired
	private se_service serice;
	
	@GetMapping("/web1.do")
	public String web1() {	//로그인
		
		return "/web1.html";
	}
	
	@PostMapping("/loginok.do")
	public String loginok() {	//로그인 확인 페이지
		return null;
	}
	
	@GetMapping("/web2.do")
	public String web2() {	//회원가입	
		return "/web2.html";
	}
	
	@PostMapping("/joinok.do")
	public String joinok(se_user dto) {
		int result = this.serice.join_insert(dto);
				
		return null;
	}
	
	
	@GetMapping("/adminlist.do")
	public String adminlist() {	//관리자 리스트 출력
		
		return "/adminlist.html";
	}

	
}
