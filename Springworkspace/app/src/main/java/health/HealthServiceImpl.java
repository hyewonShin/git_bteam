package health;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService{
	@Autowired private HealthDAO dao;
	
	@Override
	public int anHealth(HealthDTO dto) {
		return dao.anHealth(dto);
	}

	@Override
	public List<HealthDTO> anHealthGet(int pet) {
		return dao.anHealthGet(pet);
	}

}
