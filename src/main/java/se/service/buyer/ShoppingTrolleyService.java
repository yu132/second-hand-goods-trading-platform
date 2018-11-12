package se.service.buyer;

import java.util.Map;

public interface ShoppingTrolleyService {

	Map<String, Object> addGoodsToShoppingTrolley(Integer userId, Integer goodsId, Integer amount);

	Map<String, Object> deleteGoodsInShoppingTrolley(Integer userId, Integer goodsId);

	Map<String, Object> changeGoodsAmountInShoppingTrolley(Integer userId, Integer goodsId, Integer amount);

	Map<String, Object> getGoodsInShoppingTrolley(Integer userId, Integer page);

}