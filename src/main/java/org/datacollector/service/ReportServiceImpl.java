package org.datacollector.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.datacollector.dao.ReportDao;
import org.datacollector.db.PollutionType;
import org.datacollector.db.Report;
import org.datacollector.db.model.ReportModel;
import org.datacollector.utils.Messages;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl implements ReportService {
	
	private static final int TODAY = 0;
	private static final int YESTERDAY = 1;
	private static final int THREE_DAYS = 3;
	private static final int WEEK = 7;
	private static final int MONTH = 30;
	
	@Autowired
	SessionFactory sf;
	
	@Autowired
	ReportDao dao;

	@Override
	public Long addReport(String lat, String lng, int pollutionType, String description, String email)
			throws Exception {
		Long result = null;
		validateReportParameters(lat, lng, pollutionType, email);

		PollutionType pollution = PollutionType.values()[pollutionType];
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Report report = new Report(lat, lng, pollution, description, email);
			result = dao.save(report, session);
			tx.commit();
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			tx.rollback();
			session.close();
			throw e;
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public List<ReportModel> getAll() throws Exception {
		List<Report> list = (List<Report>) dao.getAllActive();
		List<ReportModel> results = new ArrayList<>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
	}


	@Override
	public List<ReportModel> getByEmail(String email) throws Exception {
		Session session = null;
		List<Report> list;
		try {
			session = sf.openSession();
			list = dao.getAllByEmail(email, session);
		} catch (Exception e) {
			session.close();
			throw e;
		} finally {
			session.close();
		}

		List<ReportModel> results = new ArrayList<>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
	}


	@Override
	public List<ReportModel> getByFilter(Integer filterType) throws Exception {
		Session session = null;
		List<Report> list = null;
		try {
			session = sf.openSession();
			validateFilter(filterType);

			switch (filterType) {
				case TODAY: {
					list = dao.getByFilterToday(session);
					break;
				}
				case YESTERDAY: {
					list = dao.getByFilterYesterday(session);
					break;
				}
				case THREE_DAYS: {
					list = dao.getByFilter3Day(session);
					break;
				}
				case WEEK: {
					list = dao.getByFilterWeek(session);
					break;
				}
				case MONTH: {
					list = dao.getByFilterMonth(session);
					break;
				}
				default: {
					throw new Exception("Filter parameter should be one of: 0, 1, 3, 7, 30");
				}
			}
		} catch (Exception e) {
			session.close();
			throw e;
		} finally {
			session.close();
		}
		List<ReportModel> results = new ArrayList<>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
	}

	private static void validateFilter(Integer filter) throws Exception {
		IntegerValidator intValidator = IntegerValidator.getInstance();
		if(intValidator.validate(filter+"") == null) {
			throw new Exception("Filter parameter is invalid");
		}
		if(!intValidator.isInRange(filter, 0, 30)) {
			throw new Exception("Filter parameter should be one of: 0, 1, 3, 7, 30");
		}
	}

	@Override
	public List<ReportModel> getByDatePeriod(Long timestampFrom, Long timestampTo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ReportModel> getByArea(String centerLat, String centerLng, Double radius) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ReportModel loadReport(String reportsUID) throws Exception {
		Session session = null;
		Report r = null;
		try {
			session = sf.openSession();
			r = dao.getByUID(reportsUID, session);
			if(r == null) throw new Exception(Messages.REPORT_BY_GIVEN_ID_DOES_NOT_EXIST.name());
		} catch (Exception e) {

		} finally {
			session.close();
		}

		return new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
	}


	@Override
	public Boolean editReport(String lat, String lng, int pollutionType, String description, String email, String uid) throws Exception {
		Session session = null;
		Report r = null;
		try {
			session = sf.openSession();
			r = dao.getByUID(uid, session);
			if (r == null) throw new Exception(Messages.REPORT_BY_GIVEN_ID_DOES_NOT_EXIST.name());

			validateReportParameters(lat, lng, pollutionType, email);


			r.setLat(lat);
			r.setLng(lng);
			r.setPollution(PollutionType.values()[pollutionType]);
			if (description != null && !description.isEmpty())
				r.setDescription(description);
			if (email != null && !email.isEmpty())
				r.setEmail(email);
			dao.update(r, session);
		} catch (Exception e) {
			session.close();
			throw e;
		} finally {
			session.close();
		}
		return true;
	}


	private static void validateReportParameters(String lat, String lng, int pollutionType, String email) throws Exception {
		//VALIDATION
		IntegerValidator intValidator = IntegerValidator.getInstance();
		FloatValidator floatValidator = FloatValidator.getInstance();
		org.apache.commons.validator.routines.EmailValidator emailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance();

		if(floatValidator.validate(lat) == null) {
			throw new Exception("Latitude is not valid");
		}
		if(floatValidator.validate(lng) == null) {
			throw new Exception("Longitude is not valid");
		}
		if(intValidator.validate(pollutionType+"") == null) {
			throw new Exception("Type parameter is invalid");
		}
		if(!intValidator.isInRange(pollutionType, 0, 5)) {
			throw new Exception("Type parameter must be in range between 0 and 5");
		}
		if(email != null && !email.isEmpty() && !emailValidator.isValid(email)) {
			throw new Exception("Email is not valid");
		}
	}

	@Override
	public Boolean removeReport(String uid) throws Exception {

		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Report r = dao.getByUID(uid, session);
			if (r == null) throw new Exception(Messages.REPORT_BY_GIVEN_ID_DOES_NOT_EXIST.name());
			r.setActive(false);
			dao.update(r, session);
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			tx.rollback();
			session.close();
			throw e;
		} finally {
			session.close();
		}

		return true;
	}

}
