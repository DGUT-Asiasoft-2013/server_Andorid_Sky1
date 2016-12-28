package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.User;

public interface IUserService {
	User create(String account, String passwordHash);
	
	User save(User user);              //锟斤拷锟斤拷user锟斤拷save()锟斤拷锟斤拷锟斤拷使锟斤拷注锟斤拷锟绞憋拷虮４锟斤拷锟斤拷锟�
	User findNum(String num);           //锟斤拷锟斤拷user锟斤拷findNum()锟斤拷锟斤拷锟斤拷使锟矫碉拷录锟斤拷时锟斤拷锟揭碉拷一锟斤拷注锟斤拷锟斤拷锟斤拷锟�
	User findEmail(String email);       //锟斤拷锟斤拷user锟斤拷findEmail()锟斤拷锟斤拷锟斤拷使锟矫碉拷录锟斤拷时锟斤拷锟揭碉拷一锟斤拷注锟斤拷锟絜mail
	
	User creat(User user);
	
	User findOne(int userId);//閫氳繃id瀵绘壘user
	void login(String account, String passwordHash);
	User getCurrentUser();
	boolean changePassword(String newPasswordHash);
	void logout();
	
	User findSum(float sumMoney);

	


}