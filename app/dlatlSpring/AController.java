package com.csslect.app.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.csslect.app.command.ABoardInsertCommand;
import com.csslect.app.command.ACalenderDelete;
import com.csslect.app.command.ACalendarInsert;
import com.csslect.app.command.ACalenderUpdate;
import com.csslect.app.command.ACalenderGetCommand;
import com.csslect.app.command.ACameraDBCommand;
import com.csslect.app.command.ACommand;
import com.csslect.app.command.ADBResultArrayCommand;
import com.csslect.app.command.AHealthGetCommand;
import com.csslect.app.command.ADeleteMultiCommand;
import com.csslect.app.command.AInsertMultiCommand;
import com.csslect.app.command.AJoinCommand;
import com.csslect.app.command.ALoginCommand;
import com.csslect.app.command.APetCommand;
import com.csslect.app.command.APetGetCommand;
import com.csslect.app.command.ASelectMultiCommand;
import com.csslect.app.command.AUpdateMultiCommand;
import com.csslect.app.command.AUpdateMultiNoCommand;
import com.csslect.app.command.AanBoardGetCommand;
import com.csslect.app.command.ADiagnosisGetCommand;
import com.csslect.app.command.AHealthCommand;

@Controller
public class AController {
	
	ACommand command;
			
	//동물정보 저장
	@RequestMapping(value="/anPet", method = {RequestMethod.GET, RequestMethod.POST })
	public String anPet (HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}
		
