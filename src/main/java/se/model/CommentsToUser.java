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
 * 用户对用户的评论
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_comment_to_user")
public class CommentsToUser {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 发表评论用户id
	 */
	@Column(name = "from_user_id",length = 11)
	private Integer fromUserId;
	
	/**
	 * 被评论用户id
	 */
	@Column(name = "to_user_id",length = 11)
	private Integer toUserId;
	
	/**
	 * 评论内容
	 */
	@Column(name = "comment_content",length = 255)
	private String content;
	
	/**
	 * 评价分数
	 */
	@Column(name = "comment_score",length = 20)
	private Double score;
	
	/**
	 * 发表评论时间
	 */
	@Column(name = "comment_time",length = 20)
	private Date commentTime;
}
