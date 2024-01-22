package telran.cars.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.*;

public interface CarRepo extends JpaRepository<Car, String> {
List<Car> findByCarOwnerId(long id);

@Query(value = "select m.model_name AS name, count(*) as amount " +
        "from cars c " +
        "join car_owners co ON c.owner_id = co.id " +
        "join models m ON c.model_name = m.model_name AND c.model_year = m.model_year " +
        "where extract(YEAR FROM CURRENT_DATE()) - extract(YEAR FROM co.birth_date) between :ageFrom and :ageTo " +
        "group by m.model_name " +
        "order by amount desc limit :nModels", nativeQuery = true)
List<ModelNameAmount> findMostPopularModelNameByOwnerAges(int nModels, int ageFrom, int ageTo);
}
