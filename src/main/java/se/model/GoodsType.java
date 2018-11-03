package se.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 商品类别
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_goods_type")
public class GoodsType {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 商品类别名字
	 */
	@Column(name = "goods_type_name",length = 20)
	private String type;
}
