package se.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 订单时间类
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_order_times")
public class OrderTimes {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 下单时间
	 */
	@Column(name = "order_time",length = 20)
	private Date orderTime;
	
	/**
	 * 支付时间
	 */
	@Column(name = "payment_time",length = 20)
	private Date paymentTime;
	
	/**
	 * 发货时间
	 */
	@Column(name = "delivery_time",length = 20)
	private Date deliveryTime;
	
	/**
	 * 交易完成时间
	 */
	@Column(name = "finish_time",length = 20)
	private Date finishTime;
}
