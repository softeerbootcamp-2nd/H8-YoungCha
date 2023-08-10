package team.youngcha.domain.keyword.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.keyword.entity.Keyword;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Long> {
}
