package kr.it.ecms.se;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	//회원가입 service 부분
	public int join_insert(se_user dto) {
		System.out.println(dto.getUserid());
		System.out.println(this.bcrypt.encode(dto.getUserpw()));
		
		return 0;
	}
	
	
}
