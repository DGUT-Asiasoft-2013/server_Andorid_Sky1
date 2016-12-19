package com.cloudage.membercenter.entity;

import javax.persistence.Entity;

import com.cloudage.membercenter.util.DateRecord;

/**
 * 书类
 * @author Administrator
 *
 */
@Entity
public class Book extends DateRecord{

	//用户（卖家）
	User user;
	
	//图书的ISBN码
	String ISBN;
	
	//价格
	String Price;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}
	
}
