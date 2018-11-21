package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import se.model.UserReceiverInfo;


public interface UserReceiverInfoRepository extends CrudRepository<UserReceiverInfo, Integer>, JpaRepository<UserReceiverInfo, Integer> {

}
