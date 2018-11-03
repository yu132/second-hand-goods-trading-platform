package se.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 举报商品
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_report_goods")
public class ReportGoods {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 举报发起用户id
	 */
	@Column(name = "from_user_id",length = 11,nullable=false)
	private Integer reportFromUserId;
	
	/**
	 * 被举报商品id
	 */
	@Column(name = "to_goods_id",length = 11)
	private Integer reportToGoodsId;
	
	/**
	 * 举报理由
	 */
	@Column(name = "report_content",length = 255)
	private String content;
	
	/**
	 * 举报时间
	 */
	@Column(name = "time",length = 20)
	private Long time;
	
	/**
	 * 举报状态
	 */
	@Column(name = "state",length = 20)
	private String state;
	
}
