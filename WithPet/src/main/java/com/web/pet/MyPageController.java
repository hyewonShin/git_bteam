package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyPageController {
	@RequestMapping("/list.pa")
	public String list(HttpSession session) {
		session.setAttribute("category", "pa");
		return "mypage/list";
	}
}
