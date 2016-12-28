package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.OrderLists;
import com.cloudage.membercenter.repository.IOrderListRepository;


@Component
@Service
@Transactional
public class DefaultOrderListService  implements IOrderListService{
	@Autowired
	IOrderListRepository iOrderListRepository;

	
	@Override
	public OrderLists save(OrderLists orderList) {
		
		return iOrderListRepository.save(orderList);
	}


	@Override
	public OrderLists changeState(OrderLists orderList) {
//		if(orderList.getFinish())
		return null;
	}


}
