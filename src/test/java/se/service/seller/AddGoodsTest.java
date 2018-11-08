package se.service.seller;

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
import se.repositories.UserInfoRepository;
import se.service.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AddGoodsTest {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	private UserInfo user;
	
	private Goods goods;
	
	@Before
	public void prepare(){
		
		user=prepareAndClean.prepareDefaultUser();
		
		goods=new Goods();
		
		goods.setSellerId(user.getId());
		
		goods.setGoodsName("肥羊");
		goods.setPrice(111.11);
		goods.setAmount(1);
		goods.setDescription("大肥羊");
		goods.setEmailRemind(Boolean.TRUE);
	}
	
	@Test
	public void testOk(){
		Integer uid=user.getId();
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.SUCCESS, res.get("State"));
		
		Assert.assertEquals(uid, goods.getSellerId());
		
		Assert.assertEquals(GoodsState.WAIT_CHECK.toString(), goods.getState());
		
		Assert.assertNotNull(goods.getCommitTime());
		
		Assert.assertNotNull(goods.getId());
	}
	
	@Test
	public void testExist(){
		Goods goods1=new Goods();
		
		try{
			goods1.setGoodsName("肥羊");
			goods1.setPrice(111.11);
			goods1.setAmount(1);
			goods1.setDescription("大肥羊");
			goods1.setEmailRemind(Boolean.TRUE);
			
			goods1.setSellerId(user.getId());
			
			prepareAndClean.prepareGoods(goods1);
			
			Integer uid=user.getId();
			
			Map<String, Object> res=sellerService.addGoods(uid, goods);
			
			Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
			
			Assert.assertEquals(Reason.GOODS_EXIST, res.get("Reason"));
			
			Assert.assertNull(goods.getId());
		}finally{
			prepareAndClean.cleanGoods(goods1);
		}
	}
	
	@Test
	public void testUserIdIsNull(){
		Integer uid=null;
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testGoodsIsNull(){
		Integer uid=user.getId();
		
		goods=null;
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_IS_NULL, res.get("Reason"));
	}
	
	@Test
	public void testGoodsNameIsNull(){
		Integer uid=user.getId();
		
		goods.setGoodsName(null);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NAME_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testGoodsNameTooLong(){
		StringBuilder sb=new StringBuilder(300);
		
		for(int i=0;i<300/6;i++){
			sb.append("物品名称测试");
		}
		
		String goodsName=sb.toString();
		
		Integer uid=user.getId();
		
		goods.setGoodsName(goodsName);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NAME_TOO_LONG, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testPriceIsNull(){
		Integer uid=user.getId();
		
		goods.setPrice(null);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.PRICE_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testPriceNegative(){
		Integer uid=user.getId();
		
		goods.setPrice(-1.0);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.PRICE_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testAmountIsNull(){
		Integer uid=user.getId();
		
		goods.setAmount(null);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testAmountNegative(){
		Integer uid=user.getId();
		
		goods.setAmount(-10);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testAmountZero(){
		Integer uid=user.getId();
		
		goods.setAmount(0);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testDescriptionTooLong(){
		StringBuilder sb=new StringBuilder(300);
		
		for(int i=0;i<300/4;i++){
			sb.append("描述测试");
		}
		
		String description=sb.toString();
		
		Integer uid=user.getId();
		
		goods.setDescription(description);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.DESCRIPTION_TOO_LONG, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@Test
	public void testEmailRemindIsNull(){
		Integer uid=user.getId();
		
		goods.setEmailRemind(null);
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.EMAIL_REMIND_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goods.getId());
	}
	
	@After
	public void clean(){
		try{
			prepareAndClean.cleanGoods(goods);
		}catch(Exception e){}
		
		prepareAndClean.cleanDefaultUser();
	}
	
}
