package se.service.user;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.Application;
import se.enumDefine.UserState.UserState;
import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.reason.Reason;
import se.model.UserInfo;
import se.repositories.UserInfoRepository;
import se.service.UserService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RegisterTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	private UserInfo userInfo;
	
	@Before
	public void beforeTest(){
		userInfo=new UserInfo();
		
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
		Assert.assertEquals(ExecuteState.SUCCESS, map.get("State"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertTrue(userInfoRepository.exists(userInfoExample));
		
		Assert.assertEquals(new Double(0.0), userInfo.getBalance());
		
		Assert.assertEquals(UserState.NORMAL, userInfo.getState());
		
		Assert.assertNotNull(userInfo.getRegisterTime());
	}
	
	@Test
	public void testUserIsNull(){
		userInfo=null;
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USER_INFO_IS_NULL, map.get("Reason"));
	}

	@Test
	public void testUserNameIsNull(){
		userInfo.setUserName(null);
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_IS_NULL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testUserNameToShort(){
		userInfo.setUserName("ts");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_TOO_SHORT, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testUserNameToLong(){
		userInfo.setUserName("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testUserNameIllegalCharacter(){
		userInfo.setUserName("666666@a");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.USERNAME_ILLEAGAL_CHARACTER, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPasswordIsNull(){
		userInfo.setPassword(null);
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PASSWORD_IS_NULL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPasswordToShort(){
		userInfo.setPassword("ts");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PASSWORD_TOO_SHORT, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPasswordToLong(){
		userInfo.setPassword("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PASSWORD_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPasswordIllegalCharacter(){
		userInfo.setPassword("大肥羊66666666");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PASSWORD_ILLEAGAL_CHARACTER, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testNickNameIsNull(){
		userInfo.setNickName(null);
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.NICKNAME_IS_NULL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testNickNameToShort(){
		userInfo.setNickName("s");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.NICKNAME_TOO_SHORT, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testNickNameToLong(){
		userInfo.setNickName("veryveryloooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.NICKNAME_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testEmailIsNull(){
		userInfo.setEmail(null);
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.EMAIL_IS_NULL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testEmailToLong(){
		userInfo.setEmail("email_address_veeeeery_verrrrrry_looooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooong@qq.com");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.EMAIL_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testEmailIllegal1(){
		userInfo.setEmail("asdjp12das");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.EMAIL_ILLEGAL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testEmailIllegal2(){
		userInfo.setEmail("asdjp12das@e1231");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.EMAIL_ILLEGAL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void tesPhoneNumberToLong(){
		userInfo.setPhoneNumber("1111111111111111111111111111111111111111111111111111111111111111");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PHONE_NUMBER_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPhoneNumberIllegal1(){
		userInfo.setPhoneNumber("15098796072111");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PHONE_NUMBER_ILLEGAL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testPhoneNumberIllegal2(){
		userInfo.setPhoneNumber("15098a96072");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.PHONE_NUMBER_ILLEGAL, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
	}
	
	@Test
	public void testAddressToLong(){
		userInfo.setAddress("address veeeeery verrrrrry looooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooooo"
				+ "oooooooooooooooooooooooooooooooooooooooong");
		Map<String,Object> map=userService.register(userInfo);
		
		Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
		Assert.assertEquals(Reason.ADDERSS_TOO_LONG, map.get("Reason"));
		
		Example<UserInfo> userInfoExample = Example.of(userInfo);
		Assert.assertFalse(userInfoRepository.exists(userInfoExample));
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
			
			Assert.assertEquals(ExecuteState.ERROR, map.get("State"));
			Assert.assertEquals(Reason.USERNAME_EXIST, map.get("Reason"));
			
			Example<UserInfo> userInfoExample = Example.of(userInfo);
			Assert.assertFalse(userInfoRepository.exists(userInfoExample));
		}finally{
			prepareAndClean.cleanUser(userInfoPrepare);
		}
	}
	
	@After
	public void clean(){
		try{
			prepareAndClean.cleanUser(userInfo);
		}catch (Exception e) {}
	}
	
}
