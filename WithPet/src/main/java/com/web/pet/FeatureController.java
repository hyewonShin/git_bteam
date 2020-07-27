package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FeatureController {
	@RequestMapping("/list.fe")
	public String list(HttpSession session) {
		session.setAttribute("category", "fe");
		return "feature/list";
	}
}
