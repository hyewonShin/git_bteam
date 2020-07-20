package com.withpet.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import health.HealthDTO;
import health.HealthServiceImpl;

@Controller
public class HealthController {
	@Autowired private HealthServiceImpl service;
	
	//동물운동정보 저장
	@RequestMapping(value="/anHealth"
					, method = {RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anHealth (HttpServletRequest req, Model model) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pet = Integer.parseInt((String) req.getParameter("pet"));
		String location = (String) req.getParameter("location");
		String date = (String) req.getParameter("date");
		HealthDTO dto = new HealthDTO(num, pet, location, date);
		
		int state = service.anHealth(dto);
		model.addAttribute("result", String.valueOf(state));
		
		return "health/anHealth";
	}//anHealth()
	
	//동물운동정보 가져오기
	@RequestMapping(value="/anHealthGet"
					, method = {RequestMethod.GET, RequestMethod.POST}
					, produces = "text/html; charset=utf-8")
	public String anHealthGet(HttpServletRequest req, Model model){
		int pet = Integer.parseInt(req.getParameter("pet"));
	 	List<HealthDTO> list = service.anHealthGet(pet);
		model.addAttribute("list", list);
		service.anHealthGet(pet);
		return "health/anHealthGet";
	}//anHealthGet()
	
}
