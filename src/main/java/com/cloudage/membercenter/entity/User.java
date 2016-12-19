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

	String account;               //
	String passwordHash;          //
	String name;                  //
	String avatar;                //
	String email;                 //
	String phoneNumb;            //
	String qq;                   //qq

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
	@Column(nullable=false,unique=true)
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