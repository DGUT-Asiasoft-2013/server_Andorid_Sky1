package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
@Component
@Service
@Transactional
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	//���е��õ�account��Ҫ��User����Ķ����һ��
	@Query("from User u where u.account=?1")
	User findNum(String num);
	
	
	@Query("from User u where u.id=?1")
	User findOne(int user_id);
	
	//���е��õ�email��Ҫ��User����Ķ����һ��
	@Query("from User u where u.email=?1")
	User findEmail(String email);

	@Query("from User u where u.account=?1")
	User findSum(float sumMoney);


}