package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	//此中调用的account需要跟User类里的定义的一样
	@Query("from User u where u.account=?1")
	User findNum(String num);
	
	//此中调用的email需要跟User类里的定义的一样
	@Query("from User u where u.email=?1")
	User findEmail(String email);
}
