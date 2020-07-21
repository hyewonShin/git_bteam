package freeboard;

import java.util.List;

public interface FreeBoardService {
	//게시판 목록 조회
	List<FreeBoardVO> list();
	//게시물 상세 정보
	FreeBoardVO detail(int f_num);
	//게시물 등록
	int insert(FreeBoardVO vo);
	//게시물 수정
	int update(FreeBoardVO vo);
	//게시물 삭제
	int delete(int f_num);
}
