package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Money;

@Repository
public interface IMoneyRepository extends PagingAndSortingRepository<Money, Integer>{

	@Query ("from Money m where m.currentUser=?1")
	float findSumMoney(String currentUser);

	@Query("from Money money where money.currentUser = ?1")
	List<Money> findMoneyByUser(int user_id);

	@Query("from Money m where m.currentUser = ?1")
	Page<Money> findAllbyUser(String cuser,Pageable page);

}
