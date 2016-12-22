package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.User;

public interface ISubscribeService {
	void addSubscribe(User user, User saler);
	void removeSubscribe(User user, User saler);
	int countSubscribe(int salerId);
	boolean checkSubscribe(int userId, int salerId);
	List<Subscribe> findAllByUser(int meId);
}
