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
@Table(name="t_comment_to_user")
public class CommentsToUser {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "comment_to_users_id",length = 11)
	private Integer id;
	
	@Column(name = "comment_from_user_id",length = 11)
	private Integer commentFromUserId;
	
	@Column(name = "comment_to_user_id",length = 11)
	private Integer commentToUserId;
	
	@Column(name = "comment_content",length = 255)
	private String content;
	
	@Column(name = "score",length = 20)
	private Double score;
	
	@Column(name = "comment_time",length = 20)
	private Long time;
}
