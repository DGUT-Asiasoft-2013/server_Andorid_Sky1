package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.Subscribe.Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.ISubscribeRepository;

@Component
@Service
@Transactional
public class DefaultSubscribeService implements ISubscribeService{
	@Autowired
	ISubscribeRepository subscribeRepo;
	
	@Override
	public void addSubscribe(User me, User saler) {
		Subscribe.Key key = new Key();
		key.setMe(me);
		key.setSaler(saler);
		
		Subscribe s = new Subscribe();
		s.setId(key);
		subscribeRepo.save(s);
	}
	

	@Override
	public void removeSubscribe(User me, User saler) {
		Subscribe.Key key = new Key();
		key.setMe(me);
		key.setSaler(saler);
		
		subscribeRepo.delete(key);
	}

	@Override
	public int countSubscribe(int salerId) {
		return subscribeRepo.subscribeCountsOfSaler(salerId);
	}

	@Override
	public boolean checkSubscribe(int meId, int salerId) {
		return subscribeRepo.checkSubscribeExsists(meId, salerId)>0;
	}

//	@Override
//	public Page<User> findAllByUser(int meId) {
//		return subscribeRepo.findAllByMe(meId);
//	}
	@Override
	public List<Subscribe> findAllByUser(int meId) {
		return subscribeRepo.findAllByMe(meId);
	}

	@Override
	public Subscribe findById(int saler_id) {
		return subscribeRepo.findById(saler_id);
	}


	@Override
	public void changeBoolean(int user_id, int saler_id, boolean b) {
		subscribeRepo.changeB(user_id,saler_id,b);		
	}


	@Override
	public int getUserCount(int user_id) {
		return subscribeRepo.getCount(user_id);
	}


	@Override
	public int isExistence(int user_id) {
		return subscribeRepo.isExistence(user_id);
	}


	@Override
	public void setCountZero(int user_id, int saler_id) {
		subscribeRepo.setCountZero(user_id,saler_id);
	}


	@Override
	public int getCountWithSalerId(int user_id, int saler_id) {
		return subscribeRepo.getCountWithSalerId(user_id,saler_id);
	}
}
