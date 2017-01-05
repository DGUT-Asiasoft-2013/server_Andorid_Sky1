package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.OrderLists;
import com.cloudage.membercenter.entity.User;
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
		PageRequest pageRequest = new PageRequest(page, 25, sort);
		return iOrderListRepository.findAllbyUser(userId,pageRequest);
	}


	//	//add
	//	@Override
	//	public void addOrders(User user, Book book) {
	//		OrderLists.orders_Key key=new orders_Key();               //获得对象
	//		key.setBook(book);
	//		key.setUser(user);
	//		
	//		
	//		OrderLists orders=new OrderLists();
	//		//设置id
	//		orders.setId(key);
	//		iOrderListRepository.save(orders); 
	//		
	//	}



	@Override
	public OrderLists findOrdersByOrderNumb(String order_numb) {
		return iOrderListRepository.getOrderByOnumb(order_numb);
	}



	@Override
	public Page<OrderLists> getListsBuy(int userId, int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 25, sort);
		return iOrderListRepository.findAllbyUser(userId,pageRequest);
	}



	@Override
	public Page<OrderLists> getListsSale(int userId, int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 25, sort);
		return iOrderListRepository.findAllbyUserSale(userId,pageRequest);
	}


	@Override
	public boolean deleteOrderById(Integer orderId) {
		
			
		try {
			iOrderListRepository.delete(orderId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
