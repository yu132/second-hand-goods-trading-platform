package se.service.seller;

import java.util.HashMap;
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
import se.service.util.DateUtil;


@Service
public class SellerService {	
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
	 * 添加商品
	 * @param userId
	 * @param goods
	 * @return
	 */
	public Map<String,Object> addGoods(Integer userId,Goods goods){
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
		goods.setState(GoodsState.PASS_CHECK.toString());
		goods.setCommitTime(dateUtil.getCurrentDate());
		goodsRepository.save(goods);
		result.put("State", ExecuteState.SUCCESS);
		return result;
	}
	
	/**
	 * 根据卖家id得到卖家所有商品，分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Map<String,Object> getMyGoods(Integer userId,Integer page){
		Map<String,Object> result=new HashMap<>();
		Map<String,Object> getMyGoodsPageResult=new HashMap<>();
		if(page==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_PAGE_IS_NULL);
			return result;
		}
		if(page<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_PAGE_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		getMyGoodsPageResult=getMyGoodsPage(userId);
		Reason reason=(Reason) getMyGoodsPageResult.get("Reason");
		if(reason!=null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",reason);
			return result;
		}
		Long pageNum=(Long)getMyGoodsPageResult.get("PageNum");
		if(page>pageNum) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_PAGE_OUT_OF_BOUNDS);
			return result;
		}else {
	        Pageable pageable = new QPageRequest(page.intValue(), AMOUNT_OF_GOODS_EACH_PAGE);
	        Page<Goods> goodsList = goodsRepository.findBySellerIdOrderByCommitTimeDesc(userId, pageable);
	        result.put("State", ExecuteState.SUCCESS);
	        result.put("GoodsList", goodsList);
			return result;
		}		
	}
	/**
	 * 根据卖家id得到显示该卖家所有商品的页数
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getMyGoodsPage(Integer userId){
		Map<String,Object> result=new HashMap<>();
		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_ID_IS_NULL);
			return result;
		}
		if(userInfoRepository.getOne(userId)==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USERNAME_NOT_EXIST);
			return result;
		}else {
			long goodsNum=goodsRepository.countBySellerId(userId);
			Long pageNum=goodsNum/AMOUNT_OF_GOODS_EACH_PAGE;
			if(pageNum%AMOUNT_OF_GOODS_EACH_PAGE!=0)
				pageNum++;
			result.put("State", ExecuteState.SUCCESS);
			result.put("PageNum", pageNum);
			return result;
		}
	}
	/**
	 * 修改商品信息
	 * @param userId
	 * @param newGoods
	 * @return
	 */
	public Map<String,Object> changeGoods(Integer userId,Goods newGoods){
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_ID_IS_NULL);
			return result;
		}
		if(newGoods==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_IS_NULL);
			return result;
		}else if(newGoods.getId()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_ID_IS_NULL);
			return result;
		}
		Goods goods=goodsRepository.getOne(newGoods.getId());
		if(goods==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_NOT_EXIST);
			return result;
		}
		if(goods.getSellerId()!=userId) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_AUTH_ILLEGAL);
			return result;
		}
		Goods goods2=goodsRepository.findBySellerIdAndGoodsName(userId, newGoods.getGoodsName());
		if(goods2!=null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_EXIST);
			return result;
		}
		if(newGoods.getGoodsName()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_NAME_IS_NULL);
			return result;
		}else if(newGoods.getGoodsName().length()>255) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_NAME_TOO_LONG);
			return result;
		}
		if(newGoods.getPrice()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.PRICE_IS_NULL);
			return result;
		}else if(newGoods.getPrice()<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.PRICE_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		if(newGoods.getAmount()==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.AMOUNT_IS_NULL);
			return result;
		}else if(newGoods.getAmount()<=0) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.AMOUNT_IS_NEGATIVE_OR_ZERO);
			return result;
		}
		if(newGoods.getDescription().length()>255) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.DESCRIPTION_TOO_LONG);
			return result;
		}else {
			goods.setCommitTime(dateUtil.getCurrentDate());
			goods.setAmount(newGoods.getAmount());
			goods.setDescription(newGoods.getDescription());
			goods.setGoodsName(newGoods.getGoodsName());
			goods.setPrice(newGoods.getPrice());
			goodsRepository.saveAndFlush(goods);
			
			result.put("State", ExecuteState.SUCCESS);
			return result;
		}
	}
	
	/**
	 * 通过id或名称删除商品
	 * @param userId
	 * @param goodNeedDelete
	 * @return
	 */
	public Map<String,Object> deleteGoods(Integer userId,Integer goodsNeedDeleteId){
		Map<String,Object> result=new HashMap<>();

		if(userId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_ID_IS_NULL);
			return result;
		}
		if(goodsNeedDeleteId==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.GOODS_ID_IS_NULL);
			return result;
		}
		Goods goodsNeedDelete=goodsRepository.getOne(goodsNeedDeleteId);
		if(goodsNeedDelete==null) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",null);
			return result;
		}
		if(goodsNeedDelete.getSellerId()!=userId) {
			result.put("State", ExecuteState.ERROR);
			result.put("Reason",Reason.USER_AUTH_ILLEGAL);
			return result;
		}else {
			goodsRepository.deleteById(goodsNeedDeleteId);
			result.put("State", ExecuteState.SUCCESS);
			return result;
		}
		
		
	}
}
