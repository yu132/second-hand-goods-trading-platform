package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 用户和用户地址多对多联系表
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_relation_between_user_and_address")
public class RelationBetweenUserAndAddress {
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
	 * id
	 */
	@Column(name = "address",length = 11)
	private String address;
}
