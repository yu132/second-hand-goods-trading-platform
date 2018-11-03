package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 购物车
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_shopping_trolley")
public class ShoppingTrolley {

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
	 * 商品id
	 */
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	/**
	 * 商品数目
	 */
	@Column(name = "goods_amount",length = 11)
	private Integer amount;
	
	/**
	 * 加入时间
	 */
	@Column(name = "time",length = 11)
	private Long time;
}
