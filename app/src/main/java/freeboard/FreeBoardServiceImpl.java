package freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeBoardServiceImpl implements FreeBoardService{
	@Autowired private FreeBoardDAO dao;

	@Override
	public List<FreeBoardVO> list() {
		return dao.list();
	}

	@Override
	public FreeBoardVO detail(int f_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(FreeBoardVO vo) {
		return dao.insert(vo);
	}

	@Override
	public int update(FreeBoardVO vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(int f_num) {
		return dao.delete(f_num);
	}

}
