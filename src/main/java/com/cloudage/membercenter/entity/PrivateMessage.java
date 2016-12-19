package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class PrivateMessage extends BaseEntity{
	User privataeMessageReceiver;//私信接收者
	User privateMessageSender;//私信发送者
	Date createDate;//私信发送时间
	String privateText;//私信的文字内容
	
	public User getPrivataeMessageReceiver() {
		return privataeMessageReceiver;
	}
	public void setPrivataeMessageReceiver(User privataeMessageReceiver) {
		this.privataeMessageReceiver = privataeMessageReceiver;
	}
	public User getPrivateMessageSender() {
		return privateMessageSender;
	}
	public void setPrivateMessageSender(User privateMessageSender) {
		this.privateMessageSender = privateMessageSender;
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
