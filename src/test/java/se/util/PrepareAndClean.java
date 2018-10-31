package se.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.UserInfo;
import se.repositories.UserInfoRepository;

@Service
public class PrepareAndClean {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public void prepareUser(UserInfo userInfo){
		userInfoRepository.save(userInfo);
	}
	
	public void cleanUser(UserInfo userInfo){
		userInfoRepository.delete(userInfo);
	}
	
}
