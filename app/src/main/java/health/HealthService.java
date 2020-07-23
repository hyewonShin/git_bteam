package health;

import java.util.List;

public interface HealthService {
	//동물운동정보 저장
	int anHealth(HealthVO dto);
	//동물운동정보 가져오기
	List<HealthVO> anHealthGet(int pet);
}