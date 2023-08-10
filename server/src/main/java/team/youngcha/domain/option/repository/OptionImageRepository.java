package team.youngcha.domain.option.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.option.entity.OptionImage;

@Repository
public interface OptionImageRepository extends CrudRepository<OptionImage, Long> {
}
