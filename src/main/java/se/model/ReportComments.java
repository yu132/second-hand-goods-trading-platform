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
@Table(name="t_report_comments")
public class ReportComments {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "report_from_user_id",length = 11,nullable=false)
	private Integer reportFromUserId;
	
	@Column(name = "report_to_comment_id",length = 11)
	private Integer reportToCommentId;
	
	@Column(name = "content",length = 255)
	private String content;
	
	@Column(name = "report_time",length = 20)
	private Long time;
	
	@Column(name = "state",length = 20)
	private String state;
	
}
