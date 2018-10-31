package se.service;

import java.util.HashMap;
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
		Map<String,Object> result=new HashMap<>();
		result.put("State", "SUCCESS");
		return result;
	}
	
	public Map<String,Object> register(UserInfo userInfo){
		//TODO
		return null;
	}
	
}
