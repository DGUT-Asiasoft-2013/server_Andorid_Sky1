package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Money;
import com.cloudage.membercenter.repository.IMoneyRepository;

@Component
@Service
@Transactional
public class DefaultMoneyService implements IMoneyService{

	@Autowired
	IMoneyRepository iMoneyRepository;
	@Override
	public Money save(Money money) {
		// TODO Auto-generated method stub
		return iMoneyRepository.save(money);
	}
	@Override
	public float findSumMoney(String currentUser) {
		// TODO Auto-generated method stub
		return iMoneyRepository.findSumMoney(currentUser);
	}

}
