package album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {
	@Autowired private AlbumDAO dao;
	
	@Override
	public List<AlbumVO> album_list(String a_pet) {
		return dao.album_list(a_pet);
	}

	@Override
	public AlbumVO album_detail(int a_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int album_insert(AlbumVO vo) {
		return dao.album_insert(vo);
	}

	@Override
	public int album_update(int a_num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int album_delete(int a_num) {
		return dao.album_delete(a_num);
	}

}
