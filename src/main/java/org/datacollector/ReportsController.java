package org.datacollector;

import java.util.List;

import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.datacollector.aspect.UIMethod;
import org.datacollector.db.model.ReportModel;
import org.datacollector.service.ReportService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportService service;
	
	@UIMethod
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addReport(@ModelAttribute ReportModel report, Model model) throws Exception {
		//ONLY FOR ACCESS USER PARAMETERS FROM TH
		model.addAttribute("report", report);
		
		//MOVE VALIDATION TO DIFFERENT CLASS
		IntegerValidator intValidator = IntegerValidator.getInstance();
		FloatValidator floatValidator = FloatValidator.getInstance();
		
		if(floatValidator.validate(report.getLat()) == null) {
			throw new Exception("Latitude is not valid");
		}
		if(floatValidator.validate(report.getLng()) == null) {
			throw new Exception("Longitude is not valid");
		}
		if(intValidator.validate(report.getPollutionType()+"") == null) {
			throw new Exception("Type parameter is invalid");
		}
		if(!intValidator.isInRange(report.getPollutionType(), 0, 5)) {
			throw new Exception("Type parameter must be in range between 0 and 5");
		}
		service.addReport(report, getPrincipal());
		return "page/about";
	}
		
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
	
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
	@ModelAttribute("user")
	public String getUser() {
	   return getPrincipal();
	}
}