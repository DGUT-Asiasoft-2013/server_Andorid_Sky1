package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Bookbus;

@Repository
public interface IBookBusRepository extends PagingAndSortingRepository<Bookbus, Bookbus.Bus_Key>{

	//count book'number to bookbus,bookbus dont' hava book,its belong to Bus_key
	@Query("select count(*) from Bookbus bookbus where bookbus.id.user.id = ?1")
	int countBookNumber(int userid);
	
	//find the currentuser'all book which add to the bookbus
	@Query("from Bookbus bookbus where bookbus.id.user.id = ?1")
	Page<Bookbus> findCurrentUserAllBookAddtoBookBus(int userId,Pageable page);//找到用户的所有加入购物车的书
}
