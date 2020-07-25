package com.web.pet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	@RequestMapping("/login")
	public String login() {
		return "";
	}
	
	
}
