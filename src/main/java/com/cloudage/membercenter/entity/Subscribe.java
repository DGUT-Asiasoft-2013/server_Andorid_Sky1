package com.cloudage.membercenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cloudage.membercenter.util.BaseEntity;
/**
 * 订阅
 * @author Administrator
 *
 */
@Entity
public class Subscribe{
	@Embeddable
	public static class Key implements Serializable{
		User me;
		User saler;

		@ManyToOne(optional=false)
		public User getMe() {
			return me;
		}

		public void setMe(User me) {
			this.me = me;
		}
		@ManyToOne(optional=false)
		public User getSaler() {
			return saler;
		}

		public void setSaler(User saler) {
			this.saler = saler;
		}


		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Key){
				Key other = (Key)obj;
				return saler.getId() == other.saler.getId() && me.getId() == other.me.getId();
			}else{
				return false;
			}
		}

		@Override
		public int hashCode() {
			return saler.getId();
		}
	}

	Key id;
	Date createDate;

	@Column(updatable=false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@EmbeddedId
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	@PrePersist
	void onPrePersist(){
		createDate = new Date();
	}
}
