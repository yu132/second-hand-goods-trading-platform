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
public class GetGoodsByKeyWordsTest {
	
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
		Map<String, Object> res=buyerService.getGoodsByKeyWords(new String[]{"肥羊"}, 1);
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		@SuppressWarnings("unchecked")
		List<Goods> list=(List<Goods>) res.get("GoodsList");
		
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 如果keywords里面有nullh或者空字符串，直接过滤掉
	 */
	@Test
	public void testKeyWordsContainNull(){
		Map<String, Object> res=buyerService.getGoodsByKeyWords(new String[]{"肥羊",null,""}, 1);
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		@SuppressWarnings("unchecked")
		List<Goods> list=(List<Goods>) res.get("GoodsList");
		
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 如果没给关键词，那么就当作推荐那个方法执行，返回那个方法的返回值（其实是一样的）
	 */
	@Test
	public void testAsRecommoend(){
		Map<String, Object> res=buyerService.getGoodsByKeyWords(null, 1);
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		@SuppressWarnings("unchecked")
		List<Goods> list=(List<Goods>) res.get("GoodsList");
		
		Assert.assertTrue(list.size()>0);
	}
	
	@Test
	public void testPageRangeOutOfBounds(){
		Map<String, Object> res=buyerService.getGoodsByKeyWords(new String[]{"肥羊"}, 20);
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		Assert.assertEquals(Reason.GOODS_PAGE_OUT_OF_BOUNDS,res.get("Reason"));
	}
	
	@Test
	public void testPageIsNull(){
		Map<String, Object> res=buyerService.getGoodsByKeyWords(new String[]{"肥羊"}, -1);
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		Assert.assertEquals(Reason.GOODS_PAGE_IS_NULL,res.get("Reason"));
	}
	
	@After
	public void clean(){
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			prepareAndClean.cleanGoods(glist.get(i));
		}
		
		prepareAndClean.cleanDefaultUser();
	}

}
