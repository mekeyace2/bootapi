package kr.it.ecms.se;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class se_user {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	public int nidx;
	@Column(unique = true)
	public String userid;
	
	public String userpw;
	public String role;
}
