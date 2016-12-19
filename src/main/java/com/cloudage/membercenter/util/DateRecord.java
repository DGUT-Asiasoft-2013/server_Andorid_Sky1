package com.cloudage.membercenter.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class DateRecord extends BaseEntity {

	Date createDate; // ���۴���ʱ��
	Date editDate; // ���۱༭ʱ��

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

	// @PreUpdateÿ�δ���֮ǰ���ᴴ��ʱ��
	@PreUpdate
	public void onPreUpdate() {
		editDate = new Date();

	}

	// @PrePersistÿ�δ���֮ǰ���ᴴ��ʱ��
	@PrePersist
	public void onPrePersist() {
		createDate = new Date();
		editDate = new Date();

	}
}
