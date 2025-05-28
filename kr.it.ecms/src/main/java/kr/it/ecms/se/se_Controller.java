package kr.it.ecms.se;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class se_Controller {

	@Autowired
	private se_service serice;
	
	//anonymousUser : security에 로그인 없이 접근 하였을 경우 출력되는 임시 아이디
	/*
	 SecurityContextHolder : 핵심 클래스 이며, 로그인시 아이디 및 레벨권한 객체가 저장되는 역활 
	 getContext() : SecurityContext 안에 있는 객체에 접근 
	 getAuthentication() : 로그인 사용자에 대한 객체 정보에 대한 인증 목록을 가져옵니다.
	 getName() : 로그인 아이디값을 가져 오는 메소드 (getter)
	 */
	@GetMapping("/main.do")
	public String main(Model m) {
		String userid = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(userid);

		//레벨 권한에 대한 정보를 가져오기 위함
		//Authentication : 인증된 신원 (HTTP,HTTPS)
		//authorization : 인가된 신원 (권한 부여) => securityconfig.java에서 활용
		Authentication authc = SecurityContextHolder.getContext().getAuthentication();
		
		//스프링 스큐리티에서 가지고 있는 모든 권한 사항을 이관하게 됩니다. (배열)
		Collection<? extends GrantedAuthority> iter = authc.getAuthorities();
		//Collection = Iterator (동일하게 사용되는 interface)
		//Iterator : next(), hasnext(), remove() => 모든 배열에 순차적으로 접근함
		Iterator<? extends GrantedAuthority> all = iter.iterator();
			
		//GrantedAuthority : 무조건 첫번째의 값을 객체에 담는 역활 
		GrantedAuthority gauth = all.next();
		System.out.println(gauth.toString()); 
		
		m.addAttribute("userid",userid);
		
		//DB에 저장된 레벨 권한을 가져와서 출력함
		m.addAttribute("userlevel",gauth.getAuthority());
		
		return "/main.html";
	}
	
	
	@GetMapping("/web1.do")
	public String web1() {	//로그인	
		return "/web1.html";
	}
	//해당 메소드 부분 Spring Security에서 처리하는 메소드가 됨
	@PostMapping("/loginok.do")
	public String loginok() {	//로그인 확인 페이지
		return null;
	}
	
	@GetMapping("/web2.do")
	public String web2() {	//회원가입	
		return "/web2.html";
	}
	
	@PostMapping("/joinok.do")
	public String joinok(se_user dto, Model m) {
		int result = this.serice.join_insert(dto);
		String msg = "";
		if(result == 0) {
			msg = "alert('해당 아이디가 중복 발생 하였습니다.'); location.href='./web2.do';";
		}else {
			msg = "alert('정상 회원가입 되었습니다.'); location.href='./web1.do';";
		}
		m.addAttribute("msg",msg);
		return null;
	}
	
	
	@GetMapping("/adminlist.do")
	public String adminlist() {	//관리자 리스트 출력
		
		return "/adminlist.html";
	}

	
}
