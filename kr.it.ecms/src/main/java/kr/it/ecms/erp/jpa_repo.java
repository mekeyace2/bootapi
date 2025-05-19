package kr.it.ecms.erp;

import org.springframework.data.jpa.repository.JpaRepository;

//JPA Repo
public interface jpa_repo extends JpaRepository<erp_loginDTO, Integer> {
	
}
