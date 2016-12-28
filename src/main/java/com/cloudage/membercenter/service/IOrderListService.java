package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.OrderLists;

public interface IOrderListService {

	OrderLists save(OrderLists orderList);//保存订单信息
	OrderLists changeState(OrderLists orderList);//改变订单状态
}
