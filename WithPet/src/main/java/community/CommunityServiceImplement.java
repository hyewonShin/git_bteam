package community;

import java.util.List;

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
