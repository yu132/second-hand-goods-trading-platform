package se.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.model.Goods;
import se.model.UserInfo;
import se.repositories.GoodsRepository;

@Service
public class SellerService {
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private GoodsRepository goodssRepository;
	
	private UserInfo user;
	
	private Goods goods;

	public Map<String,Object> addGoods(Integer userId,Goods good){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> getMyGoods(Integer userId,int page){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> changeGoods(Integer userId,Goods newGood){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> deleteGoods(Integer userId,Goods goodNeedDelete){
		//TODO
		
		return null;
	}
}
