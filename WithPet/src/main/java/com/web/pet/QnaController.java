package com.web.pet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QnaController {
	@RequestMapping("/list.qa")
	public String test() {
		
		return"qna/list";
	}
}
