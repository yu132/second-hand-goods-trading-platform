package se.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 用户踩赞
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_like_or_dislike")
public class LikeOrDislike {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id",length = 11,nullable=false)
	private Integer userId;
	
	/**
	 * 被踩赞商品id
	 */
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	/**
	 * 踩或赞
	 */
	@Column(name = "like_or_dislike",length = 20)
	private String likeOrDislike;
	
	/**
	 * 踩赞时间
	 */
	@Column(name = "commit_time",length = 20)
	private Date commitTime;
	
}
