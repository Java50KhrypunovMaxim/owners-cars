package telran.cars.controller;
import static telran.cars.api.ValidationConstants.*;
import jakarta.validation.constraints.*;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.cars.dto.CarDto;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.service.CarsService;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor

public class CarsController {
	final CarsService carsService;
	@PostMapping
	CarDto addCar(@RequestBody @Valid CarDto carDto) {
		//annotation @RequstBody informs Spring about conversion of JSON inside a request to the given parameter
		return carsService.addCar(carDto);
	}
	@PostMapping("person")
	PersonDto addPerson(@RequestBody @Valid PersonDto personDto) {
		
		return carsService.addPerson(personDto);
	}
	@PutMapping("person")
	PersonDto updatePerson(@RequestBody @Valid PersonDto personDto) {
		return carsService.updatePerson(personDto);
	}
	@PutMapping("trade")
	TradeDealDto purchase(@RequestBody @Valid TradeDealDto tradeDealDto) {
		
		return carsService.purchase(tradeDealDto);
	}
	@DeleteMapping("person/{id}")
	PersonDto deletePerson(@PathVariable(name="id") @NotNull(message=MISSING_PERSON_ID_MESSAGE)
	@Min(value=MIN_PERSON_ID_VALUE, message=WRONG_MIN_PERSON_ID_VALUE) @Max(value=MAX_PERSON_ID_VALUE,
	message=WRONG_MAX_PERSON_ID_VALUE )long id) {
		return carsService.deletePerson(id);
	}
	@DeleteMapping("{carNumber}")
	CarDto deleteCar(@PathVariable(name="carNumber") @NotEmpty(message=MISSING_CAR_NUMBER_MESSAGE)
	@Pattern(regexp = CAR_NUMBER_REGEXP, message=WRONG_CAR_NUMBER_MESSAGE) String carNumber) {
		
		return carsService.deleteCar(carNumber);
		
	}
	@GetMapping("person/{id}")
	List<CarDto> getOwnerCars(@PathVariable @NotNull(message=MISSING_PERSON_ID_MESSAGE)
	@Min(value=MIN_PERSON_ID_VALUE, message=WRONG_MIN_PERSON_ID_VALUE) @Max(value=MAX_PERSON_ID_VALUE,
	message=WRONG_MAX_PERSON_ID_VALUE )long id) {
		return carsService.getOwnerCars(id);
	}
	@GetMapping("{carNumber}")
	PersonDto getCarOwner(@PathVariable  @NotEmpty(message=MISSING_CAR_NUMBER_MESSAGE)
	@Pattern(regexp = CAR_NUMBER_REGEXP, message=WRONG_CAR_NUMBER_MESSAGE) String carNumber) {
		return carsService.getCarOwner(carNumber);
	}
}