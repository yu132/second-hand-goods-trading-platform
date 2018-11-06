package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.Goods;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Integer>, JpaRepository<Goods, Integer> {
	public Goods findBySellerIdAndGoodsName(Integer sellerId,String goodsName);
}
