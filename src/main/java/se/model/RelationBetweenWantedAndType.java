
package se.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 求购表与种类表的多对多联系表
 * @author HP
 *
 */
@Data
@Entity
@Table(name="t_relation_between_wante_and_type")
public class RelationBetweenWantedAndType {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	/**
	 * 求购id
	 */
	@Column(name = "wanted_id",length = 11)
	private Integer wantedId;
	
	/**
	 * 种类id
	 */
	@Column(name = "type_id",length = 11)
	private Integer typeId;
}
