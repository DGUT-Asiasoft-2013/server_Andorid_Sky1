package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IBookRepository;

//书的实现
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

	@Override
	public List<Book> findAllByBookTitle(Book book) {
		return iBookRepository.findAllByBookTitle(book);
	}

	@Override
	public List<Book> findAllByAuthor(Book book) {
		return iBookRepository.findAllByAuthor(book);
	}

	@Override
	public Book save(Book book) {
		return iBookRepository.save(book);
	}

	@Override
	public Page<Book> getBooks(int page){
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return iBookRepository.findAll(pageRequest);
	}

	@Override
	public Page<Book> findTextByKeyword(String keyword, int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return iBookRepository.findTextByKeyword(keyword,pageRequest);
	}

	@Override
	public List<Book> findAllByUser(int book_user_id) {
		return iBookRepository.findAllByUser(book_user_id);
	}

	@Override
	public Page<Book> getBooksByType(String tag, int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return iBookRepository.getBooksByType(tag,pageRequest);
	}

	@Override
	public void updateSubscribeB(int book_saler_id) {
		if(iBookRepository.find(book_saler_id)>0)
		iBookRepository.updateSubscribe(book_saler_id);
	}

	
	public Page<Book> getBooksByKeywordAndType(String keyword,String tag,int page){
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return iBookRepository.getBooksByKeywordAndType(keyword,tag,pageRequest);
	}
	
	
}
