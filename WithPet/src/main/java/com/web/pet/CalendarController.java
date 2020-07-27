package com.web.pet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import calendar.CalendarVO;

@Controller
public class CalendarController {
	
	@RequestMapping("/calendar.do")
	public String calendar(Model model, HttpServletRequest request, CalendarVO vo) {
		
		//Ķ���� ��� ���ִ� ��ü ����
		GregorianCalendar cal = new GregorianCalendar();
		CalendarVO calendarVO;
		
		//�˻� ��¥
		if(vo.getDay().equals(null) && vo.getMonth().equals(null)) {
			//vo�� �� Day, Month�� ������ ������ �޷� �������� ������� vo�� �־��ֱ�
			calendarVO = new CalendarVO(String.valueOf(cal.get(Calendar.YEAR))
					, String.valueOf(cal.get(Calendar.MONTH)) + 1
					, String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
					, null);
		}//if
		
		
		//�޷��� �ȸ������ �ֱ� ������ �޷� ������ ����Ʈ�� �����ͻ����ϱ�
		Map<String, Integer> today_info = vo.today_info(vo);	//���� �־��� �˻���¥�� VO���� �޷°��� ����� �� ��������
		ArrayList<CalendarVO> dateList = new ArrayList<CalendarVO>();	//�޷°��� ���� ArrayList�����
		
		//�������� �޷� ������ ����Ʈ�� ������ ������� ���� ����(�� ~ ��)
		
		//���ϴ� ���� �ε��� ���� �ƹ��͵� ���� ������ ����(�޷��� ����ִ� �� �ֱ�)
		//������ ��( today_info.get("start")-1 )���� ������̱� ������
		for(int i=0; i<today_info.get("start")-1; i++) {
			calendarVO = new CalendarVO(null, null, null, null);
			dateList.add(calendarVO);
		}//for
		
		//�賯¥����
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {//������ ���� ������ ������ �ֱ�
			if(i==today_info.get("today")) {
				calendarVO = new CalendarVO(String.valueOf(vo.getYear())
						, String.valueOf(vo.getMonth()), String.valueOf(i), "today");
			}else {
				calendarVO = new CalendarVO(String.valueOf(vo.getYear())
						, String.valueOf(vo.getMonth()), String.valueOf(i), "normal_date");
			}//if		
		}//for
		
		//�鳲�� �޷� ��� �� �����ͷ� ����
		int index = 7 - (dateList.size() % 7);
		if (dateList.size() % 7 != 0) {		//��~�ϱ��� �ؼ� �� �������� �ʴ� ��� �޷¿� ����� ����
			for (int i = 0; i < index; i++) {	//����ִ� ��ŭ �ƹ��͵� ���� ������ ����
				calendarVO = new CalendarVO(null, null, null, null);
				dateList.add(calendarVO);
			}//for
		}//if
		System.out.println(dateList);

		// �迭�� ����
		model.addAttribute("dateList", dateList); // ��¥ ������ �迭
		model.addAttribute("today_info", today_info);	//���� ��¥�� ���� ����
		
		return "views/calendar";
		
	}//calendar
	
}//CalendarController
