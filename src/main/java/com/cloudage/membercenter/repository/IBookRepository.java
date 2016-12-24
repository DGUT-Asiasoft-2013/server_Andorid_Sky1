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
	
	//通过发布者检索图书
	@Query("from Book book where book.user.id = ?1")
	List<Book> findOne(String userId);
	
	//通过书名搜索
	@Query("from Book book where book.title = ?1")
	List<Book> findAllByBookTitle(Book book);
	
	//通过图书作者搜索
	@Query("from Book book where book.author = ?1")
	List<Book> findAllByAuthor(Book book);
	
	//通过关键字搜索图书
	@Query("from Book book where book.title like %?1% or book.author like %?1% or book.isbn like %?1% or book.user.name like %?1% or book.tag like %?1% or book.publisher like %?1%")
	Page<Book> findTextByKeyword(String keyword,Pageable page);
	
	@Query("from Book book where book.user.id = ?1")
	List<Book> findAllByUser(int book_user_id);
	
	//根据标签(类型)获取图书
	@Query("from Book book where book.tag like %?1%")
	Page<Book> getBooksByType(String tag,Pageable page);
}
