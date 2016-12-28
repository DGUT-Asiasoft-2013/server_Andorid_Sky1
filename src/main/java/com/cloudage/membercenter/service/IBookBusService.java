package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.Bookbus;
import com.cloudage.membercenter.entity.User;

public interface IBookBusService {


	void addBookbus(User user,Book book);             //加入购物车
	void removeBookFromBus(User user,Book book);            //移除购物车
	int CountBook(int book_id);                             //统计数量
	Page<Bookbus> findCurrentUserAllBookAddtoBookBus(int userId,int page);//找到用户的所有加入购物车的书
}