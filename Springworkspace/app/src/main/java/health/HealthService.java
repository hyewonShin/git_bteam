package health;

import java.util.List;

public interface HealthService {
	//동물운동정보 저장
	int anHealth(HealthDTO dto);
	//동물운동정보 가져오기
	List<HealthDTO> anHealthGet(int pet);
}