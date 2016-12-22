package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.PrivateMessage;

public interface IPrivateMessageService {
	public PrivateMessage save(PrivateMessage privateMessage);

	Page<PrivateMessage> findPrivateMessagesByReveiverId(int receiverId,int senderId, int page);

}
