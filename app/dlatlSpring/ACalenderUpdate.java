package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class ACalenderUpdate implements ACommand {

	@Override
	public void execute(Model model) {
		int num = Integer.parseInt((String)model.asMap().get("num"));
		int year = Integer.parseInt((String) model.asMap().get("year"));
		int month = Integer.parseInt((String) model.asMap().get("month"));
		int date = Integer.parseInt((String) model.asMap().get("date"));
		String content = (String) model.asMap().get("content");
		

		//제대로 값이 왔는지 확인
		System.out.println("num : " + num);
		System.out.println("year : " + year);
		System.out.println("month : " + month);
		System.out.println("date : " + date);
		System.out.println("content : " + content);
		
		//DB와 연동하기 위한 메소드
		ANDao adao = new ANDao();
		
		int state = adao.anCalendarUpdate(num, year, month, date, content);
		
		System.out.println(state);
		 
		model.addAttribute("anCalenderUpdate", String.valueOf(state));

	}//execute

}//class
