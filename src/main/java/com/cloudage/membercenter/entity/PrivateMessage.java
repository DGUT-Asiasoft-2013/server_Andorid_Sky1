package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class PrivateMessage extends BaseEntity{
	String chatType;//消息的类型,"send"表示是发送的,"receive"表示是接收的
	User privataeMessageReceiver;//私信接收者
	User privateMessageSender;//私信发送者
	Date createDate;//私信发送时间
	String privateText;//私信的文字内容

	public String getChatType() {
		return chatType;
	}
	public void setChatType(String chatType) {
		this.chatType = chatType;
	}
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

	
	@PrePersist
	void onPrePersist(){
		createDate = new Date();
		
	}

}
