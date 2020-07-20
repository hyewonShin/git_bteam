package pet;

import java.util.List;

public interface PetService {
	//동물 정보 리스트 가져오기
	List<PetVO> pet_list(String m_tel);
	//동물 정보 추가하기
	int pet_insert(PetVO vo);
	//동물 정보 수정하기
	int pet_update(PetVO vo);
	//동물 정보 삭제하기
	int pet_delete(int p_num);
}
