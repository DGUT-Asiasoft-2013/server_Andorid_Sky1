package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.User;

@Repository
public interface ISubscribeRepository extends PagingAndSortingRepository<Subscribe, Subscribe.Key>{
//	搜索卖家被订阅过的次数
	@Query("select count(*) from Subscribe subscribe where subscribe.id.saler.id = ?1")
	int subscribeCountsOfSaler(int salerName);
//	搜索所有用户订阅过的卖家
	@Query("select from Subscribe subscribe where subscribe.id.me.id = ?1")
	List<User> findAllByMe(int me);
//	搜索买家是否订阅过该卖家
	@Query("select count(*) from Subscribe subscribe where subscribe.id.Me.Name = ?1 and subscribe.id.Saler.Name = ?2")
	int checkSubscribeExsists(int meName, int salerName);
}
