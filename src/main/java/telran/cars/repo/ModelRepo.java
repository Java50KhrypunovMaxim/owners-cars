package telran.cars.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.cars.service.model.Model;
import telran.cars.service.model.ModelYear;
import telran.cars.service.model.TradeDeal;

public interface ModelRepo extends JpaRepository<Model, ModelYear> {

}

