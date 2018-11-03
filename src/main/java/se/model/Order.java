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
@Table(name="t_order")
public class Order {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "order_id",length = 11)
	private Integer id;
	
	@Column(name = "seller_id",length = 11,nullable=false)
	private Integer sellerId;
	
	@Column(name = "buyer_id",length = 11,nullable=false)
	private Integer buyerId;
	
	@Column(name = "goods_id",length = 11,nullable=false)
	private Integer goodsId;
	
	@Column(name = "amount",length = 11)
	private Integer amount;

	@Column(name = "buyer_name",length = 20)
	private String buyerName;
	
	@Column(name = "buyer_address",length = 255)
	private String buyerAddress;
	
	@Column(name = "remarks",length = 255)
	private String remarks;
	
	@Column(name = "order_time_id",length = 11)
	private Integer orderTimeId;
	
	@Column(name = "total_price",length = 20)
	private Double totalPrice;
	
	@Column(name = "way_of_pay",length = 20)
	private String wayOfPay;
	
	@Column(name = "state",length = 20)
	private String state;
	
	
}
