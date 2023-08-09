package team.youngcha.domain.category.repository;

import org.springframework.data.repository.CrudRepository;
import team.youngcha.domain.category.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
