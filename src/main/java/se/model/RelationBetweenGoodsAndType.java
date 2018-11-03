package se.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 商品和商品种类多对多联系表
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_relation_between_goods_and_type")
public class RelationBetweenGoodsAndType {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 商品id
	 */
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	/**
	 * 种类id
	 */
	@Column(name = "type_id",length = 11)
	private Integer typeId;
}
