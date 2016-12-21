package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	//���е��õ�account��Ҫ��User����Ķ����һ��
	@Query("from User u where u.account=?1")
	User findNum(String num);
	
	
	@Query("from User u where u.name=?1")
	User findOne(int name);
	
	//���е��õ�email��Ҫ��User����Ķ����һ��
	@Query("from User u where u.email=?1")
	User findEmail(String email);
}