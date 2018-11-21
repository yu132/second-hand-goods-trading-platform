package se.service.buyer.buy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CheckAndAddReceivingInformationTest {

	@Before
	public void prepare(){
		
	}
	
	@After
	public void clean(){
		
	}
	
	@Test
	public void testOK() {
		
	}
	
	@Test
	public void testUserIdIsNull() {
		
	}
	
	@Test
	public void testReceivingInformationIdIsNull() {
		
	}
	
	/**
	 * 非本人的订单操作
	 */
	@Test
	public void testIllegalUser() {
		
	}
	
	/**
	 * 订单状态不是需要添加收货信息
	 */
	@Test
	public void testIllegalOrderState() {
		
	}
}
