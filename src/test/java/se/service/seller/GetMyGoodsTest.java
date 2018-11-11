package se.service.seller;

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
import se.service.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GetMyGoodsTest {
	
	//getmygoodspage未测试
	//126行原因有误
	

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private final int AMOUNT_OF_GOODS_EACH_PAGE=SellerService.AMOUNT_OF_GOODS_EACH_PAGE;
	
	private List<Goods> glist=new ArrayList<>(AMOUNT_OF_GOODS_EACH_PAGE*10);
	
	private UserInfo user;
	
	@Before
	public void prepare(){
		user=prepareAndClean.prepareDefaultUser();
		
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			Goods goods=new Goods();
			
			goods.setGoodsName("肥羊"+i);
			goods.setPrice(100.0+i);
			goods.setAmount(i);
			goods.setDescription("大肥羊: weight="+(i+40)+"kg");
			goods.setEmailRemind(Boolean.TRUE);
			
			sellerService.addGoods(user.getId(), goods);
			
			glist.add(goods);
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		
		//由于后加的商品应该在前面，所以这样排序
		glist.sort((Goods goods1,Goods goods2)->{
			
			long time1=goods1.getCommitTime().getTime();
			long time2=goods2.getCommitTime().getTime();
			
			return Long.compare(time2, time1);
		});
	}
	
	@Test
	public void testOK(){
		//前9页，每页 AMOUNT_OF_GOODS_EACH_PAGE 个
		for(int i=1;i<=9;i++){
			Map<String,Object> res=sellerService.getMyGoods(user.getId(), i);
			
			Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
			
			@SuppressWarnings("unchecked")
			List<Goods> goods=(List<Goods>) res.get("GoodsList");
			
			Assert.assertEquals(AMOUNT_OF_GOODS_EACH_PAGE, goods.size());
			
			for(int j=0;j<AMOUNT_OF_GOODS_EACH_PAGE;j++){
				Assert.assertTrue(glist.get((i-1)*AMOUNT_OF_GOODS_EACH_PAGE+j).equals(goods.get(j)));
			}
		}
		
		//最后一页，只有AMOUNT_OF_GOODS_EACH_PAGE-5个
		Map<String,Object> res=sellerService.getMyGoods(user.getId(), 10);
		
		Assert.assertEquals(ExecuteState.SUCCESS,res.get("State"));
		
		@SuppressWarnings("unchecked")
		List<Goods> goods=(List<Goods>) res.get("GoodsList");
		
		Assert.assertEquals(AMOUNT_OF_GOODS_EACH_PAGE-5, goods.size());
		
		for(int j=0;j<AMOUNT_OF_GOODS_EACH_PAGE-5;j++){
			Assert.assertTrue(glist.get(9*AMOUNT_OF_GOODS_EACH_PAGE+j).equals(goods.get(j)));
		}
	}
	
	@Test
	public void testPageOutOfBound(){
		Map<String,Object> res=sellerService.getMyGoods(user.getId(), 11);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_PAGE_OUT_OF_BOUNDS,res.get("Reason"));
		
		Assert.assertNull(res.get("GoodsList"));
	}
	
	@Test
	public void testUserIdIsNull(){
		Map<String,Object> res=sellerService.getMyGoods(null, 1);
		
		Assert.assertEquals(ExecuteState.ERROR,res.get("State"));
		
		Assert.assertEquals(Reason.GOODS_PAGE_OUT_OF_BOUNDS,res.get("Reason"));
		
		Assert.assertNull(res.get("GoodsList"));
	}
	
	@Test
	public void testPageIllegal(){
		Map<String,Object> res=sellerService.getMyGoods(user.getId(), -1);
		
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
