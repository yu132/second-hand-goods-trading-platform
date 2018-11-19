package se.service.buyer.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.goodsState.GoodsState;
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
		if(!shoppingTrolleyRepository.existsByUserIdByGoodsId(userId,goodsId)){
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_IS_NULL);
			return result;
		}
		shoppingTrolleyRepository.deleteByUserIdByGoodsId(userId, goodsId);
		result.put("State", ExecuteState.SUCCESS);
		return null;
	}
	
	@Override
	public Map<String,Object> changeGoodsAmountInShoppingTrolley(Integer userId,Integer goodsId,Integer amount){
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
		Optional<ShoppingTrolley> g= shoppingTrolleyRepository.findByUserIdByGoodsId(userId, goodsId);
		
		if(!g.isPresent()){
			result.put("State", ExecuteState.ERROR);
	        result.put("Reason",Reason.GOODS_NOT_EXIST);
	        return result;
		}
		ShoppingTrolley s=g.get();
		s.setAmount(amount);
		shoppingTrolleyRepository.save(s);
		result.put("State", ExecuteState.SUCCESS);
		return null;
		
		
	}
	
	@Override
	public Map<String,Object> getGoodsInShoppingTrolley(Integer userId,Integer page){
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_PAGE_IS_NULL);
			return result;
		}
		
		Long total = shoppingTrolleyRepository.countByUserId(userId);

		long complete_pages = total/AMOUNT_OF_TROLLEY_EACH_PAGE;
		long Remainder = total%AMOUNT_OF_TROLLEY_EACH_PAGE;
		Long pages;
		if(Remainder>0){
			pages = complete_pages+1;
		}else{
			pages = complete_pages;
		}
		
		if(page>pages) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_PAGE_OUT_OF_BOUNDS);
			return result;
		}
		
		if(page<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_PAGE_IS_NEGATIVE_OR_ZERO);
			result.put("GoodsList", null);
			return result;
		}
		
		LinkedList<ShoppingTrolley> glist=new LinkedList<ShoppingTrolley>();
		
		Pageable pageable = new PageRequest(page.intValue()-1, AMOUNT_OF_TROLLEY_EACH_PAGE.intValue());
        Page<ShoppingTrolley> trolleyList = shoppingTrolleyRepository.findByUserIdByAddTimeDesc(userId, pageable);

        result.put("State", ExecuteState.SUCCESS);
        result.put("TrolleyList", trolleyList.getContent());
		return result;

	}
	
}
