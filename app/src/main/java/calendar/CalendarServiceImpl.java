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
	public int anCalendarInsert(CalenderVO vo) {
		return dao.anCalendarInsert(vo);
	}

	@Override
	public List<CalenderVO> anCalenderGet(String tel) {
		return dao.anCalenderGet(tel);
	}

	@Override
	public int anCalenderUpdate(CalenderVO vo) {
		return dao.anCalenderUpdate(vo);
	}

	@Override
	public int anCalendarDelete(CalenderVO vo) {
		return dao.anCalendarDelete(vo);
	}

}
