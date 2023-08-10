package team.youngcha.domain.estimate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.estimate.entity.Estimate;

@Repository
public interface EstimateRepository extends CrudRepository<Estimate, Long> {
}
