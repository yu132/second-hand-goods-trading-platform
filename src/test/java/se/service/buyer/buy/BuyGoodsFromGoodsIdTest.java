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
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.repositories.OrderRepository;
import se.service.buyer.BuyerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BuyGoodsFromGoodsIdTest {
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private GoodsRepository goodsRepository;

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
	}
	
	@Test
	public void testOK(){
		res=buyerService.buyGoodsFromGoodsId(user2.getId(), goods.getId(), 2);
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
	}
	
	@Test
	public void testUserIdIsNull(){
		res=buyerService.buyGoodsFromGoodsId(null, goods.getId(), 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testGoodsIdIsNull(){
		res=buyerService.buyGoodsFromGoodsId(user2.getId(), null, 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_ID_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testAmountIsNull(){
		res=buyerService.buyGoodsFromGoodsId(user2.getId(), goods.getId(), null);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testAmountIsIllegal(){
		res=buyerService.buyGoodsFromGoodsId(user2.getId(), goods.getId(), -1);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_IS_NEGATIVE_OR_ZERO,res.get("Reason"));
	}
	
	/**
	 * 数量超过商品个数
	 */
	@Test
	public void testAmountExcessive(){
		res=buyerService.buyGoodsFromGoodsId(user2.getId(), goods.getId(), 6);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.AMOUNT_EXCESSIVE,res.get("Reason"));
	}
	
	/**
	 * 不能购买自己的商品
	 */
	@Test
	public void testAddBySeller(){
		res=buyerService.buyGoodsFromGoodsId(user.getId(), goods.getId(), 6);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.ILLEGAL_OPERATION_TO_OWN_GOODS,res.get("Reason"));
	}
	
	@Test
	public void testGoodsNotExist(){
		res=buyerService.buyGoodsFromGoodsId(user.getId(), 0, 2);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NOT_EXIST,res.get("Reason"));
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
