package org.datacollector.service;

import org.datacollector.db.Userr;

public interface UserService {
	
	Userr findById(Long id);
	Userr findByEmail(String email);

}
