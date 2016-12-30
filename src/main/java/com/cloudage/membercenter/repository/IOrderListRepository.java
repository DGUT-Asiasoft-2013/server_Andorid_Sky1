package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.OrderLists;

@Repository
public interface IOrderListRepository extends PagingAndSortingRepository<OrderLists, Integer>{

	@Query("select count(*) from OrderLists orders where orders.user.id = ?1")
	int countOrdersNumber(int userid);
	
	@Query("from OrderLists orders where orders.user.id = ?1")
	Page<OrderLists> findAllbyUser(int userId,Pageable page);

	@Query("from OrderLists orders where orders.orderId = ?1")
	OrderLists getOrderByOnumb(String order_numb);

}
