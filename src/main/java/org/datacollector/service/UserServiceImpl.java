package org.datacollector.service;

import org.datacollector.dao.UserDao;
import org.datacollector.db.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
	
	@Override
	public Userr findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public Userr findByEmail(String email) {
		Userr result=dao.findByEmail(email);
		return result;
	}

}
