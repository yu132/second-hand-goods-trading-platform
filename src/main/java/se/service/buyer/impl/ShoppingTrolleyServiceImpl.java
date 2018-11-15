package se.service.buyer.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.model.ShoppingTrolley;
import se.repositories.GoodsRepository;
import se.repositories.ShoppingTrolleyRepository;
import se.repositories.UserInfoRepository;
import se.service.buyer.ShoppingTrolleyService;
import se.service.util.DateUtil;

@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private ShoppingTrolleyRepository shoppingTrolleyRepository;
	
	@Override
	public Map<String,Object> addGoodsToShoppingTrolley(Integer userId,Integer goodsId,Integer amount){
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
		}
		if(amount<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		
		Optional<Goods> g= goodsRepository.findById(goodsId);
		
		if(!g.isPresent()){
			result.put("State", ExecuteState.ERROR);
	        result.put("Reason",Reason.GOODS_NOT_EXIST);
	        return result;
		}
		Goods goods = g.get();
		if(goods.getSellerId()==userId){
			result.put("State", ExecuteState.ERROR);
	        result.put("Reason",Reason.ILLEGAL_OPERATION_TO_OWN_GOODS);
	        return result;
		}
		if(amount>goods.getAmount()) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_EXCESSIVE);
			return result;
		}
	
		ShoppingTrolley s = new ShoppingTrolley();
		s.setAmount(amount);
		s.setGoodsId(goodsId);
		s.setUserId(userId);
		s.setAddTime(dateUtil.getCurrentDate());
		
		result.put("State", ExecuteState.SUCCESS);
		return result;
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
