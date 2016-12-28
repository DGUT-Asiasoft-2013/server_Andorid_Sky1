package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.OrderLists;
import com.cloudage.membercenter.entity.User;

public interface IOrderListService {

	OrderLists save(OrderLists orderList);//保存订单信息
	OrderLists changeState(OrderLists orderList, String finish);//改变订单状态
	Page<OrderLists> getLists(int userId, int page);
	public void addOrders(User user,Book book);
//	public void removeOrders(User user, Book book);
}
