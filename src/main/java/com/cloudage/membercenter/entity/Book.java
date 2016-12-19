package com.cloudage.membercenter.entity;

import javax.persistence.Entity;

import com.cloudage.membercenter.util.DateRecord;

/**
 * ����
 * @author Administrator
 *
 */
@Entity
public class Book extends DateRecord{

	//�û������ң�
	User user;
	
	//ͼ���ISBN��
	String ISBN;
	
	//�۸�
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
