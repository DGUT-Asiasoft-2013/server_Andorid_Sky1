package com.cloudage.membercenter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.UniqueConstraint;

import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class OrderLists extends BaseEntity{

	String orderId;//订单号

	//用户
	User user;
	Book book;              //书
	//	int booksAdded; //添加到购物车的书的数量
	String payMoney;//交易金额
	int payway;   //交易方式:0为在线交易，1为私下交易
	int finish;   //交易状态:0为已提交未付款；1为已付款；2为处理中；3为已发货；4为订单已取消；5为订单已完成
	
	Date createDate;



	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(optional=false)
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}


	public int getPayway() {
		return payway;
	}
	public void setPayway(int payway) {
		this.payway = payway;
	}
	public int getFinish() {
		return finish;
	}
	public void setFinish(int finish) {
		this.finish = finish;
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
	
	
	@Column(unique = false)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
