package telran.cars.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.EnginePowerCapacity;
import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.Model;
import telran.cars.service.model.ModelYear;
import telran.cars.service.model.TradeDeal;

public interface ModelRepo extends JpaRepository<Model, ModelYear> {
	@Query("select model.modelYear.name " +
		       "from Car cars " +
		       "where exists (select 1 from TradeDeal td where cars.number = td.car.number) " +
		       "group by cars.model.modelYear.name " +
		       "having count(*) = (select max(count)from " +
		       "(select count(*) as count from Car car " +
		       "where exists (select 1 from TradeDeal td WHERE car.number = td.car.number) " +
		       "group by car.model.modelYear.name))")
	List<String> findMostSoldModelNames();
/*************************************************************/
@Query("SELECT model.modelYear.name as name, count(*) as amount " +
	       "from Car  " +
	       "group by model.modelYear.name " +
	       "order by count(*)  desc limit :nModels")
List<ModelNameAmount> findMostPopularModelNames(int nModels);
/*************************************************************************/
@Query("select model.modelYear.name as name, count(*) as amount " +
	       "from Car c " +
	       "where c.carOwner.id in (select co.id from CarOwner co " +
	       "where co.birthDate between :birthDate1 and :birthDate2) " +
	       "group by c.model.modelYear.name " +
	       "order by count(*) desc " +
	       "limit :nModels")
List<ModelNameAmount> findPopularModelNameOwnerAges(int nModels,
		LocalDate birthDate1, LocalDate birthDate2);

}