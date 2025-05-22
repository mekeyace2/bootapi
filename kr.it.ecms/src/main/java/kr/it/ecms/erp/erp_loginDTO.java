package kr.it.ecms.erp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	
//@Id : primary key를 설정할 때 사용함
//@UniqueConstraint : unique를 설정할 때 사용함
//@JoinColumn : foreign key를 설정할 때 사용함
@Data
@Entity
@NoArgsConstructor		//@파라미터가 없는 default 생성자를 자동으로 생성하는 형태
@AllArgsConstructor		//클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동으로 구성
public class erp_loginDTO {
	//nullable = false (not null) nullable = true (null)
	//columnDefinition : 해당 컬럼에 옵션을 셋팅할 때 사용하는 속성
	@Id		//@Id : Primary key를 생성하는 어노테이션
	@Column(nullable = false, columnDefinition = "int auto_increment")
	int uidx;
	
	//unique : 중복되지 않은 고유값을 세팅한 속성)
	/*
	 외래키를 사용하고 싶을 경우 unique 속성 또는 @Id로 셋팅이 되어 있어야 하며,
	 name 속성을 적용하여 외래키를 사용할 수 있도록 셋팅 해야함
	 */
	
	@Column(nullable=false, length=15, unique = true, name="uid")
	String uid;
	
	//length => varchar
	@Column(nullable = false, columnDefinition="varchar(10)")
	String uname;
	
	@Column(nullable = false, columnDefinition = "enum('M','W') default 'M'")
	String gernder;
	
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String joindate;
}
