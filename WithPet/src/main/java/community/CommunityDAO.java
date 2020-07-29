package community;

import java.util.List;

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
	public List<CommunityVO> freeboard_list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommunityVO freeboard_detail(int b_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int freeboard_update(CommunityVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int freeboard_delete(int b_num) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
