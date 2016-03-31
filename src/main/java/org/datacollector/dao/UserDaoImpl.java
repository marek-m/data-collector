package org.datacollector.dao;

import org.datacollector.db.Userr;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends UserDao {
	
	@Override
	public Userr findByEmail(String email) {
		Session session = null;
		
		try {		
			session = getCurrentSession();
			Userr user = (Userr) session.createCriteria(Userr.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
			return user;
		} finally {
			session.close();
		}

	}

}
