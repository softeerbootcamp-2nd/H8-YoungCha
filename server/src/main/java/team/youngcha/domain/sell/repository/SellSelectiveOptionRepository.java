package team.youngcha.domain.sell.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.sell.entity.SellSelectiveOption;

@Repository
public interface SellSelectiveOptionRepository extends CrudRepository<SellSelectiveOption, Long> {
}
