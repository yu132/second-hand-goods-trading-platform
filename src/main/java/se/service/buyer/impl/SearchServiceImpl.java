package se.service.buyer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		
		Pageable pageable = new PageRequest(page.intValue()-1, AMOUNT_OF_GOODS_EACH_PAGE.intValue());
        Page<Goods> goodsList = goodsRepository.findByStateOrderByCommitTimeDesc(GoodsState.PASS_CHECK.toString(), pageable);
        System.out.println();
        System.out.println(goodsList.getContent().size());
        result.put("State", ExecuteState.SUCCESS);
        result.put("GoodsList", goodsList.getContent());
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
		Map<String,Object> result=new HashMap<>();
		if(keyWords==null){
			result.put("State", getRecommendGoods(page).get("State"));
	        result.put("GoodsList",getRecommendGoods(page).get("GoodsList"));
	        return result;
		}
		
		LinkedList<String> kws=new LinkedList<String>();
		for(String i : keyWords){
			if(i!=null&&!i.equals("")){
				kws.add(i);
			}
		}
		return result;
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
