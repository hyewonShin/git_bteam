package com.withpet.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import calendar.CalendarServiceImpl;
import calendar.CalenderDTO;
import calendar.DiagnosisDTO;

@Controller
public class CalendarController {
	@Autowired private CalendarServiceImpl service;
	
	//검진기록가져오기
	@RequestMapping(value = "/anDiagnosisGet"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anDiagnosisGet(HttpServletRequest req, Model model) {
		int pet = Integer.parseInt(req.getParameter("d_pet"));
		List<DiagnosisDTO> list = service.anDiagnosisGet(pet);
		model.addAttribute("list", list);
		return "calendar/anDiagnosisGet";
	}//anDiagnosisGet()
	
	//캘린더 일정 넣기
	@RequestMapping(value = "/anCalendarInsert"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anCalendarInsert(HttpServletRequest req, Model model) {
		CalenderDTO dto = new CalenderDTO();
		dto.setTel((String) req.getParameter("tel"));
		dto.setYear(Integer.parseInt(req.getParameter("year")));
		dto.setMonth(Integer.parseInt(req.getParameter("month")));
		dto.setDate(Integer.parseInt(req.getParameter("date")));
		dto.setContent((String) req.getParameter("content"));
		
		int state = service.anCalendarInsert(dto);
		model.addAttribute("result", String.valueOf(state));
		return "calendar/result";
	}//anCalendarInsert()
	
	//캘린더 일정 가져오기
	@RequestMapping(value = "/anCalenderGet"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anCalenderGet(HttpServletRequest req, Model model) {
		String tel = (String) req.getParameter("tel");
		List<CalenderDTO> list = service.anCalenderGet(tel);
		
		model.addAttribute("list", list);
		return "calendar/anCalenderGet";
	}//anCalenderGet()
	
}
