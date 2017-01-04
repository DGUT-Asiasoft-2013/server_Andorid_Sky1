package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Money;
import com.cloudage.membercenter.entity.OrderLists;
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
	@Override
	public Money getList(Money money) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Money> findMoneyByUser(int  user_id) {
		// TODO Auto-generated method stub
		return iMoneyRepository.findMoneyByUser(user_id);
	}
	@Override
	public Page<Money> getLists(String cuser, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 25, sort);
		return iMoneyRepository.findAllbyUser(cuser, pageRequest);
	}

}
