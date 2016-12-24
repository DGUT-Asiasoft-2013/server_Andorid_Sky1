package com.cloudage.membercenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

/*
 * 添加到购物车
 */
@Entity
public class Bookbus {

	@Embeddable
	public static class Bus_Key implements Serializable {
		//用户
		User user;
		Book book;              //书
		
		@ManyToOne(optional=false)
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		
		@ManyToOne(optional=false)
		public Book getBook() {
			return book;
		}
		public void setBook(Book book) {
			this.book = book;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Bus_Key){
				Bus_Key other = (Bus_Key)obj;
				return book.getId() == other.book.getId() && user.getId() == other.user.getId();
			}else{
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			return book.getId();
         }
		
	}
	
	Bus_Key id;
	Date createDate;
	
	@EmbeddedId
	public Bus_Key getId() {
		return id;
	}

	public void setId(Bus_Key id) {
		this.id = id;
	}

	@Column(updatable=false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@PrePersist
	void onPrePersist()
	{
		createDate=new Date();
	}
}
