package se.service.buyer.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import se.service.buyer.ShoppingTrolleyService;

@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {

	@Override
	public Map<String,Object> addGoodsToShoppingTrolley(Integer userId,Integer goodsId,Integer amount){
		
		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> deleteGoodsInShoppingTrolley(Integer userId,Integer goodsId){
		
		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> changeGoodsAmountInShoppingTrolley(Integer userId,Integer goodsId,Integer amount){
		
		//TODO
		
		return null;
	}
	
	@Override
	public Map<String,Object> getGoodsInShoppingTrolley(Integer userId,Integer page){
		
		//TODO
		
		return null;
	}
	
}
