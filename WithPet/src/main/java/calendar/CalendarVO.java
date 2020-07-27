package calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarVO {
	//멤버변수
	//year, month, day에 값이 있는지 없는지 확인하기 위한 null값을 써줘야 해서 String으로 사용
	String year="", month="", day="", value="", schedule="", schedule_detail="";
	
	
	//생성자 만들기
	public CalendarVO () {
		
	}//CalendarVO()

	//생성자 초기화
	public CalendarVO(String year, String month, String day, String value) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.value = value;
	}//CalendarVO()
	
	//생성자 초기화(스케쥴까지 사용할 경우)
	public CalendarVO(String year, String month, String day, String value, String schedule, String schedule_detail) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.value = value;
		this.schedule = schedule;
		this.schedule_detail = schedule_detail;
	}//CalendarVO()

	//Getter & Setter
	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getSchedule() {
		return schedule;
	}


	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}


	public String getSchedule_detail() {
		return schedule_detail;
	}


	public void setSchedule_detail(String schedule_detail) {
		this.schedule_detail = schedule_detail;
	}
	
	//날짜에 관련된 달력정보를 가지는 메서드
	public Map<String, Integer> today_info(CalendarVO vo) {
		//날짜 캘린더 함수에 삽입
		Map<String, Integer> today_Data = new HashMap<String, Integer>();
		//GregorianCalendar에 이번 년월과 시작일을 넣어준다.
		//가져온 달에 -1을 해줘야 올바른 값이 나옴
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(vo.getYear()), Integer.parseInt(vo.getMonth())-1, 1);
		
		//시작일
		int startDay = cal.getMinimum(Calendar.DATE);
		//끝나는일
		int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//시작하는 요일 구하기 → 1 = 월, 2 = 화 ...
		int start = cal.get(Calendar.DAY_OF_WEEK);
		
		//오늘에 대한 년월 구하기
		GregorianCalendar todayCal = new GregorianCalendar();
		SimpleDateFormat yearf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthf = new SimpleDateFormat("MM");
		
		//오늘의 년월
		int today_year = Integer.parseInt(yearf.format(todayCal.getTime()));
		int today_month = Integer.parseInt(monthf.format(todayCal.getTime()));
		
		//Controller에서 넣어준 오늘 년월을 가져오기
		int search_year = Integer.parseInt(vo.getYear());
		int search_month = Integer.parseInt(vo.getMonth());

		//Controller에서 넣어준 오늘 년월과 여기 메소드에서 구한 오늘 년월이 같을경우 today날짜 구하기
		int today = -1;
		if(today_year == search_year && today_month == search_month) {
			SimpleDateFormat dayf = new SimpleDateFormat("dd");
			today = Integer.parseInt(dayf.format(todayCal.getTime()));
		}//if
				
		Map<String, Integer> before_after_calendar = before_after_calendar(search_year, search_month);
		
		//날짜에 대한 값들 hashMap에 넣어주기
		today_Data.put("start", start);
		today_Data.put("startDay", startDay);
		today_Data.put("endDay", endDay);
		today_Data.put("today", today);
		today_Data.put("search_year", search_year);
		today_Data.put("search_month", search_month);
		today_Data.put("before_year", before_after_calendar.get("before_year"));
		today_Data.put("before_month", before_after_calendar.get("before_month"));
		today_Data.put("after_year", before_after_calendar.get("after_year"));
		today_Data.put("after_month", before_after_calendar.get("after_month"));
		
		//날짜를 넣은 hashMap전달
		return today_Data;
		
	}//today_info()

	//이전달과 다음달, 이전년과 다음년 관련 메소드
	private Map<String, Integer> before_after_calendar(int search_year, int search_month) {
		Map<String, Integer> before_after_data = new HashMap<String, Integer>();
		int before_year = search_year;
		int before_month = search_month - 1;
		int after_year = search_year;
		int after_month = search_month + 1;
		
		//위에 멤버변수 선언때 before_month = search_month - 1을 해줬는데
		//이때 이때 berfore_month가 0보다 작다는 것은 전년도 12월을 의미하기 때문에 before_month를 12로 바꿔준다.
		if(before_month < 1) {
			before_month = 12;	//전년도 12월로 바꿔준다.
			before_year = search_year - 1;	//전년도로 바꿔준다.
		}//if
		
		//위에 멤버변수 선언때 before_month = search_month + 1을 해줬는데
		//이때 이때 berfore_month가 12보다 크다는 것은 다음해를 의미하기 때문에 after_month를 1로 바꿔준다.
		if(after_month > 12) {
			after_month = 1;	//다음년도 1월
			after_year = search_year + 1;	//다음년도로 바꿔준다.
		}//if
		
		//이전달과 다음달, 이전년과 다음년에 관해서 리스트에 담고서 돌려준다.
		before_after_data.put("before_year", before_year);
		before_after_data.put("before_month", before_month);
		before_after_data.put("after_year", after_year);
		before_after_data.put("after_month", after_month);
		
		return before_after_data;
	}//before_after_calendar()
	
}//CalendarVO()
