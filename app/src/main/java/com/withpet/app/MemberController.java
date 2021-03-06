package com.withpet.app;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;

@Controller
public class MemberController {
	@Autowired private MemberServiceImpl service;
	@Autowired private CommonService common;
	
	//로그인 관련
	
	//로그인요청
	@RequestMapping(value="/anLogin", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest request, Model model) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("m_email", request.getParameter("m_email"));
		map.put("m_pw", request.getParameter("m_pw"));		
		MemberVO vo = service.member_login(map);
		
		model.addAttribute("vo", vo);
		return "member/login";
	}
	
	//SNS 로 로그인
	@RequestMapping(value="/anKakaoLogin", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
	public String anSnsLogin(HttpServletRequest request, Model model) {
		String snsId = request.getParameter("kakaoId");	
		MemberVO vo = service.member_sns_login(snsId);
		
		model.addAttribute("vo", vo);
		return "member/login";
	}
	
	//이메일 찾기
	@RequestMapping(value="/anFindEmail", 
			method = {RequestMethod.GET, RequestMethod.POST}, 
			produces = "text/html; charset=utf-8")
	public String anFindEmail(HttpServletRequest req, Model model){
		String m_tel = (String) req.getParameter("m_tel");
		String m_email = service.member_find_email(m_tel);
		model.addAttribute("result", m_email);
		return "member/result";
	}
	
	//비밀번호 찾기
	@RequestMapping(value="/anFindPw", 
			method = {RequestMethod.GET, RequestMethod.POST}, 
			produces = "text/html; charset=utf-8")
	public String anFindPw(HttpServletRequest request, Model model){
		//임시 비밀번호 생성
		String m_pw = "";
		for (int i = 1; i <= 6; i++) {
		      char ch = (char) ((Math.random() * 26) + 65);
		      m_pw += ch;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("m_email", request.getParameter("m_tel"));
		map.put("m_pw", m_pw);		
		service.member_find_pw(map);
		model.addAttribute("result", m_pw);
		
		return "member/result";
	}
	
	//회원가입 관련
	
	//회원가입처리 요청
	@RequestMapping(value="/anJoin", produces = "text/html; charset=utf-8")
	public String join(HttpServletRequest req, Model model) {
		String m_tel = (String) req.getParameter("m_tel");
		String m_email = (String) req.getParameter("m_email");
		String m_name = (String) req.getParameter("m_name");
		String m_pw = (String) req.getParameter("m_pw");
		
		String m_kakao = null, m_naver = null;
		if (req.getParameter("m_kakao") != null) {
			m_kakao = (String) req.getParameter("m_kakao");
		}
		if (req.getParameter("m_naver") != null) {
			m_naver = (String) req.getParameter("m_naver");
		}
		
		int succ = -1;
		MemberVO vo = new MemberVO(m_tel, m_email, m_name, m_pw, m_kakao, m_naver);
		succ = service.member_insert(vo);
		model.addAttribute("result", String.valueOf(succ));
		return "member/result";
	}
	
	//핸드폰 인증번호 전송
	@RequestMapping(value="/anSmsMulti", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
	public String anSmsMulti(HttpServletRequest req, Model model){
		String m_tel = (String) req.getParameter("m_tel");
		String randomNum = (String) req.getParameter("randomNum");
		
		model.addAttribute("m_tel", m_tel);
		model.addAttribute("randomNum", randomNum);
		
		//SMS로 인증번호 전송
		int succ = common.sendSms(m_tel, randomNum);
		model.addAttribute("result", String.valueOf(succ));
		return "member/result";
	}
	
	//회원 가입 전 중복 확인
	@RequestMapping(value="/anJoinSelect", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
	public String anJoinSelect(HttpServletRequest req, Model model){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", req.getParameter("type"));
		map.put("check", req.getParameter("check"));
		
		int succ = service.member_join_select(map);
		model.addAttribute("result", String.valueOf(succ));
		
		return "member/result";
	}
	
	//회원 정보 수정/탈퇴
	
	//회원 정보 수정
	@RequestMapping(value="/anUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
	public String anUpdateMulti(HttpServletRequest req, Model model){
		MemberVO vo = new MemberVO();
		vo.setM_tel((String) req.getParameter("m_tel"));
		vo.setM_email((String) req.getParameter("m_email"));
		vo.setM_name((String) req.getParameter("m_name"));
		vo.setM_pw((String) req.getParameter("m_pw"));
		
		vo.setM_pic(null);
		String dbImgPath = null;
		if(req.getParameter("imageDbPath") != null) {
			// 파일 신규 업로드/유지/갱신 시
			dbImgPath = (String) req.getParameter("imageDbPath");
			vo.setM_pic(dbImgPath);
		} else {
			// 존재하던 파일 삭제시 파일 삭제처리
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/member/" + vo.getM_tel() + ".jpg");		
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
			MultipartRequest multi = (MultipartRequest)req;
			file = multi.getFile("image");
		} catch(Exception e) {
			System.out.println("파일이 첨부되지 않음.");
		}
			
		if(file != null) {
			String fileName = dbImgPath;
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/upload/member/");
				
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
						.getRealPath("/resources/upload/member/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			}			
		}
		
		int succ = service.member_update(vo);
		model.addAttribute("result", String.valueOf(succ));
		return "member/result";
	}
	
	//파일 디렉토리 생성
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources/upload/member"));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
	
	//SNS 계정 추가 연동
	@RequestMapping(value="/anSnsUpdate", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html; charset=utf-8")
	public String anSnsUpdate(HttpServletRequest req, Model model){
		String m_kakao = null;
		String m_naver = null;
				
		if(req.getParameter("m_kakao") != null) {
			m_kakao = (String) req.getParameter("m_kakao");
		}
		if(req.getParameter("m_naver") != null) {
			m_naver = (String) req.getParameter("m_naver");
		}
		
		MemberVO vo = new MemberVO();
		vo.setM_tel((String) req.getParameter("m_tel"));
		vo.setM_kakao(m_kakao);
		vo.setM_naver(m_naver);
		
		int succ = service.member_sns_update(vo);
		model.addAttribute("result", String.valueOf(succ));
		return "member/result";
	}
	
	//회원 탈퇴
	@RequestMapping(value="/anDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public String anDeleteMulti(HttpServletRequest req, Model model){
		String m_tel = (String)req.getParameter("m_tel");
		String m_pic = null;
		if(req.getParameter("m_pic") != null) {
			m_pic = (String)req.getParameter("m_pic");
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/member/" + m_pic);		
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
		
		int succ = service.member_delete(m_tel);
		model.addAttribute("result", String.valueOf(succ));
		return "member/result";
	}
	
}
