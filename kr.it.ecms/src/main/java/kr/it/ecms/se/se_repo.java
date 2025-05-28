package kr.it.ecms.se;

import org.springframework.data.jpa.repository.JpaRepository;


public interface se_repo extends JpaRepository<se_user, Integer> {
	//로그인에서 사용하는 JPA Query
	se_user findByUserid(String userid);
}