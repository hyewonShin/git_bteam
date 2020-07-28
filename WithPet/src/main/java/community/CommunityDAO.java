package community;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityDAO implements CommunityService{
	
	@Autowired private SqlSession sql;
	
	@Override
	public int community_insert(CommunityVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommunityPage freeboard_list(CommunityPage page) {
		page.setTotalList((Integer)sql.selectOne("board.mapper.total", page));
		/* page.setList(sql.selectList("board.mapper.list", page)); */
		
		return page;
	}

	@Override
	public CommunityVO freeboard_detail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freeboard_readcnt(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int freeboard_update(CommunityVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int freeboard_delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
