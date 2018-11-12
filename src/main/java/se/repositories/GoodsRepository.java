package se.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.Goods;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Integer>, JpaRepository<Goods, Integer> {
	public Goods findBySellerIdAndGoodsName(Integer sellerId,String goodsName);
	public Goods findBySellerId(Integer sellerId);
	public long countBySellerId(Integer sellerId);
	public Page<Goods> findBySellerIdOrderByCommitTimeDesc(Integer sellerId,Pageable pageable);
}
