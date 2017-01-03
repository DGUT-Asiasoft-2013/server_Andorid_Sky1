package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.User;

@Repository
public interface ISubscribeRepository extends PagingAndSortingRepository<Subscribe, Subscribe.Key>{
//	搜索卖家被订阅过的次数
	@Query("select count(*) from Subscribe subscribe where subscribe.id.saler.id = ?1")
	int subscribeCountsOfSaler(int salerId);
//	搜索所有用户订阅过的卖家
	@Query("from Subscribe subscribe where subscribe.id.me.id = ?1")
	List<Subscribe> findAllByMe(int meId);
//	搜索买家是否订阅过该卖家
	@Query("select count(*) from Subscribe subscribe where subscribe.id.me.id = ?1 and subscribe.id.saler.id = ?2")
	int checkSubscribeExsists(int meId, int salerId);
//	通过卖家id寻找卖家
	@Query("from Subscribe subscribe where subscribe.id.saler.id = ?1")
	Subscribe findById(int saler_id);
	@Modifying
	@Query("update Subscribe subscribe set subscribe.b =?3  where subscribe.id.me.id=?1 and subscribe.id.saler.id=?2")
	void changeB(int user_id, int saler_id, boolean b);
	
	@Query("select sum(subscribe.count) from Subscribe subscribe where subscribe.id.me.id = ?1 and subscribe.b = true")
	int getCount(int user_id);
	
	@Query("select count(*) from Subscribe subscribe where subscribe.id.me.id = ?1 and subscribe.b = true")
	int isExistence(int user_id);
	@Modifying
	@Query("update Subscribe subscribe set subscribe.count=0 where subscribe.id.me.id=?1 and subscribe.id.saler.id=?2")
	void setCountZero(int user_id, int saler_id);
	
	@Query("select subscribe.count from Subscribe subscribe where subscribe.id.me.id=?1 and subscribe.id.saler.id=?2 and subscribe.b=true")
	int getCountWithSalerId(int user_id, int saler_id);
}
