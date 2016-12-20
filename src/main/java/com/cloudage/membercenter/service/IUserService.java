package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.User;

public interface IUserService {
	User create(String account, String passwordHash);
	
	User save(User user);              //定义user的save()方法，使得注册的时候保存数据
	User findNum(String num);           //定义user的findNum()方法，使得登录的时候找到一家注册的数据
	User findEmail(String email);       //定义user的findEmail()方法，使得登录的时候找到一家注册的email
	
	
	void login(String account, String passwordHash);
	User getCurrentUser();
	boolean changePassword(String newPasswordHash);
	void logout();

}