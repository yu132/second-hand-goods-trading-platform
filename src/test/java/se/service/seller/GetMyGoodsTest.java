package se.service.seller;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.Application;
import se.model.Goods;
import se.model.UserInfo;
import se.service.SellerService;
import se.util.PrepareAndClean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GetMyGoodsTest {

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PrepareAndClean prepareAndClean;
	
	private final int AMOUNT_OF_GOODS_EACH_PAGE=SellerService.AMOUNT_OF_GOODS_EACH_PAGE;
	
	private List<Goods> glist=new ArrayList<>(AMOUNT_OF_GOODS_EACH_PAGE*10);
	
	@Before
	public void prepare(){
		UserInfo defaultUser=prepareAndClean.prepareDefaultUser();
		
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			Goods goods=new Goods();
			
			goods.setGoodsName("肥羊"+i);
			goods.setPrice(100.0+i);
			goods.setAmount(i);
			goods.setDescription("大肥羊: weight="+(i+40)+"kg");
			goods.setEmailRemind(Boolean.TRUE);
			
			sellerService.addGoods(defaultUser.getId(), goods);
			
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
		//前9页，每页10个
		for(int i=0;i<9;i++){
			
		}
		//最后一页，只有5个
	}
	
	@Test
	public void testPageOutOfIndex(){
		
	}
	
	@After
	public void clean(){
		prepareAndClean.cleanDefaultUser();
		
		for(int i=0;i<AMOUNT_OF_GOODS_EACH_PAGE*10-5;i++){
			prepareAndClean.cleanGoods(glist.get(i));
		}
	}
}
