package calendar;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarDAO implements CalendarService {
	@Autowired private SqlSession sql;

	@Override
	public List<DiagnosisDTO> anDiagnosisGet(int pet) {
		return sql.selectList("calendar.mapper.diagnosisGet", pet);
	}

	@Override
	public int anCalendarInsert(CalenderDTO dto) {
		return sql.insert("calendar.mapper.calendarInsert", dto);
	}

	@Override
	public List<CalenderDTO> anCalenderGet(String tel) {
		return sql.selectList("calendar.mapper.calenderGet", tel);
	}

}
