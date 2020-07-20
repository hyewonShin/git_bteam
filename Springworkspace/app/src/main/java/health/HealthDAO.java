package health;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HealthDAO implements HealthService{
	@Autowired private SqlSession sql;
	
	@Override
	public int anHealth(HealthDTO dto) {
		return sql.insert("health.mapper.anHealth", dto);
	}

	@Override
	public List<HealthDTO> anHealthGet(int pet) {
		return sql.selectList("health.mapper.anHealthGet", pet);
	}

}
