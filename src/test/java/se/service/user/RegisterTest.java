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
import se.model.UserInfo;
import se.service.UserService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RegisterTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private UserInfo userInfo=new UserInfo();
	
	@Before
	public void beforeTest(){
		userInfo.setUserName("Test_User_Name123");
		userInfo.setPassword("#Test^(P@sswOrd!)+");
		userInfo.setNickName("TestNickName");
		userInfo.setEmail("test@se.com");
		userInfo.setPhoneNumber("18820765428");
		userInfo.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼229宿舍");
	}
	
	@Test
	public void testOK(){
		Map<String,Object> map=userService.register(userInfo);
		Assert.assertEquals("SUCCESS", map.get("State"));
	}
	
	@Test
	public void testUserNameToShort(){
		userInfo.setUserName("ts");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("USERNAME_TOO_SHORT", map.get("Reason"));
	}
	
	@Test
	public void testUserNameToLong(){
		userInfo.setUserName("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("USERNAME_TOO_LONG", map.get("Reason"));
	}
	
	@Test
	public void testUserNameIllegalCharacter(){
		userInfo.setUserName("666666@a");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("USERNAME_ILLEAGAL_CHARACTER", map.get("Reason"));
	}
	
	@Test
	public void testPasswordToShort(){
		userInfo.setPassword("ts");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("PASSWORD_TOO_SHORT", map.get("Reason"));
	}
	
	@Test
	public void testPasswordToLong(){
		userInfo.setPassword("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("PASSWORD_TOO_LONG", map.get("Reason"));
	}
	
	@Test
	public void testPasswordIllegalCharacter(){
		userInfo.setPassword("大肥羊666666");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("PASSWORD_ILLEAGAL_CHARACTER", map.get("Reason"));
	}
	
	@Test
	public void testNickNameToShort(){
		userInfo.setNickName("s");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("NICKNAME_TOO_SHORT", map.get("Reason"));
	}
	
	@Test
	public void testNickNameToLong(){
		userInfo.setNickName("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("NICKNAME_TOO_LONG", map.get("Reason"));
	}
	
	@Test
	public void testEmailIllegal1(){
		userInfo.setEmail("asdjp12das");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("EMAIL_ILLEGAL", map.get("Reason"));
	}
	
	@Test
	public void testEmailIllegal2(){
		userInfo.setEmail("asdjp12das@e1231");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("EMAIL_ILLEGAL", map.get("Reason"));
	}
	
	@Test
	public void testPhoneNumberIllegal1(){
		userInfo.setPhoneNumber("15098796072111");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("PHONE_NUMBER_ILLEGAL", map.get("Reason"));
	}
	
	@Test
	public void testPhoneNumberIllegal2(){
		userInfo.setPhoneNumber("15098a96072");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("PHONE_NUMBER_ILLEGAL", map.get("Reason"));
	}
	
	@Test
	public void testAddressToLong(){
		userInfo.setAddress("address veeeeery verrrrrry looooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals("ERROR", map.get("State"));
		Assert.assertEquals("ADDERSS_TOO_LONG", map.get("Reason"));
	}

	@Test
	public void testUserNameExist(){
		UserInfo userInfoPrepare=new UserInfo();
		try{
			userInfoPrepare.setUserName("Test_User_Name123");
			userInfoPrepare.setPassword("123456");
			userInfoPrepare.setNickName("NickName");
			userInfoPrepare.setEmail("test123@se.com");
			userInfoPrepare.setPhoneNumber("18820765427");
			userInfoPrepare.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼228宿舍");
			
			prepareAndClean.prepareUser(userInfoPrepare);
			
			Map<String,Object> map=userService.register(userInfo);
			
			Assert.assertEquals("ERROR", map.get("State"));
			Assert.assertEquals("ADDERSS_TOO_LONG", map.get("Reason"));
		}finally{
			prepareAndClean.cleanUser(userInfoPrepare);
		}
	}
	
}
