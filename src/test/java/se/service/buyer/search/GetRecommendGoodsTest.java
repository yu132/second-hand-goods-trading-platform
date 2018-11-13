package se.service.buyer.search;

import java.util.ArrayList;
import java.util.List;
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
import se.service.buyer.SearchService;
import se.service.seller.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GetRecommendGoodsTest {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private BuyerService buyerService;

	public final Integer AMOUNT_OF_GOODS_EACH_PAGE=SearchService.AMOUNT_OF_GOODS_EACH_PAGE;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private UserInfo user;
	
	private List<Goods> glist=new ArrayList<>(AMOUNT_OF_GOODS_EACH_PAGE*10);
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			Goods goods=new Goods();
			
			goods.setGoodsName("肥羊"+i);
			goods.setPrice(100.0+i);
			goods.setAmount(i+1);
			goods.setDescription("大肥羊: weight="+(i+40)+"kg");
			goods.setEmailRemind(Boolean.TRUE);
			
			sellerService.addGoods(user.getId(), goods);
			
			glist.add(goods);
		}
	}
	
	@Test
	public void testOk(){
		
		for(int i=1;i<10;i++){
			Map<String, Object> res=buyerService.getRecommendGoods(i);
			Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
			
			@SuppressWarnings("unchecked")
			List<Goods> list=(List<Goods>) res.get("GoodsList");

			Assert.assertEquals(AMOUNT_OF_GOODS_EACH_PAGE.intValue(),list.size());
		}
		
		Map<String, Object> res=buyerService.getRecommendGoods(10);
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		@SuppressWarnings("unchecked")
		List<Goods> list=(List<Goods>) res.get("GoodsList");

		Assert.assertEquals(AMOUNT_OF_GOODS_EACH_PAGE.intValue()-5,list.size());
	}
	
	@Test
	public void testPageRangeOutOfBounds(){
		Map<String, Object> res=buyerService.getRecommendGoods(11);
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		Assert.assertEquals(Reason.GOODS_PAGE_OUT_OF_BOUNDS,res.get("Reason"));
	}
	
	@Test
	public void testPageIsNull(){
		Map<String, Object> res=buyerService.getRecommendGoods(null);
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		Assert.assertEquals(Reason.GOODS_PAGE_IS_NULL,res.get("Reason"));
	}
	
	@Test
	public void testPageIllegal(){
		Map<String,Object> res=buyerService.getRecommendGoods(-1);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_PAGE_IS_NEGATIVE_OR_ZERO,res.get("Reason"));
		
		Assert.assertNull(res.get("GoodsList"));
	}
	
	@After
	public void clean(){
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			prepareAndClean.cleanGoods(glist.get(i));
		}
		
		prepareAndClean.cleanDefaultUser();
	}
	
}
