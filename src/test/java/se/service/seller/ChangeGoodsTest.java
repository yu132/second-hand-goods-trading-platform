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
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.UserInfo;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ChangeGoodsTest {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private UserInfo user;
	
	private UserInfo user2;
	
	private Goods goodsToChange;
	
	private Goods goodsExist;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		user2=prepareAndClean.prepareDefaultUser2();
		
		goodsExist.setGoodsName("肥羊");
		goodsExist.setPrice(100.0);
		goodsExist.setAmount(1);
		goodsExist.setDescription("大肥羊: weight="+40+"kg");
		goodsExist.setEmailRemind(Boolean.TRUE);
		
		sellerService.changeGoods(user.getId(), goodsExist);
		
		goodsToChange.setId(goodsExist.getId());
		
		goodsToChange.setGoodsName("大号肥羊");
		goodsToChange.setPrice(50.0);
		goodsToChange.setAmount(1);
		goodsToChange.setDescription("巨大肥羊: weight="+400+"kg");
		goodsToChange.setEmailRemind(Boolean.TRUE);
	}
	
	@Test
	public void testOK(){
		Map<String,Object> res=sellerService.changeGoods(user.getId(), goodsToChange);
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
	}
	
	@Test
	public void testAuthIllegal(){
		Map<String,Object> res=sellerService.changeGoods(user2.getId(), goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_AUTH_ILLEGAL,res.get("Reason"));
	}
	
	@Test
	public void testGoodsNotExist(){
		goodsToChange.setId(0);
		
		Map<String,Object> res=sellerService.changeGoods(user2.getId(), goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NOT_EXIST,res.get("Reason"));
	}
	
	@Test
	public void testExist(){
		Goods goods1=new Goods();
		
		try{
			goods1.setGoodsName("大号肥羊");
			goods1.setPrice(111.11);
			goods1.setAmount(1);
			goods1.setDescription("大肥羊");
			goods1.setEmailRemind(Boolean.TRUE);
			
			goods1.setSellerId(user.getId());
			
			prepareAndClean.prepareGoods(goods1);
			
			Integer uid=user.getId();
			
			Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
			
			Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
			
			Assert.assertEquals(Reason.GOODS_EXIST, res.get("Reason"));
			
			Assert.assertNull(goodsToChange.getId());
		}finally{
			prepareAndClean.cleanGoods(goods1);
		}
	}
	
	@Test
	public void testUserIdIsNull(){
		Integer uid=null;
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testGoodsIsNull(){
		Integer uid=user.getId();
		
		goodsToChange=null;
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_ID_IS_NULL, res.get("Reason"));
	}
	
	@Test
	public void testGoodsIdIsNull(){
		Integer uid=user.getId();
		
		goodsToChange.setId(null);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testGoodsNameTooLong(){
		StringBuilder sb=new StringBuilder(300);
		
		for(int i=0;i<300/6;i++){
			sb.append("物品名称测试");
		}
		
		String goodsName=sb.toString();
		
		Integer uid=user.getId();
		
		goodsToChange.setGoodsName(goodsName);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NAME_TOO_LONG, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testPriceNegative(){
		Integer uid=user.getId();
		
		goodsToChange.setPrice(-1.0);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.PRICE_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testAmountNegative(){
		Integer uid=user.getId();
		
		goodsToChange.setAmount(-10);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testAmountZero(){
		Integer uid=user.getId();
		
		goodsToChange.setAmount(0);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@Test
	public void testDescriptionTooLong(){
		StringBuilder sb=new StringBuilder(300);
		
		for(int i=0;i<300/4;i++){
			sb.append("描述测试");
		}
		
		String description=sb.toString();
		
		Integer uid=user.getId();
		
		goodsToChange.setDescription(description);
		
		Map<String, Object> res=sellerService.changeGoods(uid, goodsToChange);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.DESCRIPTION_TOO_LONG, res.get("Reason"));
		
		Assert.assertNull(goodsToChange.getId());
	}
	
	@After
	public void clean(){
		try{
			prepareAndClean.cleanGoods(goodsToChange);
		}catch (Exception e) {}
		try{
			prepareAndClean.cleanGoods(goodsExist);
		}catch (Exception e) {}
		
		prepareAndClean.cleanDefaultUser();
		prepareAndClean.cleanDefaultUser2();
	}
	
}
