package org.datacollector.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.datacollector.dao.UserDao;
import org.datacollector.db.UserProfileType;
import org.datacollector.db.Userr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	SessionFactory sf;
	
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

	@Override
	public Boolean register(String email, String password) throws Exception {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			EmailValidator emailValidator = EmailValidator.getInstance();	
			if(email != null && !email.isEmpty() && !emailValidator.isValid(email)) {
				throw new Exception("Email is not valid");
			}
			if(password != null && password.length() < 6) {
				throw new Exception("Password is to short");
			}
			if(dao.findByEmail(email) != null)
				throw new Exception("User with email:"+email + " already exists!");
			
			Userr user = new Userr();
			user.setPassword(password);
			user.setEmail(email);
			user.setRole(UserProfileType.USER);
			dao.save(user, session);
			tx.commit();
			
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			tx.rollback();
			throw e;
		} finally {
			if(session.isOpen())
				session.close();
		}

		return true;
	}

}
