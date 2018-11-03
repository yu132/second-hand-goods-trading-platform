package se.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.model.UserInfo;
import se.service.UserService;

@RestController
@RequestMapping("/secure/lessons")
public class UserController {

	private UserService userSrvice;
	public Object login(String userName,String password){
		//TODO
		return userSrvice.login(userName, password);
	}
	
	public Object register(UserInfo userInfo){
		//TODO
		return userSrvice.register(userInfo);
	}
	
}
