package board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO implements BoardService{
	@Autowired private SqlSession sql;

	@Override
	public int anBoardInsert(BoardVO dto) {
		return sql.insert("board.mapper.insert", dto);
	}

	@Override
	public List<BoardVO> anBoardGet(String name) {
		return sql.selectList("board.mapper.get", name);
	}

}
