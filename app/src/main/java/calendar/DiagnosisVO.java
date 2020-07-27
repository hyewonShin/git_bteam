package calendar;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class DiagnosisVO {
	private int d_num;
	private int d_pet;
	private String d_title;
	private String d_content;
	private String d_hname;
	private String d_date;
	
	public DiagnosisVO () {
		
	}
	
	public DiagnosisVO(int d_num, int d_pet, String d_title, String d_content, String d_hname, Date d_date) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.d_num = d_num;
		this.d_pet = d_pet;
		this.d_title = d_title;
		this.d_content = d_content;
		this.d_hname = d_hname;
		this.d_date = sdf.format(d_date);		
	}

	public int getD_num() {
		return d_num;
	}

	public void setD_num(int d_num) {
		this.d_num = d_num;
	}

	public int getD_pet() {
		return d_pet;
	}

	public void setD_pet(int d_pet) {
		this.d_pet = d_pet;
	}

	public String getD_title() {
		return d_title;
	}

	public void setD_title(String d_title) {
		this.d_title = d_title;
	}

	public String getD_content() {
		return d_content;
	}

	public void setD_content(String d_content) {
		this.d_content = d_content;
	}

	public String getD_hname() {
		return d_hname;
	}

	public void setD_hname(String d_hname) {
		this.d_hname = d_hname;
	}

	public String getD_date() {
		return d_date;
	}

	public void setD_date(String d_date) {
		this.d_date = d_date;
	}

	
	

}//class
