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
@Table(name="t_want_to_by")
public class WantToBy {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "user_id",length = 20,nullable=false)
	private Integer userId;
	
	@Column(name = "time",length = 20)
	private String time;
	
	@Column(name = "description",length = 255)
	private String description;
	
	@Column(name = "amount",length = 20)
	private Integer amount;
	
	@Column(name = "state",length = 20)
	private String state;

	
}
