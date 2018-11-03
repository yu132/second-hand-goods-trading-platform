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
@Table(name="t_shopping_trolley")
public class ShoppingTrolley {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "trolley_id",length = 11)
	private Integer id;
	
	@Column(name = "user_id",length = 11)
	private Integer userId;
	
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	@Column(name = "amount",length = 11)
	private Integer amount;
	
	@Column(name = "time",length = 11)
	private Long time;
}
