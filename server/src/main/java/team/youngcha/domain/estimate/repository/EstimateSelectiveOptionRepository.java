package team.youngcha.domain.estimate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.EstimateSelectiveOption;

@Repository
public interface EstimateSelectiveOptionRepository extends CrudRepository<EstimateSelectiveOption, Long> {
}
