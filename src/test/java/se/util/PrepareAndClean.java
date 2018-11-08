package se.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.repositories.UserInfoRepository;

@Service
public class PrepareAndClean {
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	private UserInfo DEFAULT_USER;
	private boolean DefaultUserExist;
	
	{
		DEFAULT_USER=new UserInfo();
		DEFAULT_USER.setUserName("Test_User_Name123");
		DEFAULT_USER.setPassword("#Test^(P@sswOrd!)+");
		DEFAULT_USER.setNickName("TestNickName");
		DEFAULT_USER.setEmail("test@se.com");
		DEFAULT_USER.setPhoneNumber("18820765428");
		DEFAULT_USER.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼229宿舍");
	}
	
	public UserInfo prepareDefaultUser(){
		if(!DefaultUserExist){
			DEFAULT_USER=userInfoRepository.save(DEFAULT_USER);
			DefaultUserExist=true;
		}
		return DEFAULT_USER;
	}
	
	public void cleanDefaultUser(){
		if(DefaultUserExist){
			userInfoRepository.deleteById(DEFAULT_USER.getId());
			DefaultUserExist=false;
		}
	}
	
	
	private UserInfo DEFAULT_USER2;
	private boolean DefaultUserExist2;
	
	{
		DEFAULT_USER2=new UserInfo();
		DEFAULT_USER2.setUserName("Test_User_Name123");
		DEFAULT_USER2.setPassword("#Test^(P@sswOrd!)+");
		DEFAULT_USER2.setNickName("TestNickName");
		DEFAULT_USER2.setEmail("test@se.com");
		DEFAULT_USER2.setPhoneNumber("18820765428");
		DEFAULT_USER2.setAddress("山东省济南市高新区舜华路1500号山东大学软件园校区2号宿舍楼229宿舍");
	}
	
	public UserInfo prepareDefaultUser2(){
		if(!DefaultUserExist2){
			DEFAULT_USER2=userInfoRepository.save(DEFAULT_USER);
			DefaultUserExist2=true;
		}
		return DEFAULT_USER2;
	}
	
	public void cleanDefaultUser2(){
		if(DefaultUserExist2){
			userInfoRepository.deleteById(DEFAULT_USER2.getId());
			DefaultUserExist2=false;
		}
	}
	
	
	public void prepareUser(UserInfo userInfo){
		userInfoRepository.save(userInfo);
	}
	
	public void cleanUser(UserInfo userInfo){
		userInfoRepository.delete(userInfo);
	}
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	public void prepareGoods(Goods goods){
		goodsRepository.save(goods);
	}
	
	public void cleanGoods(Goods goods){
		goodsRepository.delete(goods);
	}
	
}
