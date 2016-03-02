package org.datacollector.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.validator.EmailValidator;
import org.datacollector.dao.ReportDao;
import org.datacollector.db.PollutionType;
import org.datacollector.db.Report;
import org.datacollector.db.model.ReportModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

	
	public List<ReportModel> getReports() throws Exception {
		Session session = sf.openSession();
		List<Report> list = (List<Report>) dao.getAll();
		session.close();
		
		List<ReportModel> results = new ArrayList<ReportModel>(0);
		
//		for(Report r : list) {
//			ReportModel m = new ReportModel(r.getId(), r.getName(), r.getDate());
//			results.add(m);
//		}
		return results;
	}


	@Override
	public Long addReport(String lat, String lng, int pollutionType, String description, String email)
			throws Exception {
		Long result = null;
		//Validate data
		isNullOrEmpty(lat, "lat");
		isNullOrEmpty(lng, "lng");
		
		PollutionType pollution = PollutionType.values()[pollutionType];
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Report report = new Report(lat, lng, pollution, description, email);
			result = dao.save(report, session);
			tx.commit();
			session.close();
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			tx.rollback();
			session.close();
			throw e;
		}
		
		return result;
	}

	private static void isNullOrEmpty(String parameter, String parameterName) throws Exception {
		if(Objects.isNull(parameter) || parameter.isEmpty()) {
			throw new Exception("Parameter " + parameterName + " is required");
		}
	}

	@Override
	public List<ReportModel> getAll() throws Exception {
		Session session = sf.openSession();
		List<Report> list = (List<Report>) dao.getAllActive();
		session.close();
		
		List<ReportModel> results = new ArrayList<ReportModel>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
	}


	@Override
	public List<ReportModel> getByEmail(String email) throws Exception {
		Session session = sf.openSession();
		List<Report> list = (List<Report>) dao.getAllByEmail(email, session);
		session.close();
		
		List<ReportModel> results = new ArrayList<ReportModel>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
	}


	@Override
	public List<ReportModel> getByFilter(Integer filterType) throws Exception {
		Session session = sf.openSession();
		List<Report> list = null;
		
		switch(filterType) {
			case TODAY: {
				list = (List<Report>) dao.getByFilterToday(session);
				break;
			}
			case YESTERDAY: {
				list = (List<Report>) dao.getByFilterYesterday(session);
				break;
			}
			case THREE_DAYS: {
				list = (List<Report>) dao.getByFilter3Day(session);
				break;
			}
			case WEEK: {
				list = (List<Report>) dao.getByFilterWeek(session);
				break;
			}
			case MONTH: {
				list = (List<Report>) dao.getByFilterMonth(session);
				break;
			}
			default: {
				break;
			}
		}
		session.close();
		List<ReportModel> results = new ArrayList<ReportModel>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
			results.add(m);
		}
		return results;
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
		Session session = sf.openSession();
		Report r = dao.getByUID(reportsUID, session);
		return new ReportModel(r.getId(), r.getLat(), r.getLng(), r.getPollution().ordinal(), r.getPollution().name(), r.getDescription(), r.getDate().getTime());
	}


	@Override
	public Long editReport(String lat, String lng, int pollutionType, String description, String uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean removeReport(String uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
