package se.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
/**
 * 用户信息
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_user_info",uniqueConstraints={@UniqueConstraint(columnNames={"user_name"})},    //唯一约束
indexes = {@Index(columnList = "nick_name")})
public class UserInfo {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 用户姓名
	 */
	@Column(name = "user_name",length = 20,nullable=false ,unique=true)
	private String userName;
	
	/**
	 * 用户密码
	 */
	@Column(name = "password",length = 30,nullable=false )
	private String password;
	
	/**
	 * 用户昵称
	 */
	@Column(name = "nick_name",length = 30,nullable=false ,unique=true)
	private String nickName;
	
	/**
	 * 用户邮箱
	 */
	@Column(name = "email",length = 100)
	private String email;
	
	/**
	 * 用户联系方式
	 */
	@Column(name = "phone_number",length = 30)
	private String phoneNumber;
	
	/**
	 * 用户地址
	 */
	@Column(name = "address",length = 100)
	private String address;
	
	/**
	 * 用户头像路径
	 */
	@Column(name = "head_picture_path",length = 100)
	private String headPicturePath;
	
	/**
	 * 用户余额
	 */
	@Column(name = "balance",length = 20)
	private Double balance;
	
	/**
	 * 用户状态
	 */
	@Column(name = "state",length = 30)
	private String state;
	
	/**
	 * 用户注册时间
	 */
	@Column(name = "register_time",length = 50)
	private Date RegisterTime;
	
}
