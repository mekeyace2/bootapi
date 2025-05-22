package kr.it.ecms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories : jpa에 대한 Repositories를 셋팅하는 어노테이션
//@EnableJpaRepositories(basePackages = {"kr.it.ecms.erp"})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
