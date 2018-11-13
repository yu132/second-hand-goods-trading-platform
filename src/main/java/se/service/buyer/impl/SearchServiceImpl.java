package se.service.buyer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.goodsState.GoodsState;
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
		Long total = goodsRepository.countByState(GoodsState.PASS_CHECK.toString());
		
		long complete_pages = total/AMOUNT_OF_GOODS_EACH_PAGE;
		long Remainder = total%AMOUNT_OF_GOODS_EACH_PAGE;
		Long pages;
		if(Remainder>0){
			pages = complete_pages+1;
		}else{
			pages = complete_pages;
		}
		 
		Map<String,Object> result=new HashMap<>();
		
		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_PAGE_IS_NULL);
			return result;
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

		LinkedList<Goods> glist=new LinkedList<Goods>();
		
		Pageable pageable = new QPageRequest(page.intValue(), AMOUNT_OF_GOODS_EACH_PAGE);
        Page<Goods> goodsList = goodsRepository.findByStateOrderByCommitTimeDesc(GoodsState.PASS_CHECK.toString(), pageable);
        result.put("State", ExecuteState.SUCCESS);
        result.put("GoodsList", goodsList);
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
