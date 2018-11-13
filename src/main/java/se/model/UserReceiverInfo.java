package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 用户和收货信息表
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_user_receiver_information")
public class UserReceiverInfo {
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id",length = 11)
	private Integer userId;
	
	/**
	 * 收货地址
	 */
	@Column(name = "address",length = 100)
	private String recriverAddress;
	
	/**
	 * 收货人姓名
	 */
	@Column(name = "name",length = 11)
	private String recriverName;
	
	/**
	 * 用户联系方式
	 */
	@Column(name = "phone_number",length = 30)
	private String recriverPhoneNumber;
}
