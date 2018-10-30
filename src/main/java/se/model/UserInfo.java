package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="t_user_info")
public class UserInfo {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id",length = 11)
	private Integer id;
	
	@Column(name = "user_name",length = 20)
	private String userName;
	
	@Column(name = "password",length = 30)
	private String password;
	
	@Column(name = "nick_name",length = 30)
	private String nickName;
	
	@Column(name = "email",length = 100)
	private String email;
	
	@Column(name = "phone_number",length = 30)
	private String phoneNumber;
	
	@Column(name = "address",length = 100)
	private String address;
	
	@Column(name = "head_picture_path",length = 100)
	private String headPicturePath;
	
}
