package com.cloudage.membercenter.entity;

import java.io.Serializable;
import java.util.Date;

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

	//	@Embeddable
	//	public static class orders_Key implements Serializable {
	//		//用户
	//		User user;
	//		Book book;              //书
	//
	//		@ManyToOne(optional=false)
	//		public User getUser() {
	//			return user;
	//		}
	//		public void setUser(User user) {
	//			this.user = user;
	//		}
	//
	//		@ManyToOne(optional=false)
	//		public Book getBook() {
	//			return book;
	//		}
	//		public void setBook(Book book) {
	//			this.book = book;
	//		}
	//
	//		@Override
	//		public boolean equals(Object obj) {
	//			if(obj instanceof orders_Key){
	//				orders_Key other = (orders_Key)obj;
	//				return book.getId() == other.book.getId() && user.getId() == other.user.getId();
	//			}else{
	//				return false;
	//			}
	//		}
	//
	//		@Override
	//		public int hashCode() {
	//			return book.getId();
	//		}

	//}

	//	orders_Key id;



	String orderId;//订单号

	//用户
	User user;
	Book book;              //书
	//	int booksAdded; //添加到购物车的书的数量
	String payMoney;//交易金额
	String payway;//交易方式
	String finish;//交易状态(已/未提交->是否缺货->已/未结算'已提交'->待处理->处理中->已发货->已完成/已取消/送货失败)
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
	//	public int getBooksAdded() {
	//		return booksAdded;
	//	}
	//	public void setBooksAdded(int booksAdded) {
	//		this.booksAdded = booksAdded;
	//	}



	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
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
	
	
	@Column(unique = true)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
