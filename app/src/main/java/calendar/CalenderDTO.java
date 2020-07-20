package calendar;

public class CalenderDTO {
	private String tel;
	private int num;
	private int year;
	private int month;
	private int date;
	private String content;
	
	public CalenderDTO () {
		
	}//CalenderDTO()
	
	//생성자 초기화
	public CalenderDTO(String tel, int num, int year, int month, int date, String content) {
		super();
		this.tel = tel;
		this.num = num;
		this.year = year;
		this.month = month;
		this.date = date;
		this.content = content;
	}//CalenderDTO()
	
	//getter & setter
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
