package board;

import java.util.List;

public interface BoardService{
	//글쓰기 내용 저장하기
	int anBoardInsert(BoardDTO dto);
	//게시판 목록 가져오기(JsonArray)
	List<BoardDTO> anBoardGet(String name);
}