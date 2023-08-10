package team.youngcha.domain.category.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.category.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
