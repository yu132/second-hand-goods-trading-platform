package se.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import se.model.ShoppingTrolley;

@Repository
public interface ShoppingTrolleyRepository extends CrudRepository<ShoppingTrolley, Integer>, JpaRepository<ShoppingTrolley, Integer> {
	/**
	 * 通过买家id获取购物车
	 * @param userId
	 * @return
	 */
	public Iterable<ShoppingTrolley> findAllByUserId(Integer userId);
	
	/**
	 * 根据用户id与商品id判断是否存在
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public boolean existsByUserIdByGoodsId(Integer userId,Integer goodsId);
	
	/**
	 * 根据用户id与商品id删除购物车
	 * @param userId
	 * @param goodsId
	 */
	public void deleteByUserIdByGoodsId(Integer userId,Integer goodsId);
	
	/**
	 * 根据用户id与商品id获取购物车
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public Optional<ShoppingTrolley> findByUserIdByGoodsId(Integer userId,Integer goodsId);
	
	/**
	 * 根据用户id和页数获取购物车
	 * @param userId
	 * @param pageable
	 * @return
	 */
	public Page<ShoppingTrolley> findByUserIdByAddTimeDesc(Integer userId,Pageable pageable);
	
	/**
	 * 根据用户id获取购物车数目
	 * @param userId
	 * @return
	 */
	public long countByUserId(Integer userId);
}
