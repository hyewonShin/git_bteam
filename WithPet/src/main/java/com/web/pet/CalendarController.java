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
		
		//캘린더 계산 해주는 객체 생성
		GregorianCalendar cal = new GregorianCalendar();
		//CalendarVO객체 선언
		CalendarVO calendarVO;
		
		//검색 날짜
		if(vo.getDay().equals("") && vo.getMonth().equals("") ) {
			//vo에 들어간 Day, Month가 없으면 보여줄 달력 페이지의 년월일을 vo에 넣어주기
			vo = new CalendarVO(String.valueOf(cal.get(Calendar.YEAR))
					, String.valueOf(cal.get(Calendar.MONTH) + 1) 
					, String.valueOf(cal.get(Calendar.DATE))
					, null);
		}//if
		
		
		//달력이 안만들어져 있기 때문에 달력 데이터 리스트에 데이터삽입하기
		Map<String, Integer> today_info = vo.today_info(vo);	//위에 넣어준 검색날짜로 VO에서 달력값들 계산한 후 가져오기
		ArrayList<CalendarVO> dateList = new ArrayList<CalendarVO>();	//달력값들 넣을 ArrayList만들기
		
		//실질적인 달력 데이터 리스트에 데이터 순서대로 삽입 시작(① ~ ③)
		
		//①일단 시작 인덱스 까지 아무것도 없는 데이터 삽입(달력의 비어있는 날 넣기)
		//시작일 전( today_info.get("start")-1 )까지 빈공간이기 때문에
		for(int i=0; i<today_info.get("start")-1; i++) {
			calendarVO = new CalendarVO(null, null, null, null);
			dateList.add(calendarVO);
		}//for
		
		//②날짜삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {//시작일 부터 끝나는 날까지 넣기
			if(i==today_info.get("today")) {
				calendarVO = new CalendarVO(String.valueOf(vo.getYear())
						, String.valueOf(vo.getMonth()), String.valueOf(i), "today");
			}else {
				calendarVO = new CalendarVO(String.valueOf(vo.getYear())
						, String.valueOf(vo.getMonth()), String.valueOf(i), "normal_day");
			}//if		
			dateList.add(calendarVO);
		}//for
		
		//③남은 달력 빈곳 빈 데이터로 삽입
		int index = 7 - (dateList.size() % 7);
		if (dateList.size() % 7 != 0) {		//월~일까지 해서 딱 떨어지지 않는 경우 달력에 빈곳이 생김
			for (int i = 0; i < index; i++) {	//비어있는 만큼 아무것도 없는 데이터 삽입
				calendarVO = new CalendarVO(null, null, null, null);
				dateList.add(calendarVO);
			}//for
		}//if
		System.out.println(dateList);

		// 배열에 담음
		model.addAttribute("dateList", dateList); // 날짜 데이터 배열
		model.addAttribute("today_info", today_info);	//오늘 날짜에 대한 정보
		
		return "calendar";
		
	}//calendar
	
}//CalendarController
