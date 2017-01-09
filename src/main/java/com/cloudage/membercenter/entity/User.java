package com.cloudage.membercenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.cloudage.membercenter.util.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
@Entity
public class User extends BaseEntity{
	
	String account;               //鐠愶箑褰�
	String passwordHash;          //鐎靛棛鐖�
	String name;                  //閻€劍鍩涢崥锟�
	String avatar;                //婢舵潙鍎�
	String email;                 //闁喚顔�
	String phoneNumb;            //閻絻鐦介崣椋庣垳
	String qq;                   //qq
	float sumMoney;
	String payPassword;
	
	
	
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public float getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(float sumMoney) {
		this.sumMoney = sumMoney;
	}
	@Column(nullable=false)
	public String getPhoneNumb() {
		return phoneNumb;
	}
	public void setPhoneNumb(String phoneNumb) {
		this.phoneNumb = phoneNumb;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Column(nullable=false,unique=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(unique=true)
	public String getAccount() {
		return account;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public String getName() {
		return name;
	}
	public String getAvatar() {
		return avatar;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	@Column(nullable=false)
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	@Column(unique=true)
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}