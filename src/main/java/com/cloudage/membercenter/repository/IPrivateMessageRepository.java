package com.cloudage.membercenter.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.PrivateMessage;
@Repository
public interface IPrivateMessageRepository extends PagingAndSortingRepository<PrivateMessage, Integer>{

	
//@Query("")

}
