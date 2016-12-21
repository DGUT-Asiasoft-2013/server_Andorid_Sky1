package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Book;

//PagingAndSortingRepository�߱��˷�ҳ��Ч��

public interface IBookRepository extends PagingAndSortingRepository<Book, Integer>{

	@Query("from Book book where book.user.id = ?1")
	List<Book> findOne(String userId);
}
