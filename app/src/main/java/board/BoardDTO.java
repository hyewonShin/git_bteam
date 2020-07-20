package board;

public class BoardDTO {
	private int b_num;
	private String b_group;
	private String b_name;
	private String b_title;
	private String b_content;
	private String b_file;
	private int b_seq;
	private int b_like;
	private String b_date;

	public BoardDTO () {
		
	}

	public BoardDTO(int b_num, String b_group, String b_name, String b_title, String b_content, String b_file,
			int b_seq, int b_like, String b_date) {
		super();
		this.b_num = b_num;
		this.b_group = b_group;
		this.b_name = b_name;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_file = b_file;
		this.b_seq = b_seq;
		this.b_like = b_like;
		this.b_date = b_date;
	}
	

	public int getB_num() {
		return b_num;
	}

	public void setB_num(int b_num) {
		this.b_num = b_num;
	}

	public String getB_group() {
		return b_group;
	}

	public void setB_group(String b_group) {
		this.b_group = b_group;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_file() {
		return b_file;
	}

	public void setB_file(String b_file) {
		this.b_file = b_file;
	}

	public int getB_seq() {
		return b_seq;
	}

	public void setB_seq(int b_seq) {
		this.b_seq = b_seq;
	}

	public int getB_like() {
		return b_like;
	}

	public void setB_like(int b_like) {
		this.b_like = b_like;
	}

	public String getB_date() {
		return b_date;
	}

	public void setB_date(String b_date) {
		this.b_date = b_date;
	}
	
	
	
}
