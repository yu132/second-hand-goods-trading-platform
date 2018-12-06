package se.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>, JpaRepository<Order, Integer> {
	public long countBySellerId(Integer sellerId);
	public long countBySellerIdAndState(Integer sellerId,String state);
	public Page<Order> findBySellerId(Integer sellerId,Pageable pageable);
}
