package pet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PetDAO implements PetService{
	@Autowired private SqlSession sql;
	
	@Override
	public List<PetVO> pet_list(String m_tel) {
		return sql.selectList("pet.mapper.list", m_tel);
	}

	@Override
	public int pet_insert(PetVO vo) {
		return sql.insert("pet.mapper.insert", vo);
	}

	@Override
	public int pet_update(PetVO vo) {
		return sql.update("pet.mapper.update", vo);
	}

	@Override
	public int pet_delete(int p_num) {
		return sql.delete("pet.mapper.delete", p_num);
	}
	
}
