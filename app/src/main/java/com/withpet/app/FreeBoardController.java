package com.withpet.app;


import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import common.CommonService;
import freeboard.FreeBoardServiceImpl;
import freeboard.FreeBoardVO;

@Controller
public class FreeBoardController {
	@Autowired private FreeBoardServiceImpl service;
	@Autowired private CommonService common;
	
	//게시판 목록 조회
	@RequestMapping("/freeBoardList")
	public String list(Model model){
		model.addAttribute("list", service.list()); 
		return "freeboard/list";
	}
	
	//게시물 상세 정보
	@RequestMapping("/freeBoardDetail")
	public String detail() {
		return "";
	}
	
	//게시물 등록
	@RequestMapping("/freeBoardInsert")
	public String insert(HttpServletRequest request, Model model) {
		FreeBoardVO vo = new FreeBoardVO();
		vo.setF_tel((String)request.getParameter("f_tel"));
		vo.setF_title((String)request.getParameter("f_title"));
		vo.setF_content((String)request.getParameter("f_content"));
		
		String dbImgPath = null;
		if(request.getParameter("imageDbPath") != null) {
			dbImgPath = (String) request.getParameter("imageDbPath");
			vo.setF_file(dbImgPath);
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
			common.makeDir(request, "freeboard");	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/freeboard/");
				
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
						.getRealPath("/resources/upload/freeboard/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.insert(vo);
		model.addAttribute("result", String.valueOf(succ));
		
		return "freeboard/result";
	}
	
	//게시물 삭제
	@RequestMapping("/freeBoardDelete")
	public String delete(HttpServletRequest req, Model model) {
		int f_num = Integer.parseInt(req.getParameter("f_num"));
		String f_file = null;
		if(req.getParameter("f_file") != null) {
			f_file = (String)req.getParameter("f_file");
			String delDbImgPath = req.getSession().getServletContext()
					.getRealPath("/resources/upload/freeboard/" + f_file);		
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
		
		int succ = service.delete(f_num);
		model.addAttribute("result", String.valueOf(succ));
		return "freeboard/result";
	}
	
	//게시물 수정
	@RequestMapping("/freeBoardUpdate")
	public String update(HttpServletRequest request, Model model) {
		FreeBoardVO vo = new FreeBoardVO();
		vo.setF_num(Integer.parseInt((String)request.getParameter("f_num")));
		vo.setF_title((String) request.getParameter("f_title"));
		vo.setF_content((String) request.getParameter("f_content"));
		
		if((String) request.getParameter("f_file") != null) {
			vo.setF_file((String) request.getParameter("f_file"));
		}
		
		String dbImgPath = null;
		if(request.getParameter("imageDbPath") != null) {
			//파일 신규 업로드/유지/갱신 시
			dbImgPath = (String) request.getParameter("imageDbPath");
			vo.setF_file(dbImgPath);
		} else {
			if(vo.getF_file() != null) {
				// 존재하던 파일 삭제시 파일 삭제처리
				String delDbImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/freeboard/" + vo.getF_file());
				vo.setF_file(null);
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
			common.makeDir(request, "freeboard");	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/freeboard/");
				
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
						.getRealPath("/resources/upload/freeboard/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.update(vo);
		model.addAttribute("result", String.valueOf(succ));
		
		return "freeboard/result";
	}
	

}
