package se.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.UserInfo;
import se.repositories.UserInfoRepository;

@Service
public class UserService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public Map<String,Object> login(String userName,String password){
		//TODO
		return null;
	}
	
	public Map<String,Object> register(UserInfo userInfo){
		//TODO
		return null;
	}
	
}
