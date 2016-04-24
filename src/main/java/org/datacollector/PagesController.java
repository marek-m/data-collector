package org.datacollector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.datacollector.aspect.UIMethod;
import org.datacollector.db.model.RegisterUser;
import org.datacollector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/page")
public class PagesController {

	@Autowired
	UserService userService;
	
	@ModelAttribute("user")
	public String getUser() {
	   return getPrincipal();
	}
	
	@UIMethod
	@RequestMapping("/index")
	public String index(Model model) {
		return "page/index";
	}
	
	@UIMethod
	@RequestMapping("/services")
	public String services(Model model) {
		return "page/services";
	}	
	
	@UIMethod
	@RequestMapping("/solutions")
	public String solutions(Model model) {
		return "page/solutions";
	}	
	
	@UIMethod
	@RequestMapping("/about")
	public String about(Model model) {
		return "page/about";
	}	
	
	@UIMethod
	@RequestMapping("/contact")
	public String contact(Model model) throws Exception {
		return "page/contact";
	}	
	
	@UIMethod
	@RequestMapping("/login")
	public String login(Model model) {
		return "page/loginForm";
	}
	
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
		
		try {
			userService.register(registerUser.getEmail(), registerUser.getPassword());
			registerUser.setSuccess(true);
			registerUser.setMessage("User "+registerUser.getEmail() + " registered!");
		} catch (Exception e) {
			registerUser.setError(e.getMessage());
			registerUser.setSuccess(false);
		} 
		return "page/registerResult";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/page/login";
	}
	
	@UIMethod
	@RequestMapping(value = "/error", method=RequestMethod.GET)
	public String error(Model model) throws Exception {
		return "page/error";
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
}