package team.youngcha.domain.trim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.trim.entity.TrimOption;

@Repository
public interface TrimOptionRepository extends CrudRepository<TrimOption, Long> {
}
