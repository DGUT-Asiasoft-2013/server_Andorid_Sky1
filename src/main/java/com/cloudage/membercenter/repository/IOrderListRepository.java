package com.cloudage.membercenter.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Bookbus;
import com.cloudage.membercenter.entity.OrderLists;

@Repository
public interface IOrderListRepository extends PagingAndSortingRepository<OrderLists, Integer>{

}
