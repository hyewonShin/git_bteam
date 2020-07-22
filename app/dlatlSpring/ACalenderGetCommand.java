package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.CalenderDTO;

public class ACalenderGetCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String tel = (String) model.asMap().get("tel");
		/*
		 * int year = Integer.parseInt((String) model.asMap().get("year")); int month =
		 * Integer.parseInt((String) model.asMap().get("month")); int date =
		 * Integer.parseInt((String) model.asMap().get("date"));
		 */
		
		System.out.println("tel : " + tel);
		
		ANDao adao = new ANDao();
		ArrayList<CalenderDTO> list = adao.anCalenderGet(tel);
		
		model.addAttribute("anCalenderGet", list);
	}//execute()

}//class