		String num = (String) req.getParameter("num");
		String name = (String) req.getParameter("name");
		String tel = (String) req.getParameter("tel");
		String animal = (String) req.getParameter("animal");
		String a_animal = (String) req.getParameter("a_animal");
		String birth = (String) req.getParameter("birth");
		String pic = (String) req.getParameter("pic");
		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("image");
		
				
		model.addAttribute("num", num);
		model.addAttribute("name", name);
		model.addAttribute("tel", tel);
		model.addAttribute("animal", animal);
		model.addAttribute("a_animal", a_animal);
		model.addAttribute("birth", birth);
		//사진 DB주소
		model.addAttribute("pic", pic);
		//사진 서버에 넣기
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));										
				} catch (Exception e) {
					e.printStackTrace();
				}//try 
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);	
			}//if			
		}//if
		
		//ACommand를 상속받은 하위 Command인 APetCommand를 사용함
		command = new APetCommand();
		command.execute(model);		
		
		return "anPet";
	}//anPet
	
	//동물정보가져오기(JsonObject)
	@RequestMapping(value="/anPetGet", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anPetGet(HttpServletRequest req, Model model){
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String num = (String) req.getParameter("num");
		
		System.out.println(num);
		
		model.addAttribute("num", num);
		
		//ACommand를 상속받은 하위 커멘드를 사용함
		command = new APetGetCommand();
		command.execute(model);
		
		return "anPetGet";
	}
	
	
	//검진기록가져오기
	@RequestMapping(value = "/anDiagnosisGet", method = { RequestMethod.GET, RequestMethod.POST })
	public String anDiagnosisGet(HttpServletRequest req, Model model) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String pet = (String) req.getParameter("d_pet");

		System.out.println("AController : " + pet);

		model.addAttribute("pet", pet);

		// ACommand를 상속받은 하위 커멘드를 사용함
		command = new ADiagnosisGetCommand();
		command.execute(model);

		return "anDiagnosisGet";
	}
	
	//사진저장
	@RequestMapping(value="/anCameraDB", method = {RequestMethod.GET, RequestMethod.POST} )
	public String anCameraDB(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String dbImgPath = (String) req.getParameter("dbImgPath");
		model.addAttribute("dbImgPath", dbImgPath);
		
		//Multipart사용해주기 위해서 선언
		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("image");
		
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println("fileName : " + fileName);
			
			//디렉토리 존재하지 않으면 생성
			makeDir(req);
			
			//file이 있다면(있기 때문에 크기가 0이상)
			if(file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( "fileName realImgPath" + fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());
				
				try {
					//이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));
				} catch(Exception e) {
					e.printStackTrace();
				}//try
			}else {
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println( "fileName realImgPath" + fileName + " : " + realImgPath);
			}//if
		}//if
		
		command = new ACameraDBCommand();
		command.execute(model);
		
		return "anCameraDB";
		
	}//anCameraDB()
	
	
	//동물정보가져오기(JsonArray)
	@RequestMapping(value="/anDBResultArray", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anDBResultArray(HttpServletRequest req, Model model){
			
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String tel = (String) req.getParameter("p_tel");			
		System.out.println(tel);
		
		model.addAttribute("tel", tel);
		
		//ACommand를 상속받은 하위 커멘드를 사용함
		command = new ADBResultArrayCommand();
		command.execute(model);
			
		return "anDBResultArray";
	}//anDBResultArray()
		
	
	//동물운동정보 저장
	@RequestMapping(value="/anHealth", method = {RequestMethod.GET, RequestMethod.POST })
	public String anHealth (HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}
		
		String num = (String) req.getParameter("num");
		String pet = (String) req.getParameter("pet");
		String location = (String) req.getParameter("location");
		String date = (String) req.getParameter("date");
		
				
		model.addAttribute("num", num);
		model.addAttribute("pet", pet);
		model.addAttribute("location", location);
		model.addAttribute("date", date);
		
		//ACommand를 상속받은 하위 Command인 APetCommand를 사용함
		command = new AHealthCommand();
		command.execute(model);		
		
		return "anHealth";
	}//anPet
	
	
	//동물운동정보 가져오기
	@RequestMapping(value="/anHealthGet", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anHealthGet(HttpServletRequest req, Model model){
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String pet = (String) req.getParameter("pet");			
		System.out.println(pet);
		
		model.addAttribute("pet", pet);
		
		//ACommand를 상속받은 하위 커멘드를 사용함
		command = new AHealthGetCommand();
		command.execute(model);
		
		return "anHealthGet";
	}//anDBResultArray()
	
	
	// 동물정보 저장
	@RequestMapping(value = "/anCalendarInsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalendarInsert(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}

		String tel = (String) req.getParameter("tel");
		String year = (String) req.getParameter("year");
		String month = (String) req.getParameter("month");
		String date = (String) req.getParameter("date");
		String content = (String) req.getParameter("content");

		model.addAttribute("tel", tel);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);
		model.addAttribute("content", content);

		command = new ACalendarInsert();
		command.execute(model);

		return "anCalendarInsert";
	}// anPet
	
		
	// 동물정보가져오기(JsonArray)
	@RequestMapping(value = "/anCalenderGet", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalenderGet(HttpServletRequest req, Model model) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String tel = (String) req.getParameter("tel");
		/*
		 * String year= (String) req.getParameter("year"); String month = (String)
		 * req.getParameter("month"); String date = (String) req.getParameter("date");
		 */
		System.out.println(tel);

		model.addAttribute("tel", tel);
		/*
		 * model.addAttribute("year", year); model.addAttribute("month", month);
		 * model.addAttribute("date", date);
		 */

		// ACommand를 상속받은 하위 커멘드를 사용함
		command = new ACalenderGetCommand();
		command.execute(model);

		return "anCalenderGet";
	}// anDBResultArray()
	
	
	// 동물정보 저장
	@RequestMapping(value = "/anCalenderUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalendarUpdate(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}

		String num = (String) req.getParameter("num");
		String year = (String) req.getParameter("year");
		String month = (String) req.getParameter("month");
		String date = (String) req.getParameter("date");
		String content = (String) req.getParameter("content");

		model.addAttribute("num", num);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);
		model.addAttribute("content", content);

		command = new ACalenderUpdate();
		command.execute(model);

		return "anCalenderUpdate";
	}// anPet
	
	// 동물정보 저장
	@RequestMapping(value = "/anCalenderDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String anCalendarDelete(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}

		String num = (String) req.getParameter("num");
		String year = (String) req.getParameter("year");
		String month = (String) req.getParameter("month");
		String date = (String) req.getParameter("date");

		model.addAttribute("num", num);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);

		command = new ACalenderDelete();
		command.execute(model);

		return "anCalenderDelete";
	}// anPet
	
	
	//글쓰기 내용 저장하기
	@RequestMapping(value = "/anBoardInsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String anBoardInsert(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}

		String name = (String) req.getParameter("name");
		String title = (String) req.getParameter("title");
		String content = (String) req.getParameter("content");
		String pic = (String) req.getParameter("pic");
		//실제이미지 주소를 얻기 위함
		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("image");

		model.addAttribute("name", name);
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		// 사진 DB주소
		model.addAttribute("pic", pic);
		// 사진 서버에 넣기
		if (file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);

			// 디렉토리 존재하지 않으면 생성
			makeDir(req);

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/");

				System.out.println(fileName + " : " + realImgPath);
				System.out.println("fileSize : " + file.getSize());

				try {
					// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));
				} catch (Exception e) {
					e.printStackTrace();
				} // try

			} else {
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			} // if
		} // if

		// ACommand를 상속받은 하위 Command인 APetCommand를 사용함
		command = new ABoardInsertCommand();
		command.execute(model);

		return "anBoardInsert";
	}// anPet
	
	//게시판 목록 가져오기(JsonArray)
	@RequestMapping(value = "/anBoardGet", method = { RequestMethod.GET, RequestMethod.POST })
	public String anBoardGet(HttpServletRequest req, Model model) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name = (String) req.getParameter("name");
		/*
		 * String year= (String) req.getParameter("year"); String month = (String)
		 * req.getParameter("month"); String date = (String) req.getParameter("date");
		 */
		System.out.println(name);

		model.addAttribute("name", name);
		/*
		 * model.addAttribute("year", year); model.addAttribute("month", month);
		 * model.addAttribute("date", date);
		 */

		// ACommand를 상속받은 하위 커멘드를 사용함
		command = new AanBoardGetCommand();
		command.execute(model);

		return "anBoardGet";
	}// anDBResultArray()
	
	
	@RequestMapping(value="/anBoardSubmit", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anBoardSubmit(HttpServletRequest req, Model model){
		System.out.println("anBoardSubmit()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 		
		
		String id = (String) req.getParameter("id");
		String passwd = (String) req.getParameter("passwd");
		String name = (String) req.getParameter("name");
		String phonenumber = (String) req.getParameter("phonenumber");
		String address = (String) req.getParameter("address");
		
		System.out.println(id);
		System.out.println(passwd);
		System.out.println(name);
		System.out.println(phonenumber);
		System.out.println(address);
		
		model.addAttribute("id", id);
		model.addAttribute("passwd", passwd);
		model.addAttribute("name", name);
		model.addAttribute("phonenumber", phonenumber);
		model.addAttribute("address", address);
		
		command = new AJoinCommand();
		command.execute(model);
		
		return "anJoin";
	}
	
	
	
	@RequestMapping(value="/anSelectMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anSelectMulti(HttpServletRequest req, Model model){
		System.out.println("anSelectMulti()");
		
		command = new ASelectMultiCommand();
		command.execute(model);
		
		return "anSelectMulti";
	}
	
	@RequestMapping(value="/anInsertMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anInsertMulti(HttpServletRequest req, Model model){
		System.out.println("anInsertMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println(dbImgPath);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);	
		model.addAttribute("dbImgPath", dbImgPath);	
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = multi.getFile("image");
		
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));										
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
				
		command = new AInsertMultiCommand();
		command.execute(model);
		
		return "anInsertMulti";
	}
		
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources"));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
		
	
	@RequestMapping(value="/anUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMulti(HttpServletRequest req, Model model){
		System.out.println("anUpdateMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String pDbImgPath = (String) req.getParameter("pDbImgPath");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println("Sub1Update:dbImgPath " + dbImgPath);
		System.out.println("Sub1Update:pDbImgPath " + pDbImgPath);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		model.addAttribute("dbImgPath", dbImgPath);
		
		// 이미지가 서로 같으면 삭제하지 않고 다르면 기존이미지 삭제
		if(!dbImgPath.equals(pDbImgPath)){			
			
			String pFileName = req.getParameter("pDbImgPath").split("/")[req.getParameter("pDbImgPath").split("/").length -1];
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);
			
			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());
			
	        if(delfile.exists()) {
	        	boolean deleteFile = false;
	            while(deleteFile != true){
	            	deleteFile = delfile.delete();
	            }	            
	            
	        }//if(delfile.exists())
		
		}//if(!dbImgPath.equals(pDbImgPath))  
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = null;
		
		file = multi.getFile("image");
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));						
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
		
		command = new AUpdateMultiCommand();
		command.execute(model);		
		
	}
	
	@RequestMapping(value="/anUpdateMultiNo", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMultiNo(HttpServletRequest req, Model model){
		System.out.println("anUpdateMultiNo()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");		
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		
		command = new AUpdateMultiNoCommand();
		command.execute(model);		
		
	}
		
	
	@RequestMapping(value="/anDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anDeleteMulti(HttpServletRequest req, Model model){
		System.out.println("anDeleteMulti()");		
		
		model.addAttribute("id", req.getParameter("id"));		
				
		System.out.println((String)req.getParameter("id"));
		System.out.println((String)req.getParameter("delDbImgPath"));
		
		String pFileName = req.getParameter("delDbImgPath").split("/")[req.getParameter("delDbImgPath").split("/").length -1];
		String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);		
		
		// 이미지 파일지우기
		File delfile = new File(delDbImgPath);
		System.out.println(delfile.getAbsolutePath());
		
        if(delfile.exists()) {
            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
            boolean deleteFile = false;
            while(deleteFile != true){
            	deleteFile = delfile.delete();
            }     
        }	
		
		command = new ADeleteMultiCommand();
		command.execute(model);	
		
	}

}
