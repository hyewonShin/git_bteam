package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.dto.ANDto;
import com.csslect.app.dto.BoardDTO;
import com.csslect.app.dto.CalenderDTO;
import com.csslect.app.dto.DiagnosisDTO;
import com.csslect.app.dto.HealthDTO;
import com.csslect.app.dto.MemberDTO;
import com.csslect.app.dto.PetDTO;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/team01");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}
	
    public MemberDTO anLogin(String idin, String passwdin) {

    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from member" 
							+ " where id = '" + idin 
							+ "' and passwd = '" + passwdin + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String phonenumber = resultSet.getString("phonenumber");
				String address = resultSet.getString("address"); 

				adto = new MemberDTO(id, name, phonenumber, address);							
			}	
			
			System.out.println("MemberDTO id : " + adto.getId());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
    
    public int anJoin(String id, String passwd, String name, 
    							String phonenumber, String address) { 
    	
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into member(id, passwd, name, phonenumber, address) " + 
			               "values('" + id + "', '" + passwd + "', '" + name + "', '" + 
					        			phonenumber + "', '" + address + "' )";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {			
			System.out.println(e.getMessage());
		} finally {
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}

	public ArrayList<ANDto> anSelectMulti() {		
		
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select id, name, hire_date, image_path "					
							+ " from android" 
							+ " order by id desc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Date date = resultSet.getDate("hire_date"); 
				String imagePath = resultSet.getString("image_path"); 

				ANDto adto = new ANDto(id, name, date, imagePath);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
	
	public int anInsertMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into android(id, name, hire_date, image_path) " + "values(" + id + ",'" 
							+ name + "'," + "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	

	public int anUpdateMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image_path = '" + dbImgPath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정1성공");
				
			} else {
				System.out.println("수정1실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정2성공");
				
			} else {
				System.out.println("수정2실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from android where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제성공");				
			} else {
				System.out.println("삭제실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}

	
	//Pet정보 넣기
	public int anPet(int num, String name, String tel, String animal, String a_animal, String birth, String pic) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "insert into pet values(?, ?, ?, ?, ?, to_date(?, 'YY/MM/DD'), ?)";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, num);
			prepareStatement.setString(2, name);
			prepareStatement.setString(3, tel);
			prepareStatement.setString(4, animal);
			prepareStatement.setString(5, a_animal);
			prepareStatement.setString(6, birth);
			prepareStatement.setString(7, pic);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		return state;
	}//anPet

	
	//Pet정보 가져오기
	public PetDTO anPetGet(int num) {
		
		PetDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		System.out.println(num);
		try {
			connection = dataSource.getConnection();
			String query = "select * from pet where p_num=?";
			prepareStatement = connection.prepareStatement(query);
			
			prepareStatement.setInt(1, num);		
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				num = resultSet.getInt("p_num");
				String name = resultSet.getString("p_name");
				String tel = resultSet.getString("p_tel");
				String animal = resultSet.getString("p_animal"); 
				String a_animal = resultSet.getString("p_a_animal"); 
				Date birth = resultSet.getDate("p_birth"); 
				String pic = resultSet.getString("p_pic"); 

				//Date를 String 형태로 바꾸기
				DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				String birthS = sdFormat.format(birth);			
				
				//System.out.println("num" + num);
				System.out.println("birth" + birth);
				adto = new PetDTO(num, name, tel, animal, a_animal, birthS, pic);							
			}	
			
			System.out.println("PetDTO Birth : " + adto.getBirth());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;
	}//anPetGet

	public int anCameraDB(String dbImgPath) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into android(image_path) values(?)";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, dbImgPath);
			state = prepareStatement.executeUpdate();
			
			if(state>0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}//if
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}//try

		}//try
		
		return state;
	}//anCameraDB()
	
	
	//전화번호와 연관된 동물 정보 모두 가져오기
	public ArrayList<PetDTO> anDBResultArray(String tel) {
		PetDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList<PetDTO> list = new ArrayList<PetDTO>();
		
		System.out.println(tel);
		try {
			connection = dataSource.getConnection();
			String query = "select * from pet where p_tel=?";
			prepareStatement = connection.prepareStatement(query);
			
			prepareStatement.setString(1, tel);		
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int num = resultSet.getInt("p_num");
				String name = resultSet.getString("p_name");
				tel = resultSet.getString("p_tel");
				String animal = resultSet.getString("p_animal"); 
				String a_animal = resultSet.getString("p_a_animal"); 
				Date birth = resultSet.getDate("p_birth"); 
				String pic = resultSet.getString("p_pic"); 

				//Date를 String 형태로 바꾸기
				DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				String birthS = sdFormat.format(birth);			
				
				System.out.println("birth" + birth);
				adto = new PetDTO(num, name, tel, animal, a_animal, birthS, pic);
				list.add(adto);
			}	
			
			System.out.println("PetDTO Birth : " + adto.getBirth());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return list;
	}//anDBResultArray()
	
	
	// pet넘버와 관련된 진단기록 가져오기
	public ArrayList<DiagnosisDTO> anDiagnosisGet(int pet) {
		DiagnosisDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList<DiagnosisDTO> list = new ArrayList<DiagnosisDTO>();

		System.out.println(pet);
		try {
			connection = dataSource.getConnection();
			String query = "select * from diagnosis where d_pet=? order by d_num";
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setInt(1, pet);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				int num = resultSet.getInt("d_num");
				pet = resultSet.getInt("d_pet");
				String title = resultSet.getString("d_title");
				String content = resultSet.getString("d_content");
				String Hname = resultSet.getString("d_Hname");
				Date date = resultSet.getDate("d_date");

				// Date를 String 형태로 바꾸기
				DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateS = sdFormat.format(date);

				System.out.println("daet" + date);
				adto = new DiagnosisDTO(num, pet, title, content, Hname, dateS);
				list.add(adto);
			}

			System.out.println("DiagnosisDTO : " + adto.getNum());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return list;
	}// anDiagnosisGet()

	
	
	//동물운동기록 저장
	public int anHealth(int num, int pet, String location, String date) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "insert into health values(?, ?, ?, to_date(?, 'YY/MM/DD'))";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, num);
			prepareStatement.setInt(2, pet);
			prepareStatement.setString(3, location);
			prepareStatement.setString(4, date);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}//if
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}//try
		return state;
		
	}//anHealth()

	
	//동물운동기록 가져오기
	public ArrayList<HealthDTO> anHealthGet(int pet) {
		HealthDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList<HealthDTO> list = new ArrayList<HealthDTO>();

		System.out.println(pet);
		try {
			connection = dataSource.getConnection();
			String query = "select * from health where h_pet=? order by h_num";
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setInt(1, pet);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				int num = resultSet.getInt("h_num");
				pet = resultSet.getInt("h_pet");
				String location = resultSet.getString("h_location");
				Date date = resultSet.getDate("h_date");
				

				// Date를 String 형태로 바꾸기
				DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateS = sdFormat.format(date);

				System.out.println("date" + date);
				adto = new HealthDTO(num, pet, location, dateS);
				list.add(adto);
			}//while

			System.out.println("HealthDTO : " + adto.getNum());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}//if

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}//try
		}//try

		return list;
	}//anHealthGet()

	
	//캘린더 일정 넣기
	public int anCalendarInsert (String tel, int year, int month, int date, String content) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "insert into calender values(?, c_seq.nextval, ?, ?, ?, ?)";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, tel);
			//prepareStatement.setInt(2, num);
			prepareStatement.setInt(2, year);
			prepareStatement.setInt(3, month);
			prepareStatement.setInt(4, date);
			prepareStatement.setString(5, content);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		return state;
	}//anCalendarInsert()

	public ArrayList<CalenderDTO> anCalenderGet(String tel) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		CalenderDTO adto = null;
		ArrayList<CalenderDTO> list = new ArrayList<CalenderDTO>();

		System.out.println(tel);
		try {
			connection = dataSource.getConnection();
			String query = "select * from calender where c_tel=? order by c_num";
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, tel);
			
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				tel = resultSet.getString("c_tel");
				int num = Integer.parseInt(resultSet.getString("c_num"));
				int year = Integer.parseInt(resultSet.getString("c_year"));
				int month = Integer.parseInt(resultSet.getString("c_month"));
				int date = Integer.parseInt(resultSet.getString("c_date"));
				String content = resultSet.getString("c_content");

				System.out.println("c_content" + content);
				adto = new CalenderDTO(tel, num, year, month, date, content);
				list.add(adto);
				
				System.out.println("CalenderDTO : " + adto.getContent() + num);
			}//while

			

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}//if

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}//try
		}//try

		return list;
	}//anCalenderGet
	
	
	//캘린더 내용 갱신
	public int anCalendarUpdate(int num, int year, int month, int date, String content) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "update calender set c_content = ? where c_num = ? and c_year = ? and c_month = ? and c_date = ?";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, content);
			prepareStatement.setInt(2, num);
			prepareStatement.setInt(3, year);
			prepareStatement.setInt(4, month);
			prepareStatement.setInt(5, date);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "수정성공");				
			} else {
				System.out.println(state + "수정실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		
		return state;
	}//anCalendarUpdate()
	
	public int anCalendarDelete(int num, int year, int month, int date) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "delete calender where c_num = ? and c_year = ? and c_month = ? and c_date = ?";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, num);
			prepareStatement.setInt(2, year);
			prepareStatement.setInt(3, month);
			prepareStatement.setInt(4, date);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삭제성공");				
			} else {
				System.out.println(state + "삭제실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}//try
		
		return state;
		
	}//anCalendarDelete()
	
	//글쓰기 내용 저장하기
	public int anBoardInsertCommand(String name, String title, String content, String pic) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		
		int state = -100;//DB에서 0보다 큰 수를 받으면 성공한 것이기 때문에 음수로 설정하여 제대로 작동되는 지 확인해줌
		
		try {
			
			connection = dataSource.getConnection();
			String query = "insert into board values(b_seq.nextval, 1, ?, ?, ?, ?, 0, 0, to_date(sysdate, 'YY/MM/DD'))";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, name);
			prepareStatement.setString(2, title);
			prepareStatement.setString(3, content);
			prepareStatement.setString(4, pic);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			//DB사용한 것을 끝내준다.
			try {				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}//try
		
		return state;

	}//anBoardInsertCommand()

	
	public ArrayList<BoardDTO> anBoardGet(String name) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		BoardDTO adto = null;
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		System.out.println(name);
		try {
			connection = dataSource.getConnection();
			String query = "select * from board where b_name=? order by b_num";
			prepareStatement = connection.prepareStatement(query);

			prepareStatement.setString(1, name);
			
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				int num = Integer.parseInt(resultSet.getString("b_num"));
				String group = resultSet.getString("b_group");
				name = resultSet.getString("b_name");
				String title = resultSet.getString("b_title");
				String content = resultSet.getString("b_content");
				String file = resultSet.getString("b_file");
				int seq = Integer.parseInt(resultSet.getString("b_seq"));
				int like = Integer.parseInt(resultSet.getString("b_like"));
				Date date = resultSet.getDate("b_date");
				
				// Date를 String 형태로 바꾸기
				DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateB = sdFormat.format(date);

				System.out.println("c_content" + content);
				adto = new BoardDTO(num, group, name, title, content, file, seq, like, dateB);
				list.add(adto);
				
				System.out.println("BoardDTO : " + adto.getB_content() + num);
			}//while

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}//if

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}//try
		}//try

		return list;
	}//anBoardGet

	

	
		
	
}//class


















