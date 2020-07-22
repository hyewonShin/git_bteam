package freeboard;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FreeBoardDAO implements FreeBoardService{
	@Autowired private SqlSession sql;

	@Override
	public List<FreeBoardVO> list() {
		return sql.selectList("freeboard.mapper.list");
	}

	@Override
	public FreeBoardVO detail(int f_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(FreeBoardVO vo) {
		return sql.insert("freeboard.mapper.insert", vo);
	}

	@Override
	public int update(FreeBoardVO vo) {
		return sql.update("freeboard.mapper.update", vo);
	}

	@Override
	public int delete(int f_num) {
		return sql.delete("freeboard.mapper.delete", f_num);
	}

}
