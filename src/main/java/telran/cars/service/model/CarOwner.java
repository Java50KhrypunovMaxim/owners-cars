package telran.cars.service.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import telran.cars.dto.PersonDto;
@Getter
public class CarOwner {
	Long id;
	String name;
	LocalDate birthDate;
	String email;
	List<Car> cars = new ArrayList<>();
	public CarOwner(PersonDto personDto) {
		id = personDto.id();
		name = personDto.name();
		birthDate = LocalDate.parse(personDto.birthDate());
		email = personDto.email();
	}
	public PersonDto build() {
		return new PersonDto(id, name, birthDate.toString(), email);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Map<String, Integer> groupCarModelsCount() {
	    return cars.stream()
	            .collect(Collectors.toMap(car -> car.model, car -> 1, Integer::sum));
	}
    }
