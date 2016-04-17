package org.datacollector.dao;

import java.util.Date;
import java.util.List;

import org.datacollector.db.Report;
import org.datacollector.db.commons.DaoTemplate;
import org.hibernate.Session;

public abstract class ReportDao extends DaoTemplate<Report>{

	/**
	 * All active by user email.
	 * @param email
	 * @param session
	 * @return
	 */
	public abstract List<Report> getAllByEmail(String email, Session session) throws RuntimeException;
	
	public abstract List<Report> getByFilterToday(Session session) throws RuntimeException;
	public abstract List<Report> getByFilterYesterday(Session session) throws RuntimeException;
	public abstract List<Report> getByFilter3Day(Session session) throws RuntimeException;
	public abstract List<Report> getByFilterWeek(Session session) throws RuntimeException;
	public abstract List<Report> getByFilterMonth(Session session) throws RuntimeException;
	
	public abstract Report getByUID(String uid, Session session) throws RuntimeException;
	
	public abstract List<Report> getByDatePeriod(Date dateFrom, Date dateTo, Session session) throws RuntimeException;
}
