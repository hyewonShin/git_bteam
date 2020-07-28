package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QnaController {
	/* 사이트 QnA */
	@RequestMapping("/list.qa")
	public String list(HttpSession session) {
		session.setAttribute("category", "qa");
		session.setAttribute("qanum", "list");
		return"qna/list";
	}
	
	/* 전문가 QnA */
	@RequestMapping("/faq.qa")
	public String faq(HttpSession session) {
		session.setAttribute("qanum", "faq");
		session.setAttribute("category", "qa");
		return "qna/faq";
	}
}
