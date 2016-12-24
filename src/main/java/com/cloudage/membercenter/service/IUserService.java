package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.User;

public interface IUserService {
	User create(String account, String passwordHash);
	
	User save(User user);              //����user��save()������ʹ��ע���ʱ�򱣴�����
	User findNum(String num);           //����user��findNum()������ʹ�õ�¼��ʱ���ҵ�һ��ע�������
	User findEmail(String email);       //����user��findEmail()������ʹ�õ�¼��ʱ���ҵ�һ��ע���email
	
	User findOne(int userId);//通过id寻找user
	void login(String account, String passwordHash);
	User getCurrentUser();
	boolean changePassword(String newPasswordHash);
	void logout();

	


}