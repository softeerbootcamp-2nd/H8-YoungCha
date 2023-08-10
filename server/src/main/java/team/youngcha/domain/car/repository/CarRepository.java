package team.youngcha.domain.car.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.youngcha.domain.car.entity.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
