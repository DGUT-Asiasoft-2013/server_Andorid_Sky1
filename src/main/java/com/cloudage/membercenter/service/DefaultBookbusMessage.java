package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.Bookbus;
import com.cloudage.membercenter.entity.Bookbus.Bus_Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IBookBusRepository;

/**
 * 购物车的类
 * @author Administrator
 *
 */
@Component
@Service
@Transactional
public class DefaultBookbusMessage implements IBookBusService{

	@Autowired
	IBookBusRepository iBookBusRepository;
	
	
	//add book to bus
	@Override
	public void addBookbus(User user, Book book) {
		Bookbus.Bus_Key key=new Bus_Key();               //获得对象
		key.setBook(book);
		key.setUser(user);
		
		Bookbus bookbus=new Bookbus();
		//设置id
		bookbus.setId(key);
		iBookBusRepository.save(bookbus);          //保存购物车
	}


	//remove book From Bus
	@Override
	public void removeBookFromBus(User user, Book book) {
		Bookbus.Bus_Key key=new Bus_Key();
		key.setBook(book);
		key.setUser(user);
		
		iBookBusRepository.delete(key);           //remove book from bus
	}


	//count book'number
	@Override
	public int CountBook(int book_id) {
		return iBookBusRepository.countBookNumber(book_id);
	}

	//find the currentuser'all book which add to the bookbus
	@Override
	public Page<Bookbus> findCurrentUserAllBookAddtoBookBus(int userId, int page) {
		//sort
		Sort sort=new Sort(Direction.DESC, "createDate");
		PageRequest request=new PageRequest(page, 50, sort);
		return iBookBusRepository.findCurrentUserAllBookAddtoBookBus(userId, request);
	}

}
