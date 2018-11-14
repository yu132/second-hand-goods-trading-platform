package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>, JpaRepository<Order, Integer> {

}
