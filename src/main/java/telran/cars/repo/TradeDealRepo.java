package telran.cars.repo;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.service.model.*;

public interface TradeDealRepo extends JpaRepository<TradeDeal, Long> {
List<TradeDeal> findByCarNumber(String carNumber);

List<TradeDeal> findByCarOwnerId(long id);

long countByCarModelModelYearNameAndDateBetween(String modelName, LocalDate startDate, LocalDate endDate);

}