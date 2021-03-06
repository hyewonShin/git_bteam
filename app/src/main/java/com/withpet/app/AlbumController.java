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

import album.AlbumServiceImpl;
import album.AlbumVO;
import common.CommonService;
import pet.PetVO;

@Controller
public class AlbumController {
	
@Autowired private AlbumServiceImpl service;
@Autowired private CommonService common;
	
	//DB에서 정보 가져오기
	@RequestMapping("/albumList")
	public String list(String a_pet, Model model) {
		model.addAttribute("list", service.album_list(a_pet));
		return "album/list";
	}
	
	//앨범에 사진 업로드
	@RequestMapping(value = "/albumInsert", produces = "text/html; charset=utf-8")
	public String insert(HttpServletRequest request, Model model) {
		AlbumVO vo = new AlbumVO();
		vo.setA_pet( Integer.parseInt(request.getParameter("a_pet")));
		vo.setA_title((String) request.getParameter("a_title"));
		if(request.getParameter("a_content") != null) {
			vo.setA_content((String) request.getParameter("a_content"));
		}
		
		System.out.println(vo.getA_pet());
		
		String dbImgPath = null;
		if(request.getParameter("imageDbPath") != null) {
			dbImgPath = (String) request.getParameter("imageDbPath");
			vo.setA_file(dbImgPath);
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
			common.makeDir(request, "album/" + vo.getA_pet());	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/upload/album/" + vo.getA_pet());
				
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
						.getRealPath("/resources/upload/album/" + vo.getA_pet() + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.album_insert(vo);
		model.addAttribute("result", String.valueOf(succ));
		
		return "album/result";
	}
	
	//앨범 삭제
	@RequestMapping(value="/albumDelete", method = {RequestMethod.GET, RequestMethod.POST})
	public String petDelete(HttpServletRequest req, Model model){
		int a_num = Integer.parseInt(req.getParameter("a_num"));
		int a_pet = Integer.parseInt(req.getParameter("a_pet"));
		String a_file = null;
		if(req.getParameter("a_file") != null) {
			a_file = (String)req.getParameter("a_file");
			String delDbImgPath = req.getSession().getServletContext()
					.getRealPath("/resources/upload/album/" + a_pet + "/" + a_file);		
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
		
		int succ = service.album_delete(a_num);
		model.addAttribute("result", String.valueOf(succ));
		return "album/result";
	}
}