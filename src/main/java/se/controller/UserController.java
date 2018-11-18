package se.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//import net.minidev.json.JSONObject;
import se.model.UserInfo;
import se.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userSrvice;
	
    @RequestMapping(value = "login")
	public String login(HttpServletRequest user) throws JSONException{
		Map<String,Object> result=new HashMap<>();

    	String userName=user.getParameter("username");
    	String password=user.getParameter("password");
		result=userSrvice.login(userName, password);
		JSONObject j=new JSONObject(result.toString());
		return j.toString();
	}
	
    @RequestMapping(value = "register")
	public String register(HttpServletRequest user) throws JSONException{
		//TODO
    	Map<String,Object> result=new HashMap<>();
    	UserInfo userInfo=new UserInfo();
    	
    	userInfo.setUserName(user.getParameter("username"));
    	userInfo.setPassword(user.getParameter("password"));
    	userInfo.setAddress(user.getParameter("address"));
    	userInfo.setEmail(user.getParameter("mail"));
    	userInfo.setNickName(user.getParameter("nickname"));
    	userInfo.setPhoneNumber(user.getParameter("phone"));
		result= userSrvice.register(userInfo);
		JSONObject j=new JSONObject(result.toString());
		
		return j.toString();
	}
	
}
