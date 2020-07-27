package calendar;

public class CalendarVO {
	private String c_tel;
	private int c_num;
	private int c_year;
	private int c_month;
	private int c_date;
	private String c_content;
	
	public CalendarVO() {
		
	}
	
	public CalendarVO(String c_tel, int c_num, int c_year, int c_month, int c_date, String c_content) {
		super();
		this.c_tel = c_tel;
		this.c_num = c_num;
		this.c_year = c_year;
		this.c_month = c_month;
		this.c_date = c_date;
		this.c_content = c_content;
	}

	public String getC_tel() {
		return c_tel;
	}

	public void setC_tel(String c_tel) {
		this.c_tel = c_tel;
	}

	public int getC_num() {
		return c_num;
	}

	public void setC_num(int c_num) {
		this.c_num = c_num;
	}

	public int getC_year() {
		return c_year;
	}

	public void setC_year(int c_year) {
		this.c_year = c_year;
	}

	public int getC_month() {
		return c_month;
	}

	public void setC_month(int c_month) {
		this.c_month = c_month;
	}

	public int getC_date() {
		return c_date;
	}

	public void setC_date(int c_date) {
		this.c_date = c_date;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	
	
	
}
