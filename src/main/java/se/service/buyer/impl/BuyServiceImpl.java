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
import se.repositories.GoodsRepository;
import se.service.buyer.BuyService;

@Service
public class BuyServiceImpl implements BuyService {

	@Autowired
	private GoodsRepository goodsRepository;
	@Override
	public Map<String,Object> buyGoodsFromGoodsId(Integer userId,Integer goodsId,Integer amount){
		
		//TODO
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
		order.setGoodsId(goodsId);
		return null;
	}
	
	@Override
	public Map<String,Object> buyGoodsFromShoppingTrolley(Integer userId){
		
		//TODO
		
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
