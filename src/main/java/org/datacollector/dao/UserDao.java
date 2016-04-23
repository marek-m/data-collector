package org.datacollector.dao;

import org.datacollector.db.Userr;
import org.datacollector.db.commons.DaoTemplate;

public abstract class UserDao extends DaoTemplate<Userr> {
	
	public abstract Userr findByEmail(String email);
}
