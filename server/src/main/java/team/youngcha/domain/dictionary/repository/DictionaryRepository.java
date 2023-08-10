package team.youngcha.domain.dictionary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.dictionary.entity.Dictionary;

@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
}
