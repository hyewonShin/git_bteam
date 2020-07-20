package pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService{
	@Autowired private PetDAO dao;
	
	@Override
	public List<PetVO> pet_list(String m_tel) {
		return dao.pet_list(m_tel);
	}

	@Override
	public int pet_insert(PetVO vo) {
		return dao.pet_insert(vo);
	}

	@Override
	public int pet_update(PetVO vo) {
		return dao.pet_update(vo);
	}

	@Override
	public int pet_delete(int p_num) {
		return dao.pet_delete(p_num);
	}
	
}
