package com.cloudage.membercenter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Money;

@Repository
public interface IMoneyRepository extends PagingAndSortingRepository<Money, Integer>{

	@Query ("from Money m where m.currentUser=?1")
	float findSumMoney(String currentUser);

}
