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
@Table(name="t_relation_between_goods_and_type")
public class RelationBetweenGoodsAndType {

	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "goods_id",length = 11)
	private Integer goodsId;
	
	@Column(name = "type_id",length = 11)
	private Integer typeId;
}
