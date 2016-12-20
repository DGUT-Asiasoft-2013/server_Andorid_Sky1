package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import com.cloudage.membercenter.util.DateRecord;

@Entity
public class Book extends DateRecord{
	//�û�(����)
	User user;

	//ͼ��ID��DateRecord����
	//���а�������ʱ��ͱ༭ʱ�䣬������(����)ʱ��

	//ͼ��ISBN��
	String ISBN;
	//ͼ����ۼ۸�
	String Price;



	@ManyToOne(optional=false) //1���˳��۶౾�鼮��Ҳ���Զ������
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