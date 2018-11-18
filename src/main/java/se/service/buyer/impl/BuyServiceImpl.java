package se.service.buyer.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.orderState.OrderState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.Order;
import se.model.OrderTimes;
import se.model.ShoppingTrolley;
import se.repositories.GoodsRepository;
import se.repositories.OrderRepository;
import se.repositories.ShoppingTrolleyRepository;
import se.service.buyer.BuyService;
import se.service.util.DateUtil;

@Service
public class BuyServiceImpl implements BuyService {

	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	ShoppingTrolleyRepository shoppingTrolleyRepository;
	@Autowired
	private DateUtil dateUtil;
	@Override
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
	public Map<String,Object> buyGoodsFromShoppingTrolley(Integer userId){
		
		//TODO
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		Iterable<ShoppingTrolley> shoppingTrolleys=shoppingTrolleyRepository.findAllByUserId(userId);
		for(ShoppingTrolley s:shoppingTrolleys) {
			
		}
		return null;
	}
	
	@Override
	public Map<String,Object> checkAndAddReceivingInformation(Integer userId,Integer receivingInformationId){
		
		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> payOrder(Integer userId,Integer orderId){
		
		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> getOrder(Integer userId,Integer orderId){

		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> getAllOrder(Integer userId,Integer page){

		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> getAllOrderByType(Integer userId,Integer page,OrderState orderState){

		//TODO
		
		return null;
	}
}
