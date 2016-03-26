package org.datacollector.dao;

import java.util.Date;
import java.util.List;

import org.datacollector.db.Report;
import org.datacollector.utils.MyDateTimeUtils;
import org.hibernate.Session;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDaoImpl extends ReportDao {

	@Override
	public List<Report> getAllByEmail(String email, Session session) throws RuntimeException {
		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.eq("active", true))
				.add(Restrictions.eq("email", email))
				.list();
	}

	@Override
	public List<Report> getByFilterToday(Session session) throws RuntimeException {
		DateTime now = DateTime.now();
		DateTime startOfDay = MyDateTimeUtils.localDateStartOfDay(now);

		System.out.println("FROM:"+startOfDay.toDate().toString());
		System.out.println("TO:"+MyDateTimeUtils.localDateNow().toDate().toString());

		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.eq("date", startOfDay.toDate()))
				.add(Restrictions.eq("active", true))
				.list();
	}

	@Override
	public List<Report> getByFilterYesterday(Session session) throws RuntimeException {
		DateTime now = DateTime.now();
		DateTime startOfDay = MyDateTimeUtils.localDateStartOfDay(now).minusDays(1);

		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.ge("date", startOfDay.toDate()))
				.add(Restrictions.le("date", MyDateTimeUtils.localDateNow().toDate()))
				.add(Restrictions.eq("active", true))
				.list();
	}

	@Override
	public List<Report> getByFilter3Day(Session session) throws RuntimeException {
		DateTime now = DateTime.now();
		DateTime startOfDay = MyDateTimeUtils.localDateStartOfDay(now).minusDays(3);

		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.ge("date", startOfDay.toDate()))
				.add(Restrictions.le("date", MyDateTimeUtils.localDateNow().toDate()))
				.add(Restrictions.eq("active", true))
				.list();
	}

	@Override
	public List<Report> getByFilterWeek(Session session) throws RuntimeException {
		DateTime now = DateTime.now();
		DateTime startOfDay = MyDateTimeUtils.localDateStartOfDay(now).withDayOfWeek(DateTimeConstants.MONDAY);

		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.ge("date", startOfDay.toDate()))
				.add(Restrictions.le("date", MyDateTimeUtils.localDateNow().toDate()))
				.add(Restrictions.eq("active", true))
				.list();
	}

	@Override
	public List<Report> getByFilterMonth(Session session) throws RuntimeException {
		DateTime now = DateTime.now();
		DateTime startOfDay = MyDateTimeUtils.localDateStartOfDay(now).withDayOfMonth(1);

		return  (List<Report>) session.createCriteria(Report.class)
				.add(Restrictions.ge("date", startOfDay.toDate()))
				.add(Restrictions.le("date", MyDateTimeUtils.localDateNow().toDate()))
				.add(Restrictions.eq("active", true))
				.list();
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
		throw new NotYetImplementedException();
	}


}
