package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteInfoController {
	@RequestMapping("/list.si")
	public String list(HttpSession session) {
		session.setAttribute("category", "si");
		return "siteinfo/list";
	}
}
