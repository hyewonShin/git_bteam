package community;

public interface CommunityService {
	//CRUD
	int community_insert(CommunityVO vo);
	CommunityPage freeboard_list(CommunityPage page);
	CommunityVO freeboard_detail(int id);
	void freeboard_readcnt(int id);
	int freeboard_update(CommunityVO vo);
	int freeboard_delete(int id);
}
