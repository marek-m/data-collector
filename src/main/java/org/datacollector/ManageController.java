package org.datacollector;

import org.apache.commons.validator.routines.EmailValidator;
import org.datacollector.aspect.UIMethod;
import org.datacollector.db.model.RegisterUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manage")
public class ManageController {

	@UIMethod
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String registerForm(Model model) throws Exception {
		model.addAttribute("userInput", new RegisterUser());
		return "page/registerForm";
	}
	
	@UIMethod
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String registerSubmit(@ModelAttribute RegisterUser registerUser, Model model) {
		model.addAttribute("userInput", registerUser);
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		
		if(registerUser.getEmail() != null && !registerUser.getEmail().isEmpty() && !emailValidator.isValid(registerUser.getEmail())) {
			registerUser.setSuccess(false);
			registerUser.setError("Email is not valid");
			return "home/registerResult";
		}
		if(registerUser.getPassword() != null && registerUser.getPassword().length() < 6) {
			registerUser.setSuccess(false);
			registerUser.setError("Password is to short");
			return "page/registerResult";
		}

		registerUser.setMessage("User"+registerUser.getEmail() + " registered!");
		
		return "page/registerResult";
	}
}