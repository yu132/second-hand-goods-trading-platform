package se.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.enumDefine.executeState.ExecuteState;
import se.enumDefine.goodsState.GoodsState;
import se.enumDefine.reason.Reason;
import se.model.Goods;
import se.repositories.GoodsRepository;
import se.service.util.DateUtil;


@Service
public class SellerService {	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private DateUtil dateUtil;
	
	/**
	 * 每页货品的数量
	 */
	public final static int AMOUNT_OF_GOODS_EACH_PAGE=10;

	public Map<String,Object> addGoods(Integer userId,Goods goods){
		//TODO
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.USER_ID_IS_NULL);
			return result;
		}
		if(goods==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_IS_NULL);
			return result;
		}
		if(goods.getGoodsName()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_NAME_IS_NULL);
			return result;
		}
		if(goods.getGoodsName().length()>255) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_NAME_TOO_LONG);
			return result;
		}
		if(goods.getPrice()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PRICE_IS_NULL);
			return result;
		}
		if(goods.getPrice()<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.PRICE_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		if(goods.getAmount()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_IS_NULL);
			return result;
		}
		if(goods.getAmount()<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.AMOUNT_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		if(goods.getDescription().length()>255) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.DESCRIPTION_TOO_LONG);
			return result;
		}
		if(goods.getEmailRemind()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.EMAIL_REMIND_IS_NULL);
			return result;
		}
		Goods preGoods=goodsRepository.findBySellerIdAndGoodsName(userId,goods.getGoodsName());
		if(preGoods!=null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason", Reason.GOODS_EXIST);
			return result;
		}
		goods.setSellerId(userId);
		goods.setState(GoodsState.WAIT_CHECK.toString());
		goods.setCommitTime(dateUtil.getCurrentDate());
		goodsRepository.save(goods);
		result.put("State", ExecuteState.SUCCESS);
		return result;
	}
	
	public Map<String,Object> getMyGoods(Integer userId,int page){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> getMyGoodsPage(Integer userId){
		//TODO
		
		return null;
	}
	
	public Map<String,Object> changeGoods(Integer userId,Goods newGoods){
		//TODO
		
		return null;
	}
	
	/**
	 * 通过id或名称删除商品
	 * @param userId
	 * @param goodNeedDelete
	 * @return
	 */
	public Map<String,Object> deleteGoods(Integer userId,Integer goodsNeedDeleteId){
		//TODO
		
		return null;
	}
}
