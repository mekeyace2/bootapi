package kr.it.ecms.erp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class erp_levelDTO {

	@Id
	@Column(nullable = false, columnDefinition = "int auto_increment")
	int uidx;
	
	@Column(nullable = false, columnDefinition = "int(1)")
	int level;
	
	// @OneToMany //단방향 형태의 매핑
	//FetchType.LAZY : 지연로딩 (부모테이블 조회시 해당 자식 테이블을 지연시켜 조금 더 빠르게 부모테이블을 조회함)
	//FetchType.EAGER : 즉시로딩 (부모테이블 조회시 자식 테이블을 무조건 같이 조회하는 방식)
	@ManyToOne(fetch = FetchType.LAZY) //양방향 형태의 매핑
	@JoinColumn(name="uid", referencedColumnName = "uid", nullable=false)
	erp_loginDTO dto;
	
	
		
	
}
