package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudage.membercenter.entity.PrivateMessage;
import com.cloudage.membercenter.repository.IPrivateMessageRepository;

public class DefaultPrivateMessage implements IPrivateMessageService {

	@Autowired
	IPrivateMessageRepository iPrivateMessage;
	@Override
	public PrivateMessage save(PrivateMessage privateMessage) {
	return	 iPrivateMessage.save(privateMessage);
	}

}
