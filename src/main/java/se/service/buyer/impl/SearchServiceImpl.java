package se.service.buyer.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import se.service.buyer.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	/**
	 * 主页推荐
	 * @param page
	 * @return
	 */
	@Override
	public Map<String,Object> getRecommendGoods(Integer page){
		
		//TODO
		
		return null;
	}
	
	/**
	 * 搜索商品
	 * @param keyWords
	 * @param page
	 * @return
	 */
	@Override
	public Map<String,Object> getGoodsByKeyWords(String[] keyWords,Integer page){
		
		//TODO
		
		return null;
	}
	
	/**
	 * 点击商品，查看详细信息
	 * @return
	 */
	@Override
	public Map<String,Object> getGoodsInformation(Integer goodsId){
		
		//TODO
		
		return null;
	}
	
}
