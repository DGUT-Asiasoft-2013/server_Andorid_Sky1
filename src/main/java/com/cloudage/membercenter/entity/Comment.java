package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cloudage.membercenter.util.DateRecord;

/**
 * 评论
 * @author Administrator
 *
 */
@Entity
public class Comment extends DateRecord{

	
	String content;       //��������
	User commentor;        //������
	
	Book book;          //��
	
	@ManyToOne(optional=false)
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(optional=false)
	public User getCommentor() {
		return commentor;
	}
	public void setCommentor(User commentor) {
		this.commentor = commentor;
	}
	
}