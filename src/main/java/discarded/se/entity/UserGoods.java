package discarded.se.entity;

import java.util.Date;

public class UserGoods {
	
	private int tradeNumber;
	private User seller;
	private User buyer;
	private Goods goods;
	private Date tradeTime;
	private int numberOfGoods;
	private double tradePrice;
	
	public int getTradeNumber() {
		return tradeNumber;
	}
	public void setTradeNumber(int tradeNumber) {
		this.tradeNumber = tradeNumber;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public int getNumberOfGoods() {
		return numberOfGoods;
	}
	public void setNumberOfGoods(int numberOfGoods) {
		this.numberOfGoods = numberOfGoods;
	}
	public double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}
	
	
}
