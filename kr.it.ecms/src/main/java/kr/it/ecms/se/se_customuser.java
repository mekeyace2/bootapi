package kr.it.ecms.se;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//개발자 직접 로그인 및 로그아웃등 원하는 형태로 새롭게 구성하는 class
@SuppressWarnings("serial")
public class se_customuser implements UserDetails {
	public se_user user;	//DTO null

	public se_customuser(se_user user) {
		this.user = user;	//Front-end에서 넘어온 값을 필드에 setter로 이관
	}
	
	//Collection<? extends GrantedAuthority> : 여러개의 권한 로그인 레벨
	/*
	 GrantedAuthority : 기본권한 제공 (ROLE_ADMIN, ROLE_USER....) 
	 getAuthorities() : 해당 사용자가 어떤 권한을 가지고 있는지를 확인하는 메소드(인증 후)
	 Collection : List 또는 Set 같은 배열 형태 구성
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();	//DB에 저장된 권한을 가져오는 역활
			}
		});
		return collection;
	}

	//로그인에 사용되는 패스워드
	@Override
	public String getPassword() {
		return this.user.getUserpw();
	}

	//로그인에 사용되는 아이디
	@Override
	public String getUsername() {
		return this.user.getUserid();
	}
	
	
}

