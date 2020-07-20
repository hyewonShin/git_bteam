package com.hanul.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.MemberVO;

@Controller
public class TestController {
	
	//로그인 결과화면
	@RequestMapping("/login_result")
	public String login(String id, String pw) {
		//아이디/비번 일치시 home 화면으로 연결, 
		//일치하지 않는 경우 로그인화면으로 연결
		if(id.equals("hong") && pw.equals("1234")) {
			//return "home";	//forward : 요청 url이 유지되나 응답에 해당하는 페이지의 url이 서로 상이
			return "redirect:/";
		} else {
			//return "member/login";	//forward
			return "redirect:login";
		}
	}
	
	//로그인화면
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
	
	
	//@PathVariable로 form의 데이터를 접근
	@RequestMapping("/joinPathVariable/{n}/{gender}/{email}")
	public String join(@PathVariable("n") String name, @PathVariable String gender, @PathVariable String email, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("gender", gender);
		model.addAttribute("email", email);
		model.addAttribute("method", "@RequestParam");
		return "member/info";
	}
	
	
	//데이터객체(VO)로 form의 데이터를 접근
	@RequestMapping("/joinDataObject")
	public String join(MemberVO vo, Model model) {
		model.addAttribute("vo", vo);
		model.addAttribute("method", "데이터객체");
		return "member/info";
	}
	
	//@RequestParam으로 form의 데이터를 접근
	@RequestMapping("/joinRequestParam")
	public String join(Model model, String name, 
			@RequestParam("gender") String g, String email) {
		model.addAttribute("name", name);
		model.addAttribute("gender", g);
		model.addAttribute("email", email);
		model.addAttribute("method", "@RequestParam");
		return "member/info";
	}
	
	// HttpServletRequest로 form의 데이터를 접근
	@RequestMapping("/joinRequest")
	public String join(HttpServletRequest request, Model model) {
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("gender", request.getParameter("gender"));
		model.addAttribute("email", request.getParameter("email"));
		model.addAttribute("method", "HttpServletRequest");
		return "member/info";
	}
	@RequestMapping("/join")
	public String join() {
		
		return "member/join";
	}
	
	@RequestMapping("/second")
	public ModelAndView view() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("now", 
				new SimpleDateFormat("a hh시 mm분 ss초").format(new Date()));
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/first")
	public String view(Model model) {
		model.addAttribute("today", 
				new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()));
		return "index";
	}

}
