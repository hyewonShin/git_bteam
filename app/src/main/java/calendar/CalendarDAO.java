package calendar;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarDAO implements CalendarService {
	@Autowired private SqlSession sql;

	@Override
	public List<DiagnosisVO> anDiagnosisGet(int pet) {
		return sql.selectList("calendar.mapper.diagnosisGet", pet);
	}

	@Override
	public int anCalendarInsert(CalendarVO dto) {
		return sql.insert("calendar.mapper.calendarInsert", dto);
	}

	@Override
	public List<CalendarVO> anCalenderGet(String tel) {
		return sql.selectList("calendar.mapper.calendarGet", tel);
	}

	@Override
	public int anCalenderUpdate(CalendarVO vo) {
		return sql.update("calendar.mapper.calendarUpdate", vo);
	}

	@Override
	public int anCalendarDelete(CalendarVO vo) {
		return sql.delete("calendar.mapper.calendarDelete", vo);
	}

}
