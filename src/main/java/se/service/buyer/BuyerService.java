package se.service.buyer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.orderState.OrderState;
import se.enumDefine.wayOfPay.WayOfPay;

@Service
public class BuyerService implements BuyService,SearchService,ShoppingTrolleyService{

	@Autowired
	private BuyService buyService;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private ShoppingTrolleyService shoppingTrolleyService;
	
	@Override
	public Map<String, Object> addGoodsToShoppingTrolley(Integer userId, Integer goodsId, Integer amount) {
		// TODO Auto-generated method stub
		return shoppingTrolleyService.addGoodsToShoppingTrolley(userId, goodsId, amount);
	}

	@Override
	public Map<String, Object> deleteGoodsInShoppingTrolley(Integer userId, Integer goodsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> changeGoodsAmountInShoppingTrolley(Integer userId, Integer goodsId, Integer amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getGoodsInShoppingTrolley(Integer userId, Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getRecommendGoods(Integer page) {
		// TODO Auto-generated method stub
		return searchService.getRecommendGoods(page);
	}

	@Override
	public Map<String, Object> getGoodsByKeyWords(String[] keyWords, Integer page) {
		// TODO Auto-generated method stub
		return searchService.getGoodsByKeyWords(keyWords,page);
	}

	@Override
	public Map<String, Object> getGoodsInformation(Integer goodsId) {
		// TODO Auto-generated method stub
		return searchService.getGoodsInformation(goodsId);
	}

	@Override
	public Map<String, Object> buyGoodsFromGoodsId(Integer userId, Integer goodsId, Integer amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> buyGoodsFromShoppingTrolley(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> checkAndAddReceivingInformation(Integer userId, Integer orderId,
			Integer receivingInformationId, String remarks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> payOrder(Integer userId, Integer orderId, WayOfPay wayOfPay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getOrder(Integer userId, Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllOrder(Integer userId, Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllOrderByType(Integer userId, Integer page, OrderState orderState) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
	
}
