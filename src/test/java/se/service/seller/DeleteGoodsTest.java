package se.service.seller;

import java.util.Date;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.Application;
import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.goodsState.GoodsState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DeleteGoodsTest {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;

	@Autowired
	private GoodsRepository goodsRepository;
	
	private UserInfo user;
	
	private UserInfo user2;
	
	private Goods goodsToDelete;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		user2=prepareAndClean.prepareDefaultUser2();
		
		goodsToDelete=new Goods();
		
		goodsToDelete.setGoodsName("肥羊");
		goodsToDelete.setPrice(100.0);
		goodsToDelete.setAmount(1);
		goodsToDelete.setDescription("大肥羊: weight="+40+"kg");
		goodsToDelete.setEmailRemind(Boolean.TRUE);
		
		goodsToDelete.setSellerId(user.getId());
		goodsToDelete.setState(GoodsState.PASS_CHECK.toString());
		goodsToDelete.setCommitTime(new Date(123456789));
		
		goodsRepository.save(goodsToDelete);
	}
	
	@Test
	public void testOK(){
		Map<String,Object> res=sellerService.deleteGoods(user.getId(), goodsToDelete.getId());
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		Assert.assertNull(goodsRepository.getOne(goodsToDelete.getId()));
	}
	
	@Test
	public void testAuthIllegal(){
		Map<String,Object> res=sellerService.deleteGoods(user2.getId(), goodsToDelete.getId());
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_AUTH_ILLEGAL,res.get("Reason"));
		
		Assert.assertNotNull(goodsRepository.getOne(goodsToDelete.getId()));
	}
	
	@Test
	public void testUserIdIsNull(){
		Integer uid=null;
		
		Map<String, Object> res=sellerService.deleteGoods(uid, goodsToDelete.getId());
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL,res.get("Reason"));
		
		Assert.assertNotNull(goodsRepository.getOne(goodsToDelete.getId()));
	}
	
	@Test
	public void testGoodsIdIsNull(){
		Integer goodsId=null;
		
		Map<String, Object> res=sellerService.deleteGoods(user.getId(), goodsId);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_ID_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testGoodsNotExist(){
		Map<String, Object> res=sellerService.deleteGoods(user.getId(), 0);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NOT_EXIST,res.get("Reason"));
	}
	
	@After
	public void clean(){
		try{
			prepareAndClean.cleanGoods(goodsToDelete);
		}catch (Exception e) {}
		
		prepareAndClean.cleanDefaultUser();
		prepareAndClean.cleanDefaultUser2();
	}
	
}
