package se.service.buyer.shoppingTrolley;

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
import se.repositories.ShoppingTrolleyRepository;
import se.service.buyer.BuyerService;
import se.service.seller.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AddGoodsToShoppingTrolleyTest {

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private ShoppingTrolleyRepository shoppingTrolleyRepository;

	private UserInfo user;
	
	private UserInfo user2;
	
	private Goods goods;
	
	private Map<String,Object> res;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		user2=prepareAndClean.prepareDefaultUser2();
		
		goods=new Goods();
		
		goods.setGoodsName("肥羊");
		goods.setPrice(100.0);
		goods.setAmount(5);
		goods.setDescription("大肥羊: weight="+40+"kg");
		goods.setEmailRemind(Boolean.TRUE);
		
		sellerService.addGoods(user.getId(), goods);
	}
	
	@Test
	public void testOK(){
		res=buyerService.addGoodsToShoppingTrolley(user2.getId(), goods.getId(), 2);
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
	}
	
	@Test
	public void testUserIdIsNull(){
		res=buyerService.addGoodsToShoppingTrolley(null, goods.getId(), 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testGoodsIdIsNull(){
		res=buyerService.addGoodsToShoppingTrolley(user2.getId(), null, 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_ID_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testAmountIsNull(){
		res=buyerService.addGoodsToShoppingTrolley(user2.getId(), goods.getId(), null);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testAmountIsIllegal(){
		res=buyerService.addGoodsToShoppingTrolley(user2.getId(), goods.getId(), -1);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO,res.get("Reason"));
	}
	
	/**
	 * 数量超过商品个数
	 */
	@Test
	public void testAmountExcessive(){
		res=buyerService.addGoodsToShoppingTrolley(user2.getId(), goods.getId(), 6);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_EXCESSIVE,res.get("Reason"));
	}
	
	/**
	 * 不能把自己的商品加入购物车
	 */
	@Test
	public void testAddBySeller(){
		res=buyerService.addGoodsToShoppingTrolley(user.getId(), goods.getId(), 6);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.ILLEGAL_OPERATION_TO_OWN_GOODS,res.get("Reason"));
	}
	
	@Test
	public void testGoodsNotExist(){
		res=buyerService.addGoodsToShoppingTrolley(user.getId(), 0, 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NOT_EXIST,res.get("Reason"));
	}
	
	@After
	public void clean(){
		/**
		 * 这里无法清除购物车的数据库表，请手动清除,或者必须返回购物车ID
		 */
		try{
			Integer shoppingTrolleyId=(Integer) res.get("ShoppingTrolleyId");
			shoppingTrolleyRepository.deleteById(shoppingTrolleyId);
		}catch(Exception e){}
		
		try{
			prepareAndClean.cleanGoods(goods);
		}catch (Exception e) {}
		
		prepareAndClean.cleanDefaultUser();
		prepareAndClean.cleanDefaultUser2();
	}
	
}
