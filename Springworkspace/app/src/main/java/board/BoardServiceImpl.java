package board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired private BoardDAO dao;
	
	@Override
	public int anBoardInsert(BoardDTO dto) {
		return dao.anBoardInsert(dto);
	}

	@Override
	public List<BoardDTO> anBoardGet(String name) {
		return dao.anBoardGet(name);
	}

}
