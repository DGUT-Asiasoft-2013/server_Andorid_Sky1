package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public OrderLists changeState(OrderLists orderList,String finish) {
		orderList.setFinish(finish);
		return iOrderListRepository.save(orderList);
	}


	@Override
	public Page<OrderLists> getLists(int userId,int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 5, sort);
		return iOrderListRepository.findAllbyUser(userId,pageRequest);
	}


}
