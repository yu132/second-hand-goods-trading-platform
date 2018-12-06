package se.service.buyer.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.orderState.OrderState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.Order;
import se.model.OrderTimes;
import se.model.ShoppingTrolley;
import se.model.UserReceiverInfo;
import se.repositories.GoodsRepository;
import se.repositories.OrderRepository;
import se.repositories.ShoppingTrolleyRepository;
import se.repositories.UserInfoRepository;
import se.repositories.UserReceiverInfoRepository;
import se.service.buyer.BuyService;
import se.service.util.DateUtil;

@Service
public class BuyServiceImpl implements BuyService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	ShoppingTrolleyRepository shoppingTrolleyRepository;
	@Autowired
	UserReceiverInfoRepository userReceiverInfoRepository;
	@Autowired
	private DateUtil dateUtil;
	
	public final static int AMOUNT_OF_ORDER_EACH_PAGE=10;
	@Override
	/**
	 * 从商品页买，点击立即购买触发，生成订单部分信息
	 */
	public Map<String,Object> buyGoodsFromGoodsId(Integer userId,Integer goodsId,Integer amount){
		
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(goodsId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_ID_IS_NULL);
			return result;
		}
		if(amount==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_IS_NULL);
			return result;
		}else if(amount<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		Goods goods=goodsRepository.getOne(goodsId);
		if(goods==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_NOT_EXIST);
			return result;
		}
		if(goods.getSellerId()==userId) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.ILLEGAL_OPERATION_TO_OWN_GOODS);
			return result;
		}
		Integer currentAmount=goods.getAmount();
		if(currentAmount<amount) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_EXCESSIVE);
			return result;
		}
		goods.setAmount(currentAmount-amount);
		goodsRepository.saveAndFlush(goods);
		
		Order order=new Order();
		order.setAmount(amount);
		order.setBuyerId(userId);
		order.setSellerId(goods.getSellerId());
		order.setState(OrderState.PLACE_AN_ORDER.toString());
		order.setGoodsId(goodsId);
		order.setTotalPrice(amount*goods.getPrice());
		
		
		OrderTimes orderTimes=new OrderTimes();
		//OrderTimes添加下单时间，order添加OrderTimes
		orderTimes.setOrderTime(dateUtil.getCurrentDate());
		order.setOrderTime(orderTimes);
		orderRepository.save(order);
		
		result.put("State", ExecuteState.SUCCESS);
		return result;
		
	}
	
	@Override
	/**
	 * 购物车下单，生成订单部分信息
	 */
	public Map<String,Object> buyGoodsFromShoppingTrolley(Integer userId){
		
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		Iterable<ShoppingTrolley> shoppingTrolleys=shoppingTrolleyRepository.findAllByUserId(userId);
		
		OrderTimes orderTimes=new OrderTimes();
		orderTimes.setOrderTime(dateUtil.getCurrentDate());//下单时间
		for(ShoppingTrolley s:shoppingTrolleys) {
			//每条购物车生成一个订单
			Order order=new Order();
			Goods goods=goodsRepository.getOne(s.getGoodsId());
			order.setSellerId(goods.getSellerId());
			order.setBuyerId(s.getUserId());
			order.setGoodsId(s.getGoodsId());
			order.setAmount(s.getAmount());
			order.setState(OrderState.PLACE_AN_ORDER.toString());
			order.setTotalPrice(order.getAmount()*goods.getPrice());
			order.setOrderTime(orderTimes);
			
			orderRepository.save(order);
			shoppingTrolleyRepository.delete(s);//从购物车中移除该条信息
		}
		result.put("State", ExecuteState.SUCCESS);
		return result;
	}
	/**
	 * 给订单添加收货信息
	 */
	@Override
	public Map<String,Object> checkAndAddReceivingInformation(Integer userId,Integer orderId,Integer receivingInformationId){
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(orderId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", null);
			return result;
		}
		if(receivingInformationId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", null);
			return result;
		}
		Order order=orderRepository.getOne(orderId);
		UserReceiverInfo userReceiverInfo= userReceiverInfoRepository.getOne(receivingInformationId);
		order.setBuyerName(userReceiverInfo.getRecriverName());
		order.setBuyerAddress(userReceiverInfo.getRecriverAddress());
		//order.setRemarks(remarks);
		//order.setWayOfPay(wayOfPay);
		//TODO
		orderRepository.saveAndFlush(order);
		
		result.put("State", ExecuteState.SUCCESS);
		return result;
	}
	/**
	 * 给订单付款
	 */
	@Override
	public Map<String,Object> payOrder(Integer userId,Integer orderId){
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(orderId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", null);
			return result;
		}
		Order order=orderRepository.getOne(orderId);
		order.setState(OrderState.PAY_AN_ORDER.toString());
		orderRepository.saveAndFlush(order);

		result.put("State", ExecuteState.SUCCESS);
		return result;
		
	}
	/**
	 * 得到某人某订单
	 */
	@Override
	public Map<String,Object> getOrder(Integer userId,Integer orderId){
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(orderId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", null);
			return result;
		}
		Order order = orderRepository.getOne(orderId);
		result.put("State", ExecuteState.SUCCESS);
		result.put("Order", order);
		return result;
	}
	/**
	 * 得到某人所有订单
	 */
	@Override
	public Map<String,Object> getAllOrder(Integer userId,Integer page){

		//TODO
		Map<String,Object> result=new HashMap<>();
		Map<String,Object> getMyOrderPageResult=new HashMap<>();
		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}
		if(page<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}
		getMyOrderPageResult=getMyOrderPage(userId);
		Reason reason=(Reason) getMyOrderPageResult.get("Reason");
		if(reason!=null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",reason);
			return result;
		}
		Long pageNum=(Long)getMyOrderPageResult.get("PageNum");
		if(page>pageNum) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}else {
			Pageable pageable = new PageRequest(page.intValue(), AMOUNT_OF_ORDER_EACH_PAGE);
	        Page<Order> orderList = orderRepository.findBySellerId(userId, pageable);
	        result.put("State", ExecuteState.SUCCESS);
	        result.put("OrderList", orderList.getContent());
			return result;
		}
	}
	
	/**
	 * 得到某种订单状态的所有订单
	 */
	
	@Override
	public Map<String,Object> getAllOrderByType(Integer userId,Integer page,OrderState orderState){
		Map<String,Object> result=new HashMap<>();
		Map<String,Object> getMyOrderPageResult=new HashMap<>();

		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}
		if(page<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}
		getMyOrderPageResult=getMyOrderPageByState(userId,orderState);
		Reason reason=(Reason) getMyOrderPageResult.get("Reason");
		if(reason!=null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",reason);
			return result;
		}
		Long pageNum=(Long)getMyOrderPageResult.get("PageNum");
		if(page>pageNum) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}else {
			Pageable pageable = new PageRequest(page.intValue(), AMOUNT_OF_ORDER_EACH_PAGE);
	        Page<Order> orderList = orderRepository.findBySellerId(userId, pageable);
	        result.put("State", ExecuteState.SUCCESS);
	        result.put("OrderList", orderList.getContent());
			return result;
		}
	}
	
	private Map<String,Object> getMyOrderPage(Integer userId){
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_ID_IS_NULL);
			return result;
		}
		if(userInfoRepository.getOne(userId)==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USERNAME_NOT_EXIST);
			return result;
		}else {
			long orderNum=orderRepository.countBySellerId(userId);
			Long pageNum=orderNum/AMOUNT_OF_ORDER_EACH_PAGE;
			if(pageNum%AMOUNT_OF_ORDER_EACH_PAGE!=0)
				pageNum++;
			result.put("State", ExecuteState.SUCCESS);
			result.put("PageNum", pageNum);
			return result;
		}
	}	
	
	private Map<String,Object> getMyOrderPageByState(Integer userId,OrderState orderState){
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_ID_IS_NULL);
			return result;
		}
		if(userInfoRepository.getOne(userId)==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USERNAME_NOT_EXIST);
			return result;
		}else {
			long orderNum=orderRepository.countBySellerIdAndState(userId, orderState.toString());
			Long pageNum=orderNum/AMOUNT_OF_ORDER_EACH_PAGE;
			if(pageNum%AMOUNT_OF_ORDER_EACH_PAGE!=0)
				pageNum++;
			result.put("State", ExecuteState.SUCCESS);
			result.put("PageNum", pageNum);
			return result;
		}
	}	
}
