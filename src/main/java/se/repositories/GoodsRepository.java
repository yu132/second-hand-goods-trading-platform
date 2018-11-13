package se.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.Goods;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Integer>, JpaRepository<Goods, Integer> {
	/**
	 * 根据卖家id和物品名找物品
	 * @param sellerId
	 * @param goodsName
	 * @return
	 */
	public Goods findBySellerIdAndGoodsName(Integer sellerId,String goodsName);
	/**
	 * 根据卖家id找物品，用于判断该卖家是否有物品
	 * @param sellerId
	 * @return
	 */
	public Goods findBySellerId(Integer sellerId);
	/**
	 * 返回该卖家商品数量
	 * @param sellerId
	 * @return
	 */
	public long countBySellerId(Integer sellerId);
	/**
	 * 返回某种状态的所有物品
	 * @param state
	 * @return
	 */
	public Iterable<Goods> findAllByState(String state);
	/**
	 * 返回某种状态的所有物品数量
	 * @param state
	 * @return
	 */
	public long countByState(String state);
	/**
	 * 分页返回某种状态的物品，时间降序
	 * @param state
	 * @param pageable
	 * @return
	 */
	public Page<Goods> findByStateOrderByCommitTimeDesc(String state, Pageable pageable);
	/**
	 *  分页返回某卖家的物品，时间降序
	 * @param sellerId
	 * @param pageable
	 * @return
	 */
	public Page<Goods> findBySellerIdOrderByCommitTimeDesc(Integer sellerId,Pageable pageable);
}
