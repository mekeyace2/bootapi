package kr.it.ecms.erp;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletResponse;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class erp_controller {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	PrintWriter pw = null;
	
	//데이터 리스트 출력 및 페이징 번호 포함 (JPA)
	@GetMapping("/jpa_memberlist.do")
	public String jpa_memberlist(Model m) {
		int page_ea = 5;
				
		//Sort(Class) => 데이터 배열을 정렬을 하는 Class
		//Sort st = Sort.by("uidx").descending();
		Sort st = Sort.by(Sort.Order.desc("uidx"));
		//Sort st = Sort.by(Sort.Order.desc("uidx"),Sort.Order.asc("uname"));
			
		//Pageable(interface) : Database를 limit 0,3
		Pageable pg = PageRequest.of(0,page_ea,st);		//Pageable = Map
		
		//Pageable에 대한 interface의 값을 배열로 받아서 처리하는 결과(List)
		Page<erp_loginDTO> result = this.repo.findAll(pg);
		System.out.println(result.getTotalPages());	//전체 데이터를 확인하여 page 번호 갯수 확인
		System.out.println(result.getContent());	//DTO 기준으로 배열화 적용되어 데이터를 출력
		/*
		for(erp_loginDTO dto : result) {
			System.out.println(dto.getUname());
		}
		*/
		m.addAttribute("pgno",result.getTotalPages());
		m.addAttribute("result",result.getContent());
		return null;
	}
	
	
	
	
	
	
	//.war  .jar(서버 포트 포워딩 필수)	
	@Autowired(required=true)
	public jpa_repo repo;
		
	//React에 select 데이터 전송
	@GetMapping("/jpa_member.do")
	public String jpa_member(ServletResponse res) throws Exception {
		try {
			res.setContentType("text/html;charset=utf-8");
			this.pw = res.getWriter();
			
			List<erp_loginDTO> all = this.repo.findByOrderByUidxDesc();
			JSONObject jo = new JSONObject();
			JSONArray ja1 = new JSONArray();
			
			for(erp_loginDTO a : all) {
				JSONArray ja2 = new JSONArray();
				ja2.put(a.getUidx());
				ja2.put(a.getUid());
				ja2.put(a.getUname());
				ja2.put(a.getGernder());
				ja2.put(a.getJoindate());
				ja1.put(ja2);
			}
			jo.put("members", ja1);
			this.pw.print(jo);
		}catch (Exception e) {
			this.pw.print("API Server Error");
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	
	
	
	//React에서 fetch로 Ajax(FormData)로 통신
	@PostMapping("/jpa_join.do")
	public String jpa_join(
		@RequestParam("uid") String uid,
		@RequestParam("uname") String uname,
		@RequestParam("gernder") String gernder, ServletResponse res) throws Exception {
		this.pw = res.getWriter();
		try {
			//실서버에 있는 시간을 가져오는 코드
			String today = repo.mysql_today();
			
			//DTO에 즉시실행 메소드에 setter로 값을 이관
			erp_loginDTO dto = new erp_loginDTO(0,uid,uname,gernder,today);
			this.repo.save(dto);	//JPA에서 DTO를 이용하여 SQL 저장함
			this.pw.print("ok");
			
		}catch (Exception e) {
			this.pw.print("error");
		}
		/*
		System.out.println(uid);
		System.out.println(uname);
		System.out.println(gernder);
		*/
		return null;
	}
	
	
	@GetMapping("/jpa_select.do")
	public String jpa_select() {
		//findAll은 select * from 테이블명
		//List<erp_loginDTO> all = this.repo.findAll();
		List<erp_loginDTO> all = this.repo.findAllByOrderByUidxDesc();
		System.out.println(all.size());
		System.out.println(all.get(1).getUid());
		
		//List<erp_loginDTO> all2 = this.repo.findByUid("apink");
		List<erp_loginDTO> all2 = this.repo.findByUidAndGernder("apink","W");
		System.out.println(all2.get(0).getUname());
		
		
		return null;
	}
	
	@GetMapping("/jpa_update.do")
	public String jpa_update() {
		
		int result = this.repo.myinfo_update("hong","홍길동");
		System.out.println(result);
		
		return null;
	}
	
	

	@GetMapping("/jpa_insert2.do")
	public String jpa_insert2() {
		String today = this.repo.mysql_today();
		//int result = this.repo.mysql_insert("black", "블랙", "W", today);
		//erp_loginDTO dtos = erp_loginDTO;
		
		//System.out.println(result);
		
		return null;
	}

		
	
	//jpa를 활용하여 insert
	@GetMapping("/jpa_insert.do")
	public String jpa_insert() {
		//신규 데이터를 입력하는 샘플이며, DTO에 메소드 순서에 맞게 인자값을 생성함
		//erp_loginDTO dto = new erp_loginDTO(0,"hong");	
		//save => JPA 전용 메소드 (select 후 insert)
		//erp_loginDTO saveEntity = this.repo.save(dto);
		//System.out.println(saveEntity);	
		
		//고유값 및 시간 표현
		//LocalDateTime : Window 시간을 가져와서 DB 저장하는 방식
		//LocalDateTime todays = LocalDateTime.now(); 
		
		//Database에서 적용된 시간을 가져와서 DB 저장하는 방식
		try {
		String today = this.repo.mysql_today();
		/*save 메소드는 두가지 기능 
		1. insert 기능 : Primary key 기준 핸들링 되며, 해당 조건이 없을 경우
		2. update 기능 : Primary key 동일한 값이 있을 경우 해당 정보의 값을 수정
		*/
		erp_loginDTO dto = new erp_loginDTO(0,"apink","에이핑크","W",today);
		erp_loginDTO insert = this.repo.save(dto);
		
		System.out.println("정상적으로 가입이 되었습니다.");
		}catch(Exception e) {	//save메소드 발생시 insert가 정상적으로 작동되지 않을 경우
		System.out.println("해당 아이디의 값이 중복 되어서 저장 되지 않습니다.");	
		}
		
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
