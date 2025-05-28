package kr.it.ecms.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//jpa로 insert, select, update.. 모든것을 처리하여 결과값을 Controller에 전달

/*
 UserDetailsService : 사용자 정보를 로드하여 권한 및 역활 정보를 가져오는 interface 
 기본 메소드 인자값은 username 
 */
@Service
public class se_service implements UserDetailsService {

   
	@Autowired
	private se_repo repo;
	
	//SHA : 일반적인 암호화 => 산술연산 (GPU)
	
	
	//scrypt (암호화 기술) => openssl
	//BCryptPasswordEncoder : spring security에서 제공된 암호화 모듈
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	int result = 0;
		
	/*
	  UserDetails () : 기본은 username으로 매개변수 이름으로 설정되어 있음
	  단 config 환경을 Paramater를 셋팅 하였을 경우 매겨변수 이름을 원하는 형태로 변경 가능
	  Collection<? extends GrantedAuthority>에 대한 상세 정보를 확인하는 사항
	  
	  loadUserByUsername : 해당 사용자의 정보를 DB에서 확인 후 처리하는 메소드
	  
	  ****보안 : Front에서 아이디와 패스워드를 기존 Security에서 제공한 name값으로는 절대 사용하지 않음
	 */
	
	//만약 해당 Front값이 출력되지 않을 경우 securityconfig.java 및 se_customuser.java 모두 재확인 필수
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		System.out.println(userid);		//Front 값을 확인 하는 작업을 함
		
		//JPA에 select 쿼리를 발동하여 해당 아이디의 정보를 체크함
		se_user user = this.repo.findByUserid(userid);
		if(user != null) {		
			//해당 정보가 확인이 되었을 경우 새롭게 se_customuser호출 하여 로그인 정보를 반환함
			return new se_customuser(user);
		}
		/*
		System.out.println(user.getUserid());
		System.out.println(user.getUserpw());
		System.out.println(user.getRole());
		*/
		return null;
	}
	
	
	
	
	//회원가입 service 부분
	public int join_insert(se_user dto) {
		//System.out.println(dto.getUserid());
		
		//BCrypt 암호화 모듈로 적용 후 setter에 새로운 값을 이관
		dto.setUserpw(this.bcrypt.encode(dto.getUserpw()));
		//ROLE_SUPER_ADMIN > ROLE_ADMIN > ROLE_MANAGER > ROLE_USER
		dto.setRole("ROLE_USER");
		//dto.setRole("ROLE_ADMIN");
		try {		
			this.repo.save(dto);
			this.result = 1;	//정상적 가입
		}catch (Exception e) {
			this.result = 0;	//아이디 중복 발생
		}
		
		return this.result;
	}
	
	
}
