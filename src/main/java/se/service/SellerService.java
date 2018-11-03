package se.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import se.model.Goods;

@Service
public class SellerService {

	public Map<String,Object> addGood(Integer userId,Goods good){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> getMyGoods(Integer userId,int page){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> changeGood(Integer userId,Goods newGood){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> deleteGood(Integer userId,Goods goodNeedDelete){
		//TODO
		
		return null;
	}
}
