package se.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.model.UserInfo;
import se.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userSrvice;
    @RequestMapping(value = "login")
	public Object login(String userName,String password){
		//TODO
		return userSrvice.login(userName, password);
	}
	
	public Object register(UserInfo userInfo){
		//TODO
		return userSrvice.register(userInfo);
	}
	
}
