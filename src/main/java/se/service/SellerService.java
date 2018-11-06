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
	private GoodsRepository goodsRepository;
	
	private UserInfo user;
	
	private Goods goods;
	
	/**
	 * 每页货品的数量
	 */
	public final static int AMOUNT_OF_GOODS_EACH_PAGE=10;

	public Map<String,Object> addGoods(Integer userId,Goods good){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> getMyGoods(Integer userId,int page){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> getMyGoodsPage(Integer userId){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> changeGoods(Integer userId,Goods newGood){
		//TODO
		
		return null;
	}
	
	/**
	 * 通过id或名称删除商品
	 * @param userId
	 * @param goodNeedDelete
	 * @return
	 */
	public Map<String,Object> deleteGoods(Integer userId,Goods goodNeedDelete){
		//TODO
		
		return null;
	}
}
