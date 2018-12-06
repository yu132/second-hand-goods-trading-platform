package se.service.buyer;

import java.util.Map;

import se.enumDefine.orderState.OrderState;
import se.enumDefine.wayOfPay.WayOfPay;

public interface BuyService {

	Map<String, Object> buyGoodsFromGoodsId(Integer userId, Integer goodsId, Integer amount);

	Map<String, Object> buyGoodsFromShoppingTrolley(Integer userId);

	Map<String, Object> checkAndAddReceivingInformation(Integer userId,Integer orderId, Integer receivingInformationId,String remarks);

	Map<String, Object> payOrder(Integer userId, Integer orderId,WayOfPay wayOfPay);

	Map<String, Object> getOrder(Integer userId, Integer orderId);

	Map<String, Object> getAllOrder(Integer userId, Integer page);

	Map<String, Object> getAllOrderByType(Integer userId, Integer page, OrderState orderState);

}