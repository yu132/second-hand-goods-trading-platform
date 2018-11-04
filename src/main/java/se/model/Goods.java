package se.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
/**
 * 商品类
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_goods")
public class Goods {

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
	 * 商品名称
	 */
	@Column(name = "goods_name",length = 255,nullable=false)
	private String goodsName;
	
	/**
	 * 商品价格
	 */
	@Column(name = "goods_price",length = 20,nullable=false)
	private Double price;
	
	/**
	 * 商品数量
	 */
	@Column(name = "goods_amount",length = 11)
	private Integer amount;
	
	/**
	 * 商品描述
	 */
	@Column(name = "goods_description",length = 255)
	private String description;
	
	/**
	 * 是否需要邮件提醒
	 */
	@Column(name = "need_email_remind",length = 10)
	private Boolean emailRemind;
	
	/**
	 * 商品状态
	 */
	@Column(name = "state",length = 10)
	private String state;
	
	/**
	 * 商品上传时间
	 */
	@Column(name = "commit_time",length = 20)
	private Date commitTime;
	
}
