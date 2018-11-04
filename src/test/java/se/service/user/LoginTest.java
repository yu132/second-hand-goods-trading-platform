package se.service.user;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.Application;
import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.reason.Reason;
import se.model.UserInfo;
import se.service.UserService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LoginTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private String userName=null;
	
	private String password=null;
	
	@Before
	public void beforeTest(){
		userName="TestUserName";
		password="TestPassword";
	}
	
	@Test
	public void testOK(){
		UserInfo userInfoPrepare=new UserInfo();
		try{
			userInfoPrepare.setUserName("TestUserName");
			userInfoPrepare.setPassword("TestPassword");
			userInfoPrepare.setNickName("NickName");
			userInfoPrepare.setEmail("test123@se.com");
			userInfoPrepare.setPhoneNumber("18820765427");
			userInfoPrepare.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼228宿舍");
		
			prepareAndClean.prepareUser(userInfoPrepare);
			
			Map<String,Object> map=userService.login(userName, password);
			
			Assert.assertEquals(ExecuteState.SUCCESS, map.get("State"));
			
		}finally{
			prepareAndClean.cleanUser(userInfoPrepare);
		}
	}
	
	@Test
	public void testUserIsNull(){
		userName=null;
		Map<String,Object> map=userService.login(userName, password);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_IS_NULL, map.get("Reason"));
	}
	
	@Test
	public void testUserNameNotExist(){
		Map<String,Object> map=userService.login(userName, password);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_NOT_EXIST, map.get("Reason"));
	}
	
	@Test
	public void testPasswordIsNull(){
		password=null;
		Map<String,Object> map=userService.login(userName, password);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PASSWORD_IS_NULL, map.get("Reason"));
	}
	
	@Test
	public void testPasswordIncorrect(){
		UserInfo userInfoPrepare=new UserInfo();
		try{
			userInfoPrepare.setUserName("TestUserName");
			userInfoPrepare.setPassword("TestPassword123");
			userInfoPrepare.setNickName("NickName");
			userInfoPrepare.setEmail("test123@se.com");
			userInfoPrepare.setPhoneNumber("18820765427");
			userInfoPrepare.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼228宿舍");
		
			prepareAndClean.prepareUser(userInfoPrepare);
			
			Map<String,Object> map=userService.login(userName, password);
			
			Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
			Assert.assertEquals(Reason.PASSWORD_INCORRECT, map.get("Reason"));
		}finally{
			prepareAndClean.cleanUser(userInfoPrepare);
		}
	}
	
	@Test
	public void testSQLInjection(){
		userName="' or 1=1#";
		
		Map<String,Object> map=userService.login(userName, password);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_NOT_EXIST, map.get("Reason"));
	}
	
}
