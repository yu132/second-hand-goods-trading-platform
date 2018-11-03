package se.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
/**
 * 商品图片
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_goods_picture")
public class GoodsPicture {
	
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
	 * 商品照片存放路径
	 */
	@Column(name = "picture_path",length = 255)
	private String picPath;
}
