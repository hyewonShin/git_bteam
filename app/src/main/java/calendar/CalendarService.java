package calendar;

import java.util.List;

public interface CalendarService {
	//검진기록가져오기
	List<DiagnosisDTO> anDiagnosisGet(int pet);
	//캘린더 일정 넣기
	int anCalendarInsert(CalenderVO vo);
	//캘린더 일정 가져오기
	List<CalenderVO> anCalenderGet(String tel);
	//캘린더 일정 수정
	int anCalenderUpdate(CalenderVO vo);
	//캘린더 일정 삭제
	int anCalendarDelete(CalenderVO vo);
}
