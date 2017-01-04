package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Money;

public interface IMoneyService {

	Money save(Money money);

	float findSumMoney(String currentUser);

	Money getList(Money money);

	List<Money> findMoneyByUser(int user_id);

	Page<Money> getLists(String cuser, int page);

}
