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
	
	String account;               //账号
	String passwordHash;          //密码
	String name;                  //用户名
	String avatar;                //头像
	String email;                 //邮箱
	String phoneNumb;            //电话号码
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