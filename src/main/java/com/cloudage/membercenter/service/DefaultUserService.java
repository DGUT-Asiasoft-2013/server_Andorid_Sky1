package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IUserRepository;

/*
 *  ʵ����
 */
@Component
@Service
@Transactional
public class DefaultUserService implements IUserService {

	@Autowired
	IUserRepository userRepo;
	
	/*
	 * ʵ����save(User user)����
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#save(com.cloudage.membercenter.entity.User)
	 */
	public User save(User user) {
		return userRepo.save(user);
	}
	
	/*
	 * ʵ����findNum(String num)����
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#findNum(com.cloudage.membercenter.entity.User)
	 */
	@Override
	public User findNum(String num){
		return userRepo.findNum(num);
	}

	/*
	 * ʵ����findEmail(String email)����
	 * (non-Javadoc)
	 * @see com.cloudage.membercenter.service.IUserService#findEmail(java.lang.String)
	 */
	@Override
	public User findEmail(String email) {
		// ����userRepo����findEmail()��������Ϊ����IUserRepository������select...���
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
	public User findOne(int userName) {
		return userRepo.findOne(userName);
	}
	
	


	
	

}
