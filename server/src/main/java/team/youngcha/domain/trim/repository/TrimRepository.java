package team.youngcha.domain.trim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.trim.entity.Trim;

@Repository
public interface TrimRepository extends CrudRepository<Trim, Long> {
}
