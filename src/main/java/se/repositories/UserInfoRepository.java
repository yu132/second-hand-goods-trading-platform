package se.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.model.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer>, JpaRepository<UserInfo, Integer> {
	/**
	 * 用用户名和密码寻找一条用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserInfo findByUserNameAndPassword(String userName,String password);
	/**
	 * 用用户名寻找一条用户信息
	 * @param userName
	 * @return
	 */
	public UserInfo findByUserName(String userName);
}
