package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import com.cloudage.membercenter.util.DateRecord;

@Entity
public class Book extends DateRecord {

	User user;

	// 图书标题
	private String title;
	// 图书作者
	private String author;
	// 图书出版社
	private String publisher;
	// 图书ISBN码
	private String isbn;
	// 图书价格
	private float price;
	// 图书标签
	private String tag;
	// 图书摘要
	private String summary;
	// 卖家备注
	private String text;
	// 书本数量
	private int booknumber;
	//图书照片
	private String bookavatar;

	@ManyToOne(optional = false) // 1个人出售多本书籍，也可以多个人买
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBookavatar() {
		return bookavatar;
	}
	public void setBookavatar(String bookavatar) {
		this.bookavatar = bookavatar;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getBooknumber() {
		return booknumber;
	}

	public void setBooknumber(int booknumber) {
		this.booknumber = booknumber;
	}
}
