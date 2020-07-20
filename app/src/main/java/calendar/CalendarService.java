package calendar;

import java.util.List;

public interface CalendarService {
	//검진기록가져오기
	List<DiagnosisDTO> anDiagnosisGet(int pet);
	//캘린더 일정 넣기
	int anCalendarInsert(CalenderDTO dto);
	//캘린더 일정 가져오기
	List<CalenderDTO> anCalenderGet(String tel);
}
