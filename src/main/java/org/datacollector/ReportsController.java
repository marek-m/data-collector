package org.datacollector;

import java.util.List;

import org.datacollector.db.model.ReportModel;
import org.datacollector.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportService service;
	
	@RequestMapping("/add")
	public String add(@RequestParam String name) {
		try {
			service.add(name);
			return "REPORT ADDED";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping("/getAll")
	public List<ReportModel> getAll() {
		try {
			return service.getReports();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
}