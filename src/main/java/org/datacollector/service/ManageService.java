package org.datacollector.service;

import org.datacollector.dao.UserDao;
import org.datacollector.db.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ManageService {

	public Boolean registerUser(String email, String password);

}
