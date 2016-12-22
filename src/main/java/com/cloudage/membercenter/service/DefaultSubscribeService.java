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
		
		Subscribe lk = new Subscribe();
		lk.setId(key);
		subscribeRepo.save(lk);
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
}
