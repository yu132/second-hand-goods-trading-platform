package se.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.UserInfo;
import se.repositories.UserInfoRepository;
import se.service.judge.UserServiceJudge;

@Service
public class UserService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserServiceJudge userServiceJudge;
	
	public Map<String,Object> login(String userName,String password){
		Map<String,Object> result=new HashMap<>();
		result.put("State", "SUCCESS");
		return result;
	}
	
	public Map<String,Object> register(UserInfo userInfo){
		//TODO
		Map<String,Object> result=new HashMap<>();
		String userName=userInfo.getUserName();
		if(userName.length()<6){
			result.put("State", "");
			return result;
		}else if(userName.length()>20){
			
		}
		userInfoRepository.save(userInfo);
		return result;
	}
	
}
