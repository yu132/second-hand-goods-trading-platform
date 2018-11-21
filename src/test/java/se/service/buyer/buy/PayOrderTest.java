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
public class PayOrderTest {

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
	public void testOrderIdIsNull() {
		
	}
	
	@Test
	public void testIllegalUser() {
		
	}
	
	/**
	 * 订单状态不是需要支付
	 */
	@Test
	public void testIllegalOrderState() {
		
	}
	
}
