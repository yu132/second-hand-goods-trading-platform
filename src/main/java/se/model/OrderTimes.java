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
@Table(name="t_order_times")
public class OrderTimes {

	@Column(name = "order_times_id",length = 11)
	private Integer id;
	
	@Column(name = "order_id",length = 11)
	private Integer orderId;
	
	@Column(name = "order_time",length = 20)
	private Long orderTime;
	
	@Column(name = "payment_time",length = 20)
	private Long paymentTime;
	
	@Column(name = "delivery_time",length = 20)
	private Long deliveryTime;
	
	@Column(name = "finish_time",length = 20)
	private Long finishTime;
}
