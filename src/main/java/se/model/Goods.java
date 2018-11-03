package se.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="t_goods")
public class Goods {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "goods_id",length = 11)
	private Integer id;
	
	@Column(name = "seller_id",length = 11,nullable=false)

	private Integer sellerId;
	
	@Column(name = "goods_name",length = 255,nullable=false)
	private String goodsName;
	
	@Column(name = "goods_price",length = 20,nullable=false)
	private Double price;
	
	@Column(name = "amount",length = 11)
	private Integer amount;
	
	@Column(name = "goods_description",length = 255)
	private String description;
	
	@Column(name = "need_email_remind",length = 10)
	private Boolean emailRemind;
	
	@Column(name = "state",length = 10)
	private String state;
	
	@Column(name = "time",length = 20)
	private Long time;
	
}
