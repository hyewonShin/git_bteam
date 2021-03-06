package com.withpet.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import calendar.CalendarServiceImpl;
import calendar.CalendarVO;
import calendar.DiagnosisVO;

@Controller
public class CalendarController {
	@Autowired private CalendarServiceImpl service;
	
	//검진기록가져오기
	@RequestMapping(value = "/anDiagnosisGet"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anDiagnosisGet(HttpServletRequest req, Model model) {
		int pet = Integer.parseInt(req.getParameter("d_pet"));
		List<DiagnosisVO> list = service.anDiagnosisGet(pet);
		model.addAttribute("list", list);
		return "calendar/anDiagnosisGet";
	}//anDiagnosisGet()
	
	//캘린더 일정 넣기
	@RequestMapping(value = "/anCalendarInsert"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anCalendarInsert(HttpServletRequest req, Model model) {
		CalendarVO vo = new CalendarVO();
		vo.setC_tel((String) req.getParameter("tel"));
		vo.setC_year(Integer.parseInt(req.getParameter("year")));
		vo.setC_month(Integer.parseInt(req.getParameter("month")));
		vo.setC_date(Integer.parseInt(req.getParameter("date")));
		vo.setC_content((String) req.getParameter("content"));
		
		int state = service.anCalendarInsert(vo);
		model.addAttribute("result", String.valueOf(state));
		return "calendar/result";
	}//anCalendarInsert()
	
	//캘린더 일정 가져오기
	@RequestMapping(value = "/anCalenderGet"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anCalenderGet(HttpServletRequest req, Model model) {
		String tel = (String) req.getParameter("tel");
		List<CalendarVO> list = service.anCalenderGet(tel);
		model.addAttribute("list", list);
		return "calendar/anCalenderGet";
	}//anCalenderGet()
	
	//캘린더 일정 수정
	@RequestMapping(value = "/anCalenderUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalendarUpdate(HttpServletRequest req, Model model) {
		CalendarVO vo = new CalendarVO();
		vo.setC_num(Integer.parseInt( req.getParameter("num")));
		vo.setC_year(Integer.parseInt(req.getParameter("year")));
		vo.setC_month(Integer.parseInt(req.getParameter("month")));
		vo.setC_date(Integer.parseInt(req.getParameter("date")));
		vo.setC_content((String) req.getParameter("content"));
		
		int result = service.anCalenderUpdate(vo);
		model.addAttribute("result", result);
		return "calendar/result";
	}//anCalenderUpdate()
	
	
	//캘린더 일정 삭제
	@RequestMapping(value = "/anCalenderDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalendarDelete(HttpServletRequest req, Model model) {
		CalendarVO vo = new CalendarVO();
		vo.setC_num(Integer.parseInt( req.getParameter("num")));
		vo.setC_year(Integer.parseInt(req.getParameter("year")));
		vo.setC_month(Integer.parseInt(req.getParameter("month")));
		vo.setC_date(Integer.parseInt(req.getParameter("date")));
		
		int result  = service.anCalendarDelete(vo);
		model.addAttribute("result", result);
		return "calendar/result";
	}// anPet
}
