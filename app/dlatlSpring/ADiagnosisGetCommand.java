package com.csslect.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csslect.app.dao.ANDao;
import com.csslect.app.dto.DiagnosisDTO;
import com.csslect.app.dto.PetDTO;

public class ADiagnosisGetCommand implements ACommand {

	@Override
	public void execute(Model model) {	
		int pet = Integer.parseInt(((String)model.asMap().get("pet")));
		
		System.out.println("ADiagnosisGetCommand : " + pet);
		
		ANDao adao = new ANDao();
		ArrayList<DiagnosisDTO> adto = adao.anDiagnosisGet(pet);
		
		model.addAttribute("anDiagnosisGet", adto); 
	
	}//execute

}//class
