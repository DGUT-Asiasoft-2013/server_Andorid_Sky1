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
	Subscribe findById(int saler_id);
	void changeBoolean(int user_id, int saler_id, boolean b);
	int getUserCount(int user_id);
	int isExistence(int user_id);
	void setCountZero(int user_id, int saler_id);
	int getCountWithSalerId(int user_id, int saler_id);
}
