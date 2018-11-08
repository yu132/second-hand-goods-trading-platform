package se.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.repositories.UserInfoRepository;

@Service
public class PrepareAndClean {

	private final UserInfo DEFAULT_USER;
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
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public void prepareUser(UserInfo userInfo){
		userInfoRepository.save(userInfo);
	}
	
	public void cleanUser(UserInfo userInfo){
		userInfoRepository.delete(userInfo);
	}
	
	public UserInfo prepareDefaultUser(){
		if(!DefaultUserExist){
			userInfoRepository.save(DEFAULT_USER);
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
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	public void prepareGoods(Goods goods){
		goodsRepository.save(goods);
	}
	
	public void cleanGoods(Goods goods){
		goodsRepository.delete(goods);
	}
	
}
