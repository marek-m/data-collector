package org.datacollector.service;

import java.util.ArrayList;
import java.util.List;

import org.datacollector.dao.ReportDao;
import org.datacollector.db.Report;
import org.datacollector.db.model.ReportModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	SessionFactory sf;
	
	@Autowired
	ReportDao dao;
	
	@Override
	public void add(String name) throws Exception {
		Report report = new Report(name);
		dao.save(report);
	}

	
	@Override
	public List<ReportModel> getReports() throws Exception {
		List<Report> list = (List<Report>) dao.getAll();
		List<ReportModel> results = new ArrayList<ReportModel>(0);
		
		for(Report r : list) {
			ReportModel m = new ReportModel(r.getId(), r.getName(), r.getDate());
			results.add(m);
		}
		return results;
	}

}
