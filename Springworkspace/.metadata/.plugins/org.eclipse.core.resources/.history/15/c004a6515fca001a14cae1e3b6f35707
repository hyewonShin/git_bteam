package com.withpet.app;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import common.CommonService;
import pet.PetServiceImpl;
import pet.PetVO;

@Controller
public class PetController {
	@Autowired private PetServiceImpl service;
	@Autowired private CommonService common;
	
	//DB에서 정보 가져오기
	@RequestMapping("/petList")
	public String list(String m_tel, Model model) {
		System.out.println(m_tel);
		model.addAttribute("list", service.pet_list(m_tel));
		return "pet/list";
	}
	
	//동물정보추가
	@RequestMapping(value = "/petInsert", produces = "text/html; charset=utf-8")
	public String insert(HttpServletRequest request, Model model) {
		PetVO vo = new PetVO();
		vo.setP_tel((String) request.getParameter("p_tel"));
		vo.setP_name((String) request.getParameter("p_name"));
		vo.setP_birth((String) request.getParameter("p_birth"));
		vo.setP_gender((String) request.getParameter("p_gender"));
		
		if(request.getParameter("p_animal") != null) {
			vo.setP_animal((String) request.getParameter("p_animal"));
		}
		if(request.getParameter("p_a_animal") != null) {
			vo.setP_a_animal((String) request.getParameter("p_a_animal"));
		}
		
		String dbImgPath = null;
		if(request.getParameter("imageDbPath") != null) {
			dbImgPath = (String) request.getParameter("imageDbPath");
			vo.setP_pic(dbImgPath);
		}
			
		MultipartFile file = null;
		try {
			MultipartRequest multi = (MultipartRequest)request;
			file = multi.getFile("image");
		} catch(Exception e) {
			System.out.println("파일이 첨부되지 않음.");
		}
		
		if(file != null) {
			String fileName = dbImgPath;
			// 디렉토리 존재하지 않으면 생성
			common.makeDir(request, "pet");	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/pet/");
				
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
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/pet/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.pet_insert(vo);
		model.addAttribute("result", String.valueOf(succ));
		
		return "pet/result";
	}
	
	//동물정보수정
	@RequestMapping(value = "/petUpdate", produces = "text/html; charset=utf-8")
	public String update(HttpServletRequest request, Model model) {
		PetVO vo = new PetVO();
		vo.setP_num( Integer.parseInt(request.getParameter("p_num")));
		vo.setP_name((String) request.getParameter("p_name"));
		vo.setP_birth((String) request.getParameter("p_birth"));
		vo.setP_gender((String) request.getParameter("p_gender"));
		
		if(request.getParameter("p_animal") != null) {
			vo.setP_animal((String) request.getParameter("p_animal"));
		}
		if(request.getParameter("p_a_animal") != null) {
			vo.setP_a_animal((String) request.getParameter("p_a_animal"));
		}
		
		vo.setP_pic(null);
		String dbImgPath = null;
		if(request.getParameter("imageDbPath") != null) {
			//파일 신규 업로드/유지/갱신 시
			dbImgPath = (String) request.getParameter("imageDbPath");
			vo.setP_pic(dbImgPath);
		} else {
			// 존재하던 파일 삭제시 파일 삭제처리
			String delDbImgPath = request.getSession().getServletContext().getRealPath("/resources/upload/pet/" + vo.getP_num() + ".jpg");		
			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());
			
	        if(delfile.exists()) {
	            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
	            boolean deleteFile = false;
	            while(deleteFile != true){
	            	deleteFile = delfile.delete();
	            }     
	        }
		}
		
		MultipartFile file = null;
		try {
			MultipartRequest multi = (MultipartRequest)request;
			file = multi.getFile("image");
		} catch(Exception e) {
			System.out.println("파일이 첨부되지 않음.");
		}
		
		if(file != null) {
			String fileName = dbImgPath;
			// 디렉토리 존재하지 않으면 생성
			common.makeDir(request, "pet");	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/pet/");
				
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
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/pet/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.pet_update(vo);
		model.addAttribute("result", String.valueOf(succ));
		
		return "pet/result";
	}
	
	//동물정보삭제
	@RequestMapping(value="/petDelete", method = {RequestMethod.GET, RequestMethod.POST})
	public String petDelete(HttpServletRequest req, Model model){
		int p_num = Integer.parseInt(req.getParameter("p_num"));
		String p_pic = null;
		if(req.getParameter("m_pic") != null) {
			p_pic = (String)req.getParameter("p_pic");
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/pet/" + p_pic);		
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
		}
		
		int succ = service.pet_delete(p_num);
		model.addAttribute("result", String.valueOf(succ));
		return "pet/result";
	}
}