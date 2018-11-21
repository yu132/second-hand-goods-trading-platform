package se.service.buyer.buy;

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
import se.model.ShoppingTrolley;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.repositories.OrderRepository;
import se.repositories.ShoppingTrolleyRepository;
import se.service.buyer.BuyerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BuyGoodsFromShoppingTrolleyTest {
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
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
		
		goods.setSellerId(user.getId());
		goods.setState(GoodsState.PASS_CHECK.toString());
		goods.setCommitTime(new Date(123456789));
		
		goodsRepository.save(goods);
		
		ShoppingTrolley shoppingTrolley=new ShoppingTrolley();
		
		shoppingTrolley.setUserId(user2.getId());
		shoppingTrolley.setGoodsId(goods.getId());
		shoppingTrolley.setAmount(2);
		shoppingTrolley.setAddTime(new Date(123456789));

		shoppingTrolleyRepository.save(shoppingTrolley);
	}
	
	@Test
	public void testOK() {
		Map<String,Object> res=buyerService.buyGoodsFromShoppingTrolley(user2.getId());
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
	}
	
	@Test
	public void testUserIdIsNull() {
		Map<String,Object> res=buyerService.buyGoodsFromShoppingTrolley(null);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL,res.get("Reason"));
	}
	
	/**
	 * 有可能商品在被添加到购物车后被别人买走了，这个时候需要提示这种情况
	 */
	@Test
	public void testGoodsHasBeenBuyed() {
		goods.setState(GoodsState.BUYED.toString());
		
		goodsRepository.save(goods);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_BUYED,res.get("Reason"));
	}
	
	@After
	public void clean(){
		try{
			Integer OrderId=(Integer) res.get("OrderId");
			orderRepository.deleteById(OrderId);
		}catch(Exception e){}
		
		try{
			prepareAndClean.cleanGoods(goods);
		}catch (Exception e) {}
		
		prepareAndClean.cleanDefaultUser();
		prepareAndClean.cleanDefaultUser2();
	}
}
