package se.service.buyer.search;

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
import se.service.buyer.BuyerService;
import se.service.seller.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GetGoodsInformationTest {

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private UserInfo user;
	
	private Goods goods;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		goods=new Goods();
		
		goods.setGoodsName("肥羊");
		goods.setPrice(100.0);
		goods.setAmount(1);
		goods.setDescription("大肥羊: weight="+40+"kg");
		goods.setEmailRemind(Boolean.TRUE);
		
		sellerService.addGoods(user.getId(), goods);
	}
	
	@Test
	public void testOk(){
		Map<String, Object> res=buyerService.getGoodsInformation(goods.getId());
		System.out.println(res.get("Reason"));
		Assert.assertEquals(ExecuteState.SUCCESS, res.get("State"));
		
		Assert.assertEquals(goods, res.get("Goods"));
	}
	
	@Test
	public void testGoodsNotExist(){
		Map<String, Object> res=buyerService.getGoodsInformation(-1);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_NOT_EXIST, res.get("Reason"));
	}
	
	@Test
	public void testGoodsIdIsNull(){
		Map<String, Object> res=buyerService.getGoodsInformation(null);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_ID_IS_NULL, res.get("Reason"));
	}
	
	@After
	public void clean(){
		try{
			prepareAndClean.cleanGoods(goods);
		}catch (Exception e) {}
		
		prepareAndClean.cleanDefaultUser();
	}
	
}
