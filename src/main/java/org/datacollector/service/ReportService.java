package org.datacollector.service;

import java.util.List;

import org.datacollector.db.model.ReportModel;

public interface ReportService {
	public Long addReport(String lat, String lng, int pollutionType, String description, String email) throws Exception;
	public List<ReportModel> getAll() throws Exception;
	public List<ReportModel> getByEmail(String email) throws Exception;
	public List<ReportModel> getByFilter(Integer filterType) throws Exception;
	public List<ReportModel> getByDatePeriod(Long timestampFrom, Long timestampTo) throws Exception;
	public List<ReportModel> getByArea(String centerLat, String centerLng, Double radius) throws Exception;
	public ReportModel loadReport(String reportsUID) throws Exception;
	public Boolean editReport(String lat, String lng, int pollutionType, String description, String email, String uid) throws Exception;
	public Boolean removeReport(String uid) throws Exception;
}
