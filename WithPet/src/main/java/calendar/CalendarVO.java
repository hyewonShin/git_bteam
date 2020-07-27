package calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarVO {
	//�������
	//year, month, day�� ���� �ִ��� ������ Ȯ���ϱ� ���� null���� ����� �ؼ� String���� ���
	String year, month, day, value, schedule, schedule_detail;
	
	
	//������ �����-
	public CalendarVO () {
		
	}//CalendarVO()

	//������ �ʱ�ȭ
	public CalendarVO(String year, String month, String day, String value) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.value = value;
	}//CalendarVO()
	
	//������ �ʱ�ȭ(��������� ����� ���)
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
	
	//��¥�� ���õ� �޷������� ������ �޼���
	public Map<String, Integer> today_info(CalendarVO vo) {
		//��¥ Ķ���� �Լ��� ����
		Map<String, Integer> today_Data = new HashMap<String, Integer>();
		//GregorianCalendar�� �̹� ����� �������� �־��ش�.
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(vo.getYear()), Integer.parseInt(vo.getMonth()), 1);
		
		//������
		int startDay = cal.getMinimum(Calendar.DATE);
		//��������
		int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//�����ϴ� ���� ���ϱ�
		int start = cal.get(Calendar.DAY_OF_WEEK);
		
		//���ÿ� ���� ��� ���ϱ�
		GregorianCalendar todayCal = new GregorianCalendar();
		SimpleDateFormat yearf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthf = new SimpleDateFormat("MM");
		
		//������ ���
		int today_year = Integer.parseInt(yearf.format(todayCal.getTime()));
		int today_month = Integer.parseInt(monthf.format(todayCal.getTime()));
		
		//Controller���� �־��� ���� ����� ��������
		int search_year = Integer.parseInt(vo.getYear());
		int search_month = Integer.parseInt(vo.getMonth()) + 1;

		//Controller���� �־��� ���� ����� ���� �޼ҵ忡�� ���� ���� ����� ������� today��¥ ���ϱ�
		int today = -1;
		if(today_year == search_year && today_month == search_month) {
			SimpleDateFormat dayf = new SimpleDateFormat("dd");
			today = Integer.parseInt(dayf.format(todayCal.getTime()));
		}//if
		
		//������ search_month�� ���� ���� +1 ���ذ��� �����ֱ�
		search_month = search_month -1;
		
		Map<String, Integer> before_after_calendar = before_after_calendar(search_year, search_month);
		
		//��¥�� ���� ���� hashMap�� �־��ֱ�
		today_Data.put("start", start);
		today_Data.put("startDay", startDay);
		today_Data.put("endDay", endDay);
		today_Data.put("today", today);
		today_Data.put("search_year", search_year);
		today_Data.put("search_month", search_month+1);
		today_Data.put("before_year", before_after_calendar.get("before_year"));
		today_Data.put("before_month", before_after_calendar.get("before_month"));
		today_Data.put("after_year", before_after_calendar.get("after_year"));
		today_Data.put("after_month", before_after_calendar.get("after_month"));
		
		return today_Data;
		
	}//today_info()

	//�����ް� ������, ������� ������ ���� �޼ҵ�
	private Map<String, Integer> before_after_calendar(int search_year, int search_month) {
		Map<String, Integer> before_after_data = new HashMap<String, Integer>();
		int before_year = search_year;
		int before_month = search_month - 1;
		int after_year = search_year;
		int after_month = search_month + 1;
		
		//���� ������� ���� before_month = search_month - 1�� ����µ�
		//�̶� �̶� berfore_month�� 0���� �۴ٴ� ���� ���⵵ 11���� �ǹ��ϱ� ������ before_month�� 11�� �ٲ��ش�.
		if(before_month < 0) {
			before_month = 11;	//���⵵ 11���� �ٲ��ش�.
			before_year=search_year-1;	//���⵵�� �ٲ��ش�.
		}//if
		
		//���� ������� ���� before_month = search_month + 1�� ����µ�
		//�̶� �̶� berfore_month�� 11���� ũ�ٴ� ���� �����ظ� �ǹ��ϱ� ������ after_month�� 0�� �ٲ��ش�.
		if(after_month > 11) {
			after_month = 0;
			after_year = search_year + 1;
		}//if
		
		//�����ް� ������, ������� �����⿡ ���ؼ� ����Ʈ�� ��� �����ش�.
		before_after_data.put("before_year", before_year);
		before_after_data.put("before_month", before_month);
		before_after_data.put("after_year", after_year);
		before_after_data.put("after_month", after_month);
		
		return before_after_data;
	}//before_after_calendar()
	
}//CalendarVO()
