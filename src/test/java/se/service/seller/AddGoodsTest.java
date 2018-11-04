package se.service.seller;

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
import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.goodsState.GoodsState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
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
	private GoodsRepository goodssRepository;
	
	private UserInfo user;
	
	private Goods goods;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		goods=new Goods();
		
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
		
		Assert.assertEquals(GoodsState.WAIT_CHECK, goods.getState());
		
		Assert.assertNotNull(goods.getTime());
		
		Example<Goods> example=Example.of(goods);
		Assert.assertTrue(goodssRepository.exists(example));
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
			
			prepareAndClean.prepareGoods(goods1);
			
			Integer uid=user.getId();
			
			Map<String, Object> res=sellerService.addGoods(uid, goods);
			
			Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
			
			Assert.assertEquals(Reason.GOODS_EXIST, res.get("Reson"));
			
			Example<Goods> example=Example.of(goods);
			Assert.assertFalse(goodssRepository.exists(example));
		}finally{
			prepareAndClean.cleanGoods(goods1);
		}
	}
	
	@Test
	public void testUserIdIsNull(){
		Integer uid=null;
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL, res.get("Reson"));
		
		Example<Goods> example=Example.of(goods);
		Assert.assertFalse(goodssRepository.exists(example));
	}
	
	@Test
	public void testGoodsIsNull(){
		Integer uid=user.getId();
		
		goods=null;
		
		Map<String, Object> res=sellerService.addGoods(uid, goods);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_IS_NULL, res.get("Reson"));
		
		Example<Goods> example=Example.of(goods);
		Assert.assertFalse(goodssRepository.exists(example));
	}
	
	@Test
	public void testGoodsNameIsNull(){
		
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
		
		Assert.assertEquals(Reason.GOODS_NAME_TOO_LONG, res.get("Reson"));
		
		Example<Goods> example=Example.of(goods);
		Assert.assertFalse(goodssRepository.exists(example));
	}
	
	@Test
	public void testPriceIsNull(){
		
	}
	
	@Test
	public void testPriceNegative(){
		
	}
	
	@Test
	public void testAmountIsNull(){
		
	}
	
	@Test
	public void testAmountNegative(){
		
	}
	
	@Test
	public void testAmountZero(){
		
	}
	
	@Test
	public void testDescriptionTooLong(){
		
	}
	
	@Test
	public void testEmailRemindIsNull(){
		
	}
	
	@After
	public void clean(){
		prepareAndClean.cleanDefaultUser();
		try{
			prepareAndClean.cleanGoods(goods);
		}catch(Exception e){}
	}

}
