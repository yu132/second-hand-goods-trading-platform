package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer>,JpaRepository<UserInfo, Integer>{
}
