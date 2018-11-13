package se.service.buyer;

import java.util.Map;

public interface SearchService {
	
	public final static Integer AMOUNT_OF_GOODS_EACH_PAGE=15;

	/**
	 * 主页推荐
	 * @param page
	 * @return
	 */
	Map<String, Object> getRecommendGoods(Integer page);

	/**
	 * 搜索商品
	 * @param keyWords
	 * @param page
	 * @return
	 */
	Map<String, Object> getGoodsByKeyWords(String[] keyWords, Integer page);

	/**
	 * 点击商品，查看详细信息
	 * @return
	 */
	Map<String, Object> getGoodsInformation(Integer goodsId);

}