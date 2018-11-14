package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.ShoppingTrolley;

@Repository
public interface ShoppingTrolleyRepository extends CrudRepository<ShoppingTrolley, Integer>, JpaRepository<ShoppingTrolley, Integer> {
	public Iterable<ShoppingTrolley> findAllByUserId(Integer userId);
}
