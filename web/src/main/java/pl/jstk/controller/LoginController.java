package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.jstk.constants.ViewNames;
import pl.jstk.service.impl.UserServiceImpl;

@Controller
public class LoginController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@GetMapping(value= "/login")
	public String showLoginPanel(Model model){
		return ViewNames.LOGIN;
	}

	
}
