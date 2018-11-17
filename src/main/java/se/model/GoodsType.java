package se.model;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(joinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id") }, inverseJoinColumns ={ @JoinColumn(name = "goods_id", referencedColumnName = "id") } )
	private Set<Goods> goods;
	
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(joinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id") }, inverseJoinColumns ={ @JoinColumn(name = "wanted_id", referencedColumnName = "id") } )
//	private Set<WantToBy> wantToBys;
}
