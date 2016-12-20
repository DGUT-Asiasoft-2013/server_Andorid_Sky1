package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class PrivateMessage extends BaseEntity{
	User privataeMessageReceiver;
	User privateMessageSender;
	Date createDate;
	String privateText;
	@ManyToOne(optional=false)
	public User getPrivataeMessageReceiver() {
		return privataeMessageReceiver;
	}
	@ManyToOne(optional=false)
	public User getPrivateMessageSender() {
		return privateMessageSender;
	}
	public void setPrivateMessageSender(User privateMessageSender) {
		this.privateMessageSender = privateMessageSender;
	}
	public void setPrivataeMessageReceiver(User privataeMessageReceiver) {
		this.privataeMessageReceiver = privataeMessageReceiver;
	}

	
	@Column(updatable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPrivateText() {
		return privateText;
	}
	public void setPrivateText(String privateText) {
		this.privateText = privateText;
	}


}