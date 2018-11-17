package se.model;
import java.util.Date;
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
 * 求购表
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_want_to_by")
public class WantToBy {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id",length = 20,nullable=false)
	private Integer userId;
	
	/**
	 * 发表求购信息时间
	 */
	@Column(name = "commit_time",length = 20)
	private Date commitTime;
	
	/**
	 * 求购信息描述
	 */
	@Column(name = "description",length = 255)
	private String description;
	
	/**
	 * 求购数量
	 */
	@Column(name = "amount",length = 20)
	private Integer amount;
	
	/**
	 * 求购信息状态
	 */
	@Column(name = "state",length = 20)
	private String state;

	/**
	 * 求购种类
	 */
	@ManyToMany
	@JoinTable( joinColumns = { @JoinColumn(name = "wanted_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id") })
	private Set<GoodsType> goodsTypes;
}
