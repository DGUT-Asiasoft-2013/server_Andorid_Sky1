package com.cloudage.membercenter.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class DateRecord extends BaseEntity {

	Date createDate; // 评论创建时间
	Date editDate; // 评论编辑时间

	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	// @PreUpdate每次创建之前都会创建时间
	@PreUpdate
	public void onPreUpdate() {
		editDate = new Date();

	}

	// @PrePersist每次创建之前都会创建时间
	@PrePersist
	public void onPrePersist() {
		createDate = new Date();
		editDate = new Date();

	}
}
