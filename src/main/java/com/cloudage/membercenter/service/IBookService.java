package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Book;

public interface IBookService {
	Book findOne(int id);
	
	List<Book> findAllByBookTitle(Book book);//通过书名搜索
	List<Book> findAllByAuthor(Book book);//通过图书作者搜索
	Page<Book> getBooks(int page);//分页
	Book save(Book book);//保存图书信息
	Page<Book> findTextByKeyword(String keyword,int page);
}
