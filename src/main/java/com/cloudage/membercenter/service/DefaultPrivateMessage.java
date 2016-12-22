package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.PrivateMessage;
import com.cloudage.membercenter.repository.IPrivateMessageRepository;
@Component
@Service
@Transactional
public class DefaultPrivateMessage implements IPrivateMessageService {

	@Autowired
	IPrivateMessageRepository iPrivateMessage;
	@Override
	public PrivateMessage save(PrivateMessage privateMessage) {
	return	 iPrivateMessage.save(privateMessage);
	}

}
