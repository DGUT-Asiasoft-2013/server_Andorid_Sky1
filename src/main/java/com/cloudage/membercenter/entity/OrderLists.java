package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import com.cloudage.membercenter.util.BaseEntity;


@Entity
public class OrderLists extends BaseEntity{

	String orderId;//订单号

	Bookbus bookbus;//购物车
	int booksAdded; //添加到购物车的书的数量
	float payMoney;//交易金额
	String payway;//交易方式
	String finish;//交易状态(已/未提交->是否缺货->已/未结算'已提交'->待处理->处理中->已发货->已完成/已取消/送货失败)
	Date createDate;
	
	
	
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	public int getBooksAdded() {
		return booksAdded;
	}
	public void setBooksAdded(int booksAdded) {
		this.booksAdded = booksAdded;
	}
	@EmbeddedId
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public Bookbus getBookbus() {
		return bookbus;
	}
	public void setBookbus(Bookbus bookbus) {
		this.bookbus = bookbus;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	
	@Column(updatable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@PrePersist
	void onPrePersist()
	{
		createDate=new Date();
	}
	
	
}
