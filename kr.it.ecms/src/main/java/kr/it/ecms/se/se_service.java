package kr.it.ecms.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

//jpa로 insert, select, update.. 모든것을 처리하여 결과값을 Controller에 전달
@Service
public class se_service {
	
	@Autowired
	private se_repo repo;
	
	//SHA : 일반적인 암호화 => 산술연산 (GPU)
	
	
	//scrypt (암호화 기술) => openssl
	//BCryptPasswordEncoder : spring security에서 제공된 암호화 모듈
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	int result = 0;
	
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
