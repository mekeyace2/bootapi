package kr.it.ecms.erp;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

//JPA Repo
public interface jpa_repo extends JpaRepository<erp_loginDTO, Integer> {
	
	
	//@Query : 개발자가 원하는 DDL 문법을 사용하여 적용하는 방식
	@Query("select now() as today")
	String mysql_today();
		
	//전체데이터를 가져옴
	List<erp_loginDTO> findAll();
	
	//전체데이터를 가져올시 고유값을 기준으로 최신 데이터를 상위(desc)를 적용
	//select * from erp_logindto order by uidx desc
	//findBy(데이터 1개),findAllBy(데이터 전체)동일한 동작 
	List<erp_loginDTO> findByOrderByUidxDesc();
	List<erp_loginDTO> findAllByOrderByUidxDesc();
	
	//select * from erp_logindto where uid=#{uid}
	List<erp_loginDTO> findByUid(String uid);
	List<erp_loginDTO> findByUidAndGernder(String uid,String gernder);
	
		
	@Query("update erp_loginDTO set uname=:uname where uid = :uid")
	//JPA를 사용하여 update, delete문을 위에서 했던 방식으로 직접 작성하여 실행 할때는 @Transactional
	@Transactional	
	@Modifying(clearAutomatically = true) //insert, update, delete문 DDL일때 사용하는 어노테이션 입니다.
	int myinfo_update(@Param("uid")String uid,
			@Param("uname")String uname);
	//@Param : @Query에서 가상의 :가상컬럼명에 값을 대입하기 위한 어노테이션
	
	
	
	//nativeQuery : true(SQL 직접 정의를 하여 실행),  false(JPQL을 사용하는 형태)
	@Query(value = "insert into erp_logindto (uidx, uid, uname, gernder, joindate) " +
	               "values ('0', :mid, :uname, :gender, :today)", nativeQuery = true)
	@Transactional	
	@Modifying(clearAutomatically = true)
	int mysql_insert(@Param("mid") String mid,
	                 @Param("uname") String uname,
	                 @Param("gender") String gender,
	                 @Param("today") String today);
	
	
}
