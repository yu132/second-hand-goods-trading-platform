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
import se.enumDefine.executeState.State;
import se.enumDefine.goodState.GoodState;
import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;
import se.service.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AddGoodTest {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	private UserInfo user;
	
	private Goods good;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		good=new Goods();
		
		good.setGoodsName("肥羊");
		good.setPrice(111.11);
		good.setAmount(1);
		good.setDescription("大肥羊");
		good.setEmailRemind(Boolean.TRUE);
	}
	
	@Test
	public void testOk(){
		Integer uid=user.getId();
		
		Map<String, Object> res=sellerService.addGood(uid, good);
		
		Assert.assertEquals(State.SUCCESS, res.get("State"));
		
		Assert.assertEquals(uid, good.getSellerId());
		
		Assert.assertEquals(GoodState.WAIT_CHECK, good.getState());
		
		Example<Goods> example=Example.of(good);
		Assert.assertTrue(goodsRepository.exists(example));
	}
	
	@Test
	public void testExist(){
		Goods good1=new Goods();
		
		good1.setGoodsName("肥羊");
		good1.setPrice(111.11);
		good1.setAmount(1);
		good1.setDescription("大肥羊");
		good1.setEmailRemind(Boolean.TRUE);
		
		prepareAndClean.prepareGood(good1);
		
		Integer uid=user.getId();
		
		Map<String, Object> res=sellerService.addGood(uid, good);
		
		Assert.assertEquals("SUCCESS", res.get("State"));
		
		
	}
	
	@Test
	public void testIllegal(){
		
	}
	
	@After
	public void clean(){
		prepareAndClean.cleanDefaultUser();
		try{
			prepareAndClean.cleanGood(good);
		}catch(Exception e){}
	}

}
