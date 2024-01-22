package telran.cars.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.EnginePowerCapacity;
import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.Model;
import telran.cars.service.model.ModelYear;
import telran.cars.service.model.TradeDeal;

public interface ModelRepo extends JpaRepository<Model, ModelYear> {
	@Query(value = "select model_name from cars join trade_deals td"
			+ " on cars.car_number=td.car_number group by model_name " + " having count(*) = (select max(count) from "
			+ "(select count(*) as count from cars join trade_deals "
			+ " on cars.car_number = trade_deals.car_number group by model_name)) ", nativeQuery = true) // just SQL
																											// query
	List<String> findMostSoldModelNames();

	@Query(value = "select c.model_name as name, count(*) as amount "
			+ "from cars c group by c.model_name order by count(*) desc limit :nModels", nativeQuery = true)
	List<ModelNameAmount> findMostPopularModelNames(int nModels);

	boolean existsByModelYearName(String modelName);

	@Query(value = "select color " +
	        "from cars c " +
	        "join models m on c.model_name = m.model_name and c.model_year = m.model_year " +
	        "where c.model_name = :modelName " +
	        "group by color " +
	        "order by count(color) desc limit 1", nativeQuery = true)
	String findOneMostPopularColorModel(String modelName);
	
	@Query(value = "SELECT MIN(engine_power) AS enginePower, MIN(engine_capacity) AS engineCapacity " +
	        "FROM car_owners " +
	        "JOIN cars ON cars.owner_id = car_owners.id " +
	        "JOIN models ON cars.model_name = models.model_name AND cars.model_year = models.model_year " +
	        "WHERE TIMESTAMPDIFF(YEAR, car_owners.birth_date, CURRENT_DATE()) BETWEEN :ageFrom AND :ageTo", nativeQuery = true)
	EnginePowerCapacity findMinEnginePowerCapacityByOwnerAges(int ageFrom, int ageTo);
}