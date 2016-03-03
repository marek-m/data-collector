package org.datacollector;

import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.datacollector.db.model.ReportModel;
import org.datacollector.service.ReportService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Object addReport(
			@RequestParam String lat, 
			@RequestParam String lng,
			@RequestParam Integer type,
			@RequestParam(required=false) String description,
			@RequestParam(required=false) String email) throws Exception {
		
		//MOVE VALIDATION TO DIFFERENT CLASS
		IntegerValidator intValidator = IntegerValidator.getInstance();
		FloatValidator floatValidator = FloatValidator.getInstance();
		EmailValidator emailValidator = EmailValidator.getInstance();
		
		if(floatValidator.validate(lat) == null) {
			throw new Exception("Latitude is not valid");
		}
		if(floatValidator.validate(lng) == null) {
			throw new Exception("Longitude is not valid");
		}
		if(intValidator.validate(type+"") == null) {
			throw new Exception("Type parameter is invalid");
		}
		if(!intValidator.isInRange(type, 0, 5)) {
			throw new Exception("Type parameter must be in range between 0 and 5");
		}
		if(email != null && !email.isEmpty() && !emailValidator.isValid(email)) {
			throw new Exception("Email is not valid");
		}
		
		return service.addReport(lat, lng, type, description, email);
	}
		
	@RequestMapping("/getAll")
	public @ResponseBody Object getAll() throws Exception {
		throw new NotYetImplementedException();
	}
	
	@RequestMapping("/getAllByEmail")
	public @ResponseBody Object getAllByEmail(
			@RequestParam String email) throws Exception {
		throw new NotYetImplementedException();
	}
	
	@RequestMapping("/getAllByFilter")
	public @ResponseBody Object getAllByFilter(
			@RequestParam Integer filter) throws Exception {
		throw new NotYetImplementedException();
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
	
}