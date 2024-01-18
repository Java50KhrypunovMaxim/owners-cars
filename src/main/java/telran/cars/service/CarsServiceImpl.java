package telran.cars.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.cars.exceptions.PersonNotFoundException;
import telran.cars.dto.*;
import telran.cars.exceptions.CarNotFoundException;
import telran.cars.exceptions.IllegalCarsStateException;
import telran.cars.exceptions.IllegalPersonsStateException;
import telran.cars.exceptions.ModelIllegalStateException;
import telran.cars.exceptions.NotFoundException;
import telran.cars.exceptions.TradeDealIllegalStateException;
import telran.cars.exceptions.ModelNotFoundException;
import telran.cars.service.model.*;
import telran.cars.repo.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsServiceImpl implements CarsService {
	final CarRepo carRepo;
	final CarOwnerRepo carOwnerRepo;
	final ModelRepo modelRepo;
	final TradeDealRepo tradeDealRepo;
	@Override
	@Transactional
	public PersonDto addPerson(PersonDto personDto) {
		if(carOwnerRepo.existsById(personDto.id())) {
			throw new IllegalPersonsStateException();
		}
		CarOwner carOwner = CarOwner.of(personDto);
		carOwnerRepo.save(carOwner);
		log.debug("person {} has been saved", personDto);
		return personDto;
	}

	@Override
	@Transactional
	public CarDto addCar(CarDto carDto) {
		if(carRepo.existsById(carDto.number())) {
			throw new IllegalCarsStateException();
		}
		Model model = modelRepo.findById(new ModelYear(carDto.model(), carDto.year()))
				.orElseThrow(() -> new ModelNotFoundException());
		Car car = Car.of(carDto);
		car.setModel(model);
		carRepo.save(car);
		log.debug("car {} has been saved", carDto);
		return carDto;
	}

	@Override
	@Transactional
	public PersonDto updatePerson(PersonDto personDto) {
		CarOwner carOwner = carOwnerRepo.findById(personDto.id())
				.orElseThrow(() -> new PersonNotFoundException());
		carOwner.setEmail(personDto.email());
		return personDto;
	}

	@Override
	@Transactional
	public PersonDto deletePerson(long id) {
		CarOwner carOwner = carOwnerRepo.findById(id)
				.orElseThrow(()-> new PersonNotFoundException());
		List<Car> carsForOwnerNull = carRepo.findByCarOwnerId(id);
		carsForOwnerNull.forEach(c -> c.setCarOwner(null));
		List<TradeDeal> dealsForOwnerNulling = tradeDealRepo.findByCarOwnerId(id);
		dealsForOwnerNulling.forEach(d -> d.setCarOwner(null));
		carOwnerRepo.deleteById(id);
		return carOwner.build();
	}

	@Override
	@Transactional
	public CarDto deleteCar(String carNumber) {
		Car car = carRepo.findById(carNumber).orElseThrow(() -> new CarNotFoundException());
		List<TradeDeal> tradeDealsForDeleting = tradeDealRepo.findByCarNumber(carNumber);
		tradeDealsForDeleting.forEach(tradeDealRepo::delete);
		CarDto res = car.build();
		carRepo.deleteById(carNumber);
		return res;
	}

	@Override
	@Transactional
	public TradeDealDto purchase(TradeDealDto tradeDealDto) {
		Car car = carRepo.findById(tradeDealDto.carNumber())
				.orElseThrow(() -> new CarNotFoundException());
		CarOwner carOwner = null;
		Long personId = tradeDealDto.personId();
		if ( personId != null) {
			log.debug("ID of new car's owner is {}", personId);
			carOwner = carOwnerRepo.findById(personId)
					.orElseThrow(() -> new PersonNotFoundException());
			if(car.getCarOwner().getId() == personId) {
				throw new TradeDealIllegalStateException();
			}
		}
		TradeDeal tradeDeal = new TradeDeal();
		tradeDeal.setCar(car);
		tradeDeal.setCarOwner(carOwner);
		tradeDeal.setDate(LocalDate.parse(tradeDealDto.date()));
		car.setCarOwner(carOwner);
		tradeDealRepo.save(tradeDeal);
		log.debug("trade: {} has been saved", tradeDealDto);
		return tradeDealDto;
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		// Not Implemented yet
		return null;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		// Not Implemented yet
		return null;
	}

	@Override
	public List<String> mostPopularModels() {
		// Not Implemented yet
		
		return null;
	}

	@Override
	@Transactional
	public ModelDto addModel(ModelDto modelDto) {
		ModelYear modelYear = new ModelYear(modelDto.getModelName(), modelDto.getModelYear());
		if(modelRepo.existsById(modelYear)) {
			throw new ModelIllegalStateException();
		}
		Model model = Model.of(modelDto);
		modelRepo.save(model);
		return modelDto;
	}

}