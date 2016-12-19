package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Book {
	//用户(卖家)
	User user;

	//图书ID在DateRecord里面
	//其中包含创建时间和编辑时间，即出售(发布)时间

	//图书ISBN码
	String ISBN;
	//图书出售价格
	String Price;



	@ManyToOne(optional=false) //1个人出售多本书籍，也可以多个人买
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
