package team.youngcha.domain.option.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionRelation;

@Repository
public interface OptionRelationRepository extends CrudRepository<OptionRelation, Long> {
}
