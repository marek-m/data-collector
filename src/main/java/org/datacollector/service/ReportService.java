package org.datacollector.service;

import java.util.List;

import org.datacollector.db.model.ReportModel;

public interface ReportService {
	public void add(String name) throws Exception;
	public List<ReportModel> getReports() throws Exception;
}
