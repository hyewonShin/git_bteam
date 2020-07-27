package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommunityController {
	@RequestMapping("/list.co")
	public String list(HttpSession session) {
		session.setAttribute("category", "co");
		return "community/list";
	}
}
