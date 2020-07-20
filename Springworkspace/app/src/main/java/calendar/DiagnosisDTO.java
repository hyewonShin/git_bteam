package calendar;

public class DiagnosisDTO {
	private int num;
	private int pet;
	private String title;
	private String content;
	private String Hname;
	private String date;
	
	public DiagnosisDTO () {
		
	}

	public DiagnosisDTO(int num, int pet, String title, String content, String hname, String date) {
		super();
		this.num = num;
		this.pet = pet;
		this.title = title;
		this.content = content;
		Hname = hname;
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPet() {
		return pet;
	}

	public void setPet(int pet) {
		this.pet = pet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHname() {
		return Hname;
	}

	public void setHname(String hname) {
		Hname = hname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}//class
