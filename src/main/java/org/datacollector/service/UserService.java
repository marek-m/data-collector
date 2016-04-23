package org.datacollector.service;

import org.datacollector.db.Userr;

public interface UserService {
	
	Userr findById(Long id);
	Userr findByEmail(String email);
	Boolean register(String email, String password) throws Exception;

}
