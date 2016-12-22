package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Book;



@Repository
public interface IBookRepository extends PagingAndSortingRepository<Book, Integer>{

	@Query("from Book book where book.user.id = ?1")
	List<Book> findOne(String userId);
	
	@Query("from Book book where book.title = ?1")
	List<Book> findAllByBookTitle(Book book);
	
	@Query("from Book book where book.author = ?1")
	List<Book> findAllByAuthor(Book book);
	
	@Query("from Book book where book.title like %?1% or book.author like %?1% or book.isbn like %?1% or book.user.name like %?1% or book.tag like %?1% or book.publisher like %?1%")
	Page<Book> findTextByKeyword(String keyword,Pageable page);
	
	@Query("from Book book where book.user.id = ?1")
	List<Book> findAllByUser(int book_user_id);
}
