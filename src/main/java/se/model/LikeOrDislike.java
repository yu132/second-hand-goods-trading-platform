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
@Table(name="t_like_or_dislike")
public class LikeOrDislike {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "like_or_dislike_id",length = 11)
	private Integer id;
	
	@Column(name = "user_id",length = 11,nullable=false)
	private Integer userId;
	
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	@Column(name = "like_or_dislike",length = 20)
	private String likeOrDislike;
	
	@Column(name = "time",length = 20)
	private Long time;
	
}
