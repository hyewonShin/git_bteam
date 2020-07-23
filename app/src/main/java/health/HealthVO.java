package health;

public class HealthVO {
	private int num;
	private int pet;
	private String location;
	private String date;
	
	public HealthVO () {
		
	}

	public HealthVO(int num, int pet, String location, String date) {
		super();
		this.num = num;
		this.pet = pet;
		this.location = location;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
