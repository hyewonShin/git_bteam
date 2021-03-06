package com.withpet.app;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import board.BoardVO;
import board.BoardServiceImpl;
import common.CommonService;

@Controller
public class BoardController {
	@Autowired private BoardServiceImpl service;
	@Autowired private CommonService common;
	
	//글쓰기 내용 저장하기
	@RequestMapping(value = "/anBoardInsert"
					, method = { RequestMethod.GET, RequestMethod.POST }
					, produces = "text/html; charset=utf-8")
	public String anBoardInsert(HttpServletRequest req, Model model) {
		BoardVO dto = new BoardVO();
		dto.setB_name((String) req.getParameter("name"));
		dto.setB_title((String) req.getParameter("title"));
		dto.setB_content((String) req.getParameter("content"));
		String pic = (String) req.getParameter("pic");
		dto.setB_file(pic);
		
		//실제이미지 주소를 얻기 위함
		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("image");

		// 사진 서버에 넣기
		if (file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);

			// 디렉토리 존재하지 않으면 생성
			common.makeDir(req, "board/");

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/board");

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
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/board/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
			} // if
		} // if

		int state = service.anBoardInsert(dto);
		model.addAttribute("result", String.valueOf(state));

		return "board/anBoardInsert";
	}//anBoardInsert()
	
	//게시판 목록 가져오기
	@RequestMapping(value = "/anBoardGet"
			, method = { RequestMethod.GET, RequestMethod.POST }
			, produces = "text/html; charset=utf-8")
	public String anBoardGet(HttpServletRequest req, Model model) {
		String name = (String) req.getParameter("name");
		List<BoardVO> list = service.anBoardGet(name);
		model.addAttribute("list", list);
		return "board/anBoardGet";
	}//anBoardGet()
	
}
