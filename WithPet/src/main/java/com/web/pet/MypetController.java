package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MypetController {
	@RequestMapping("/list.my")
	public String list(HttpSession session) {
		session.setAttribute("category", "my");
		return "mypet/list";
	}
}
