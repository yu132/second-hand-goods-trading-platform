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
		UserInfo userInfo=userInfoRepository.findByUserName(userName);
		if(userInfo==null){
			result.put("State", "ERROR");
			result.put("Reason", "USERNAME_NOT_EXIST");
		}else if(password!=userInfo.getPassword()){
			result.put("State", "ERROR");
		}else{
			result.put("State", "SUCCESS");
		}
		return result;
	}
	
	public Map<String,Object> register(UserInfo userInfo){
		//TODO
		Map<String,Object> result=new HashMap<>();
		String userName=userInfo.getUserName();
		result=userServiceJudge.judgeUserName(userName);
		if(result.get("State").equals("ERROR")){
			return result;
		}
		String password=userInfo.getPassword();
		result=userServiceJudge.judgePassword(password);
		if(result.get("State").equals("ERROR")){
			return result;
		}
		String nickName=userInfo.getNickName();
		result=userServiceJudge.judgeNickName(nickName);
		if(result.get("State").equals("ERROR")){
			return result;
		}
		String email=userInfo.getEmail();
		result=userServiceJudge.judgeEmail(email);
		if(result.get("State").equals("ERROR")){
			return result;
		}
		String phoneNumber=userInfo.getPhoneNumber();
		result=userServiceJudge.judgePhoneNumber(phoneNumber);
		if(result.get("State").equals("ERROR")){
			return result;
		}
		String address=userInfo.getAddress();
		result=userServiceJudge.judgeAddress(address);
		if(result.get("State").equals("ERROR")){
			return result;
		}else{
			userInfoRepository.save(userInfo);
			return result;
		}
		
	}
	
}
