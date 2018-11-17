package se.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 订单类
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_order")
public class Order {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 卖家id
	 */
	@Column(name = "seller_id",length = 11,nullable=false)
	private Integer sellerId;
	
	/**
	 * 买家id
	 */
	@Column(name = "buyer_id",length = 11,nullable=false)
	private Integer buyerId;
	
	/**
	 * 商品id
	 */
	@Column(name = "goods_id",length = 11,nullable=false)
	private Integer goodsId;
	
	/**
	 * 商品数目
	 */
	@Column(name = "goods_amount",length = 11)
	private Integer amount;

	/**
	 * 收件人称呼
	 */
	@Column(name = "buyer_name",length = 20)
	private String buyerName;
	
	/**
	 * 收件人地址
	 */
	@Column(name = "buyer_address",length = 255)
	private String buyerAddress;
	
	/**
	 * 订单备注
	 */
	@Column(name = "remarks",length = 255)
	private String remarks;
	
	/**
	 * 订单时间类id
	 */
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName="id",nullable=false)
	private OrderTimes orderTime;
	
	/**
	 * 订单总价
	 */
	@Column(name = "total_price",length = 20)
	private Double totalPrice;
	
	/**
	 * 支付方式
	 */
	@Column(name = "way_of_pay",length = 20)
	private String wayOfPay;
	
	/**
	 * 订单状态
	 */
	@Column(name = "state",length = 20)
	private String state;
	
	
}
