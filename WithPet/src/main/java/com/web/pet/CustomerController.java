package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {
	@RequestMapping("list.cu")
	public String list(HttpSession session) {
		session.setAttribute("category", "cu");
		return "customer/list";
	}
}
