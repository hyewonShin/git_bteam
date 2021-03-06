package album;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDAO implements AlbumService{
	@Autowired private SqlSession sql;
	
	@Override
	public List<AlbumVO> album_list(String a_pet) {
		return sql.selectList("album.mapper.list", a_pet);
	}

	@Override
	public AlbumVO album_detail(int a_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int album_insert(AlbumVO vo) {
		return sql.insert("album.mapper.insert", vo);
	}

	@Override
	public int album_update(int a_num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int album_delete(int a_num) {
		return sql.delete("album.mapper.delete", a_num);
	}

}
