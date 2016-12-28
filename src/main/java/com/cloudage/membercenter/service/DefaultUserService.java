package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IUserRepository;

/*
 *  实锟斤拷锟斤拷
 */
@Component
@Service
@Transactional
public class DefaultUserService implements IUserService {

	@Autowired
	IUserRepository userRepo;
	
	/*
	 * 实锟斤拷锟斤拷save(User user)锟斤拷锟斤拷
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#save(com.cloudage.membercenter.entity.User)
	 */
	public User save(User user) {
		return userRepo.save(user);
	}
	
	/*
	 * 实锟斤拷锟斤拷findNum(String num)锟斤拷锟斤拷
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#findNum(com.cloudage.membercenter.entity.User)
	 */
	@Override
	public User findNum(String num){
		return userRepo.findNum(num);
	}

	/*
	 * 实锟斤拷锟斤拷findEmail(String email)锟斤拷锟斤拷
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#findEmail(java.lang.String)
	 */
	@Override
	public User findEmail(String email) {
		// 锟斤拷锟斤拷userRepo锟斤拷锟斤拷findEmail()锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷锟斤拷IUserRepository锟斤拷锟斤拷锟斤拷select...锟斤拷锟�
		return userRepo.findEmail(email);
	}

	
	@Override
	public User create(String account, String passwordHash) {
		User user = new User();
		user.setAccount(account);
		user.setPasswordHash(passwordHash);
		return userRepo.save(user);
	}

	@Override
	public void login(String account, String passwordHash) {
		

	}

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePassword(String newPasswordHash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public User findOne(int userId) {
		return userRepo.findOne(userId);
	}


	@Override
	public User creat(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public User findSum(float sumMoney) {
		// TODO Auto-generated method stub
		return userRepo.findSum(sumMoney);
	}
	


	
	

}
