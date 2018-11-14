package se.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.xdevapi.JsonParser;

import se.model.UserInfo;
import se.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userSrvice;
	
    @RequestMapping(value = "login")
	public String login(String data) throws JSONException{
		Map<String,Object> result=new HashMap<>();

		JSONObject json=new JSONObject(data);
    	String userName=json.getString("username");
    	String password=json.getString("password");
		result=userSrvice.login(userName, password);
		JSONObject j=new JSONObject(result);
		return j.toString();
	}
	
    @RequestMapping(value = "register")
	public Object register(String data) throws JSONException{
		//TODO
    	Map<String,Object> result=new HashMap<>();
    	UserInfo user=new UserInfo();
    	
		JSONObject json=new JSONObject(data);
    	user.setUserName(json.getString("username"));
    	user.setPassword(json.getString("password"));
    	user.setAddress(json.getString("address"));
    	user.setEmail(json.getString("email"));
    	user.setNickName(json.getString("nickname"));
    	user.setPhoneNumber(json.getString("phoneNumber"));
		result= userSrvice.register(user);
		JSONObject j=new JSONObject(result);
		
		return j.toString();
	}
	
}
