package com.web.pet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.CommonService;
import community.CommunityPage;
import community.CommunityServiceImplement;

@Controller
public class CommunityController {

	@Autowired private CommunityServiceImplement service;
	@Autowired private CommunityPage page;
	@Autowired private CommonService common;
	
	/* 자유게시판 freeboard */
	@RequestMapping("/freeboard_list.co") 
	public String freeboard(HttpSession session, Model model){ 
		session.setAttribute("category", "co"); 
		session.setAttribute("conum","freeboard"); 
		/* model.addAttribute("page",service.freeboard_list(page)); */
		return "community/freeboard/freeboard_list"; 
	}
	 

	/* 분양 */
	@RequestMapping("/sale.co")
	public String sale(HttpSession session) {
		session.setAttribute("category", "co");
		session.setAttribute("conum", "sale");
		return "community/sale/sale";
	}

	/* 결혼(중매) */
	@RequestMapping("/marry.co")
	public String marry(HttpSession session) {
		session.setAttribute("category", "co");
		session.setAttribute("conum", "marry");
		return "community/marry/marry";
	}

}
