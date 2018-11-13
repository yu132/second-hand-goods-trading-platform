package se.service.buyer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.repositories.GoodsRepository;
import se.repositories.UserInfoRepository;
import se.service.buyer.SearchService;
import se.service.util.DateUtil;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	/**
	 * 每页货品的数量
	 */
	public final static int AMOUNT_OF_GOODS_EACH_PAGE=10;
	/**
	 * 主页推荐
	 * @param page
	 * @return
	 */
	@Override
	public Map<String,Object> getRecommendGoods(Integer page){
		
		//TODO
		Long total = goodsRepository.count();
		long complete_pages = total/AMOUNT_OF_GOODS_EACH_PAGE;
		long Remainder = total%AMOUNT_OF_GOODS_EACH_PAGE;
		
		Map<String,Object> result=new HashMap<>();
		
		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_PAGE_IS_NULL);
			return result;
		}
		
		if(page>complete_pages+1) {
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

		LinkedList<Goods> glist=new LinkedList<Goods>();
		
		if(page==complete_pages+1){
			for(long i=complete_pages*AMOUNT_OF_GOODS_EACH_PAGE;i<total;i++){
				//获取当页的goods 首先判断状态？
				glist.add(goodsRepository.)
			}
			
		}else{
			for(int i =(page-1)*AMOUNT_OF_GOODS_EACH_PAGE;i<AMOUNT_OF_GOODS_EACH_PAGE;i++){
				
			}
		}
		return result;
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
