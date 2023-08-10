package team.youngcha.domain.sell.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.sell.entity.Sell;

@Repository
public interface SellRepository extends CrudRepository<Sell, Long> {
}
