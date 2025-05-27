package kr.it.ecms.se;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//WEB Sercurity 활성화 및 환경설정을 하기 위한 어노테이션
@Configuration
@EnableWebSecurity
public class securityconfig {
	
	
	//Springboot 3.2x 이상, Spring-security 6.x 이상
	//Lamda 방식의 코드 작성  (abc->abc)
	//Spring Security의 필터 역활로 해당 환경조건 및 접속 URL에 대한 권한을 설정
	@Bean
	public SecurityFilterChain filterch(HttpSecurity http) throws Exception{
		//위변조 토큰의 값을 보낼 경우 해당 토큰값을 이용하여 로그인 할 수 있음
		//disable() : 토큰 미발행
		http.csrf((auth)->auth.disable());	
		//spring security에서 기본으로 제공되는 로그인 페이지를 사용하지 않음
		http.httpBasic((auth)->auth.disable());
		
		//authorizeHttpRequests : URL 보안 인증 및 인가를 구현하는 구조
		/*
		 requestMatchers : 컨트롤에 사용되는 요청타입에 따라 페이지를
		 지정 할 수 있으며 또한 권한 레벨에 맞춰서 접근을 설정할 수 있음 
		 */
		/*
		 [접근권한] 
		 permitAll() : 모든 사용자가에게 로그인 없이 접근이 가능한 페이지 
		 hasRole(null) : 관리자 권한 또는 일반권한 등 각 파트에 맞게 접근 가능하게 설정
		 denyAll() : 모든 사용자에게 접근을 금지
		 authenticated() : 로그인한 사용자만 접근이 가능합니다. (관리자, 일반사용자)
		 hasAnyRole(null) : 여러 권한을 한번에 처리 할때 사용합니다.
		 */
		http.authorizeHttpRequests((auth)->auth
				.requestMatchers("/web1.do","/web2.do").permitAll()
				.requestMatchers("/joinok.do").permitAll()
				.requestMatchers("/WEB-INF/**").permitAll()	//MVC중 JSP를 출력 위한 경로 또한 보안에서 접근허락
				.requestMatchers("/adminlist.do").hasRole("ADMIN")
				.anyRequest().authenticated()
		);
		//로그인 셋팅
		//auth.loginPage : 보안 페이지 해당 되는 사항일 경우 로그인 페이지로 강제이동
		http.formLogin((auth)->auth.loginPage("/web1.do")
				.loginProcessingUrl("/loginok.do").permitAll());
		
		return http.build();	//보안관련 설정값을 build으로 생성하여 적용함
	}
	
	@Bean
	public BCryptPasswordEncoder bcrypass() {
		return new BCryptPasswordEncoder();
	}
	
}
