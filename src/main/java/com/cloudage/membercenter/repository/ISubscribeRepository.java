package com.cloudage.membercenter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Subscribe;

@Repository
public interface ISubscribeRepository extends PagingAndSortingRepository<Subscribe, Subscribe.Key>{
	@Query("select count(*) from Subscribe subscribe where subscribe.id.saler.name = ?1")
	int subscribeCountsOfSaler(int salerName);
	
	@Query("select count(*) from Subscribe subscribe where subscribe.id.Me.Name = ?1 and subscribe.id.Saler.Name = ?2")
	int checkSubscribeExsists(int meName, int salerName);
}
