package telran.cars.service.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.cars.dto.TradeDealDto;
@Entity
@Table(name = "trade_deals")
@Getter
public class TradeDeal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@ManyToOne
	@JoinColumn(name="car_number", nullable = false)
	@Setter
	Car car;
	@ManyToOne
	@JoinColumn(name="owner_id")
	@Setter
	CarOwner carOwner;
	@Temporal(TemporalType.DATE)
	@Setter
	LocalDate date;
}