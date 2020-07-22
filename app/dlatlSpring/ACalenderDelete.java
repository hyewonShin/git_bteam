package com.csslect.app.command;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;

public class ACalenderDelete implements ACommand {

	@Override
	public void execute(Model model) {
		int num = Integer.parseInt((String)model.asMap().get("num"));
		int year = Integer.parseInt((String) model.asMap().get("year"));
		int month = Integer.parseInt((String) model.asMap().get("month"));
		int date = Integer.parseInt((String) model.asMap().get("date"));
		

		//����� ���� �Դ��� Ȯ��
		System.out.println("num : " + num);
		System.out.println("year : " + year);
		System.out.println("month : " + month);
		System.out.println("date : " + date);
		
		//DB�� �����ϱ� ���� �޼ҵ�
		ANDao adao = new ANDao();
		
		int state = adao.anCalendarDelete(num, year, month, date);
		
		System.out.println(state);
		 
		model.addAttribute("anCalenderDelete", String.valueOf(state));

		
	}//execute()

}//class
