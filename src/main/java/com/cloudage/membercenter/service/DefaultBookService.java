package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.repository.IBookRepository;

/*
 *  实现类
 */
@Component
@Service
@Transactional
public class DefaultBookService implements IBookService{

	@Autowired
	IBookRepository iBookRepository;
	
	
	@Override
	public Book findOne(int id) {
		return iBookRepository.findOne(id);
	}

}
