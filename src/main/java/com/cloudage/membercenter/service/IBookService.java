package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.User;


public interface IBookService {

	
	Book findOne(int id);
	
	List<Book> findAllByUser(int book_user_id);//通过发布者检索图书
	List<Book> findAllByBookTitle(Book book);//通过书名搜索
	List<Book> findAllByAuthor(Book book);//通过图书作者搜索
	Page<Book> getBooks(int page);//获取图书分页
	Book save(Book book);//保存图书信息
	Page<Book> findTextByKeyword(String keyword,int page);//通过关键字搜索图书
	Page<Book> getBooksByType(String tag,int page);//根据标签(类型)获取图书
	Page<Book> getBooksByKeywordAndType(String keyword,String tag,int page);//根据关键字在类型中搜索图书
}
