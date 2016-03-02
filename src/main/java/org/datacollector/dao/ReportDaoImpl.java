package org.datacollector.dao;

import java.util.Date;
import java.util.List;

import org.datacollector.db.Report;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDaoImpl extends ReportDao {

	@Override
	public List<Report> getAll(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getAllByEmail(String email, Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getByFilterToday(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getByFilterYesterday(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getByFilter3Day(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getByFilterWeek(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getByFilterMonth(Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Report getByUID(String uid, Session session) throws RuntimeException {
		Report report = (Report) session.createCriteria(Report.class)
				.add(Restrictions.eqOrIsNull("uid", uid))
				.add(Restrictions.eq("active", true))
				.uniqueResult();
		return report;
	}

	@Override
	public List<Report> getByDatePeriod(Date dateFrom, Date dateTo, Session session) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}


}
