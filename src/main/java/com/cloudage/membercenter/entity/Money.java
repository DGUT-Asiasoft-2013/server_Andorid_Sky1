package com.cloudage.membercenter.entity;

import javax.persistence.Entity;

import com.cloudage.membercenter.util.DateRecord;

@Entity
public class Money extends DateRecord{

	String currentUser;
	float recharge;
	float sumMoney;

	
	public float getRecharge() {
		return recharge;
	}

	public void setRecharge(float recharge) {
		this.recharge = recharge;
	}

	public float getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(float sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}


	
}
