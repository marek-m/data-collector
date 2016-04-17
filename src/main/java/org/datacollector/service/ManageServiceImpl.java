package org.datacollector.service;

import org.datacollector.dao.ManageDao;
import org.datacollector.dao.UserDao;
import org.datacollector.db.Report;
import org.datacollector.db.UserProfileType;
import org.datacollector.db.Userr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageServiceImpl implements ManageService {

	@Autowired
	ManageDao dao;

	@Autowired
	SessionFactory sf;

	@Override
	public Boolean registerUser(String email, String password) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Userr user = new Userr();
			user.setEmail(email);
			user.setPassword(password);
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
