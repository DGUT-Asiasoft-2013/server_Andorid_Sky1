package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.Money;

public interface IMoneyService {

	Money save(Money money);

	float findSumMoney(String currentUser);

}
