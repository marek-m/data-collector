package org.datacollector;

import java.util.List;

import org.datacollector.db.model.ReportModel;
import org.datacollector.service.ReportService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ReportService service;
		
	@RequestMapping("/getAll")
	public @ResponseBody Object getAll() throws Exception {
		return service.getAll();
	}
	
	@RequestMapping("/getAllByEmail")
	public @ResponseBody Object getAllByEmail(
			@RequestParam String email) throws Exception {
		return service.getByEmail(email);
	}
	
	@RequestMapping("/getAllByFilter")
	public @ResponseBody Object getAllByFilter(
			@RequestParam Integer filter) throws Exception {
		return service.getByFilter(filter);
	}
	
	@RequestMapping("/getAllByDatePeriod")
	public @ResponseBody List<ReportModel> getAllByEmail(
			@RequestParam String timestampFrom,
			@RequestParam String timestampTo) throws Exception {
		throw new NotYetImplementedException();
	}
	
	@RequestMapping("/getAllByArea")
	public @ResponseBody List<ReportModel> getAllByArea(
			@RequestParam String centerLat,
			@RequestParam String centerLng,
			@RequestParam String radius) throws Exception {
		throw new NotYetImplementedException();
	}
	
	@RequestMapping("/load")
	public @ResponseBody Object load(
			@RequestParam String uid) throws Exception {
		return service.loadReport(uid);
	}
	
	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public @ResponseBody Object remove(
			@RequestParam String uid) throws Exception {
		return service.removeReport(uid);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody Object editReport(
			@RequestParam String lat,
			@RequestParam String lng,
			@RequestParam Integer type,
			@RequestParam(required=false) String description,
			@RequestParam(required=false) String email,
			@RequestParam String uid) throws Exception {

		return service.editReport(lat, lng, type, description, email, uid);
	}
}