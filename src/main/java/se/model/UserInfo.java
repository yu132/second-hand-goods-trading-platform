package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="t_user_info",uniqueConstraints={@UniqueConstraint(columnNames={"loginName"})},    //唯一约束
indexes = {@Index(columnList = "nick_name")})
public class UserInfo {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id",length = 11)
	private Integer id;
	
	@Column(name = "user_name",length = 20,nullable=false ,unique=true)
	private String userName;
	
	@Column(name = "password",length = 30,nullable=false )
	private String passWord;
	
	@Column(name = "nick_name",length = 30,nullable=false ,unique=true)
	private String nickName;
	
	@Column(name = "email",length = 100)
	private String email;
	
	@Column(name = "phone_number",length = 30)
	private String phoneNumber;
	
	@Column(name = "address",length = 100)
	private String address;
	
	@Column(name = "head_picture_path",length = 100)
	private String headPicturePath;
	
	@Column(name = "balance",length = 20)
	private Double balance;
	
	@Column(name = "state",length = 30)
	private String state;
	
	@Column(name = "register_time",length = 50)
	private Long time;
	
}
