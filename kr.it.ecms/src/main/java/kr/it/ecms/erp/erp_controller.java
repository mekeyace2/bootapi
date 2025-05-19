package kr.it.ecms.erp;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletResponse;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class erp_controller {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	PrintWriter pw = null;
	
	
	@Autowired
	public jpa_repo repo;
	
	//jpa를 활용하여 insert
	@GetMapping("/jpa_insert.do")
	public String jpa_insert() {
		//신규 데이터를 입력하는 샘플이며, DTO에 메소드 순서에 맞게 인자값을 생성함
		erp_loginDTO dto = new erp_loginDTO(1,"park");
		
		
		//save => JPA 전용 메소드 (select 후 insert)
		erp_loginDTO saveEntity = this.repo.save(dto);
		System.out.println(saveEntity.getUidx());
		
		return null;
	}
	
	
	
	//React 에서 값을 비동기 통신(fetch)를 이용한 데이터를 이관 받아서 처리하는 Controller	
	@GetMapping("/erp/dataload.do")
	public String dataload(ServletResponse res, 
			@RequestParam String corp_part, 
			@RequestParam String corp_search) throws Exception {
		this.pw = res.getWriter();
		this.log.info("React 통신 확인!!");
		this.log.info(corp_part);
		this.log.info(corp_search);
		this.pw.print("Ajax 테스트 중!!!!!");		//React로 결과값을 회신함
		this.pw.close();
		return null;
	}
	
	
}
