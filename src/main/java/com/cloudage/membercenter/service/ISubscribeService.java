package com.cloudage.membercenter.service;

import java.util.List;

import com.cloudage.membercenter.entity.User;

public interface ISubscribeService {
	void addSubscribe(User user, User saler);
	void removeSubscribe(User user, User saler);
	int countSubscribe(int salerId);
	boolean checkSubscribe(int userId, int salerId);
	List<User> findAllByUser(int user_name);
}
