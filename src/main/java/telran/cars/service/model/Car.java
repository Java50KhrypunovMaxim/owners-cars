package telran.cars.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.cars.dto.CarColors;
import telran.cars.dto.CarDto;
import telran.cars.dto.CarState;
import jakarta.persistence.*;

@Entity
@Getter
@Table(name="cars")
@NoArgsConstructor
public class Car {
	@Id
	String number;
	@ManyToOne
	@JoinColumns({@JoinColumn(name="model_name", nullable = false),
	@JoinColumn(name="model_year", nullable = false)})
	Model model;
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=true)
	CarOwner carOwner;
	@JoinColumn(nullable=false)
	@Enumerated(EnumType.STRING)
	CarColors color;
	int kilometers;
	@JoinColumn(nullable=false)
	@Enumerated(EnumType.STRING) // value in the table will be a string (by default a number)
	CarState state;
	
	public static Car of(CarDto carDto) {
        Car car = new Car();
        car.number = carDto.number();
        car.model = new Model();
        car.model.modelYear = new ModelYear(carDto.model(), carDto.year());
        car.carOwner.id = carDto.id();
        car.color = carDto.color();
        car.kilometers = carDto.kilometers();
        car.state = carDto.state();
        return car;
    }

	public CarDto build (Car car) {
		return new CarDto(car.number, car.model.modelYear.getName(), car.model.modelYear.getYear(), car.carOwner.id,
				car.color, car.kilometers, car.state);
	}
}
	
	
