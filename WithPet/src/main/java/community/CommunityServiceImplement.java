package community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImplement implements CommunityService{
	@Autowired private CommunityDAO dao;
	@Override
	public int community_insert(CommunityVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommunityPage freeboard_list(CommunityPage page) {
		
		return dao.freeboard_list(page);
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
