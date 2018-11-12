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
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GetGoodsPageTest {

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
			goods.setAmount(i+1);
			goods.setDescription("大肥羊: weight="+(i+40)+"kg");
			goods.setEmailRemind(Boolean.TRUE);
			
			Map<String,Object> res=sellerService.addGoods(user.getId(), goods);
			
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
	public void testOk(){
		Map<String,Object> res=sellerService.getMyGoodsPage(user.getId());
		
		Assert.assertEquals(ExecuteState.SUCCESS, res.get("State"));
		
		Assert.assertEquals(10, res.get("PageNum"));
	}
	
	@Test
	public void testUserIdIsNull(){
		Map<String,Object> res=sellerService.getMyGoodsPage(null);
		
		Assert.assertEquals(ExecuteState.ERROR, res.get("State"));
		
		Assert.assertEquals(Reason.USER_ID_IS_NULL, res.get("Reason"));
	}
	
	@After
	public void clean(){
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			prepareAndClean.cleanGoods(glist.get(i));
		}
		
		prepareAndClean.cleanDefaultUser();
	}
	
}
