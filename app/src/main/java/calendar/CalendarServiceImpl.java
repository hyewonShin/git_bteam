package calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements CalendarService {
	@Autowired private CalendarDAO dao;
	
	@Override
	public List<DiagnosisVO> anDiagnosisGet(int pet) {
		return dao.anDiagnosisGet(pet);
	}

	@Override
	public int anCalendarInsert(CalendarVO vo) {
		return dao.anCalendarInsert(vo);
	}

	@Override
	public List<CalendarVO> anCalenderGet(String tel) {
		return dao.anCalenderGet(tel);
	}

	@Override
	public int anCalenderUpdate(CalendarVO vo) {
		return dao.anCalenderUpdate(vo);
	}

	@Override
	public int anCalendarDelete(CalendarVO vo) {
		return dao.anCalendarDelete(vo);
	}

}
