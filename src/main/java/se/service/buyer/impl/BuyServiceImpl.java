package se.service.buyer.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import se.enumDefine.orderState.OrderState;
import se.service.buyer.BuyService;

@Service
public class BuyServiceImpl implements BuyService {

	@Override
	public Map<String,Object> buyGoodsFromGoodsId(Integer userId,Integer goodsId){
		
		//TODO
		
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
