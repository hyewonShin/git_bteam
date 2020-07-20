package calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements CalendarService {
	@Autowired private CalendarDAO dao;
	
	@Override
	public List<DiagnosisDTO> anDiagnosisGet(int pet) {
		return dao.anDiagnosisGet(pet);
	}

	@Override
	public int anCalendarInsert(CalenderDTO dto) {
		return dao.anCalendarInsert(dto);
	}

	@Override
	public List<CalenderDTO> anCalenderGet(String tel) {
		return dao.anCalenderGet(tel);
	}

}