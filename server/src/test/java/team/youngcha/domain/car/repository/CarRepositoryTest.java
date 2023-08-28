package team.youngcha.domain.car.repository;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import team.youngcha.domain.car.entity.Car;

import java.util.List;
import java.util.Optional;

@JdbcTest
@ExtendWith(SoftAssertionsExtension.class)
class CarRepositoryTest {

    @InjectSoftAssertions
    SoftAssertions softAssertions;

    JdbcTemplate jdbcTemplate;
    CarRepository carRepository;

    @Autowired
    public CarRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.carRepository = new CarRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("자동차 id로 Car 엔티티를 조회할 수 있어야 한다")
    void findByValidId() {
        //given
        jdbcTemplate.execute("INSERT INTO car VALUES (1, '팰리세이드', 'Palisade')");

        Long carId = 1L;
        String koreanName = "팰리세이드";
        String englishName = "Palisade";

        //when
        Car car = carRepository.findById(carId).orElseThrow();

        //then
        softAssertions.assertThat(car).isNotNull();
        softAssertions.assertThat(car.getId()).isEqualTo(carId);
        softAssertions.assertThat(car.getKoreanName()).isEqualTo(koreanName);
        softAssertions.assertThat(car.getEnglishName()).isEqualTo(englishName);
    }

    @Test
    @DisplayName("존재하지 않는 자동차 id로 조회 시 optional-null을 반환한다")
    void findByInvalidId() {
        //given
        Long invalidCarId = -1L;

        //when
        Optional<Car> optionalCar = carRepository.findById(invalidCarId);

        //then
        softAssertions.assertThat(optionalCar.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("모든 자동차 목록을 조회한다")
    void findAll() {
        //given
        jdbcTemplate.execute("INSERT INTO car VALUES " +
                "(1, '팰리세이드', 'Palisade'), " +
                "(2, '아반떼', 'Avante'), " +
                "(3, '소나타', 'Sonata')");

        List<Car> expected = List.of(
                new Car(1L, "팰리세이드", "Palisade"),
                new Car(2L, "아반떼", "Avante"),
                new Car(3L, "소나타", "Sonata")
        );

        //when
        List<Car> actual = carRepository.findAll();

        //then
        softAssertions.assertThat(actual.size()).isEqualTo(3);

        softAssertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }
}
