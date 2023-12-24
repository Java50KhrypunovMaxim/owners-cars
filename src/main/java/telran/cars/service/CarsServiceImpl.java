package telran.cars.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.cars.dto.CarDto;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.exceptions.NotFoundException;
import telran.cars.service.model.Car;
import telran.cars.service.model.CarOwner;
@Service
@Slf4j
public class CarsServiceImpl implements CarsService {
	HashMap<Long, CarOwner> owners = new HashMap<>();
	HashMap<String, Car> cars = new HashMap<>();
	HashMap<Long, List<String>> dealToCarsMap = new HashMap<>();

	public void removeAll () {
		owners.clear();
		cars.clear();
		dealToCarsMap.clear();
	}
	
	@Override
	public PersonDto addPerson (PersonDto personDto) {
		long id = personDto.id();
		if (owners.containsKey(id)) {
            throw new IllegalStateException(String.format("Person with id %d already exists", id));
        }
        CarOwner newCarOwner = new CarOwner(personDto);
        owners.put(id, newCarOwner);
        log.debug("car with number {} has been saved",id);
        return personDto;
    }

	@Override
	public CarDto addCar(CarDto carDto) {
		String number = carDto.number();
		if (cars.containsKey(number) ){
			log.error("car with number %d already exists", number);
			throw new NotFoundException(String.format("car with number %d already exists", number));
		}
		 Car newCar = new Car(carDto);
		 cars.put(number, newCar);
		 log.debug("car with number {} has been saved", number);
		 return carDto;
	}

	@Override
	public PersonDto updatePerson(PersonDto personDto) {
		long id = personDto.id();
		if (!owners.containsKey(id)) {
			log.error("person with id %d is not in the database", id);
			throw new IllegalStateException(String.format("person with id %d is not in the database", id));}
		 owners.remove(id);
		 CarOwner newCarOwner = new CarOwner(personDto);
		 owners.put(id, newCarOwner);
		 log.debug("person with id {} has been update", id);
		 return personDto;

		
	}

	@Override
	public PersonDto deletePerson(long id) {
		if (!owners.containsKey(id)) {
		log.error("person with id %d is not in the database", id);
		throw new IllegalStateException(String.format("person with id %d is not in the database", id));}
		CarOwner deleteCarOwner = owners.get(id);
		owners.remove(id);
		log.debug("person with id {} has been delete", id);
		return deleteCarOwner.build();
	}

	@Override
	public CarDto deleteCar(String carNumber) {
		
		if (!cars.containsKey(carNumber)) {
			log.error("car with number %s is not in the database", carNumber);
			throw new IllegalStateException(String.format("car with number %d is not in the database", carNumber));}
			Car deleteCar = cars.get(carNumber);
			cars.remove(carNumber);
			log.debug("person with id {} has been delete", carNumber);
			return deleteCar.build();
	}

	@Override
	public TradeDealDto purchase(TradeDealDto tradeDeal) {
		Long id = tradeDeal.personId();
	    if (!owners.containsKey(id)) {
	        log.error("Person with id %d is not in the database", id);
	        throw new IllegalStateException(String.format("Person with id %d is not in the database", id));
	    }
	    
	    String carNumber = tradeDeal.carNumber();
	    Car car = cars.get(carNumber);

	    if (car == null) {
	        log.error("Car with number %s is not in the database", carNumber);
	        throw new IllegalStateException(String.format("Car with number %s is not in the database", carNumber));
	    }

	    CarOwner owner = owners.get(id);
	    car.setOwner(owner);
	    owner.addCar(car);
	    log.debug("Deal has been added");

	    return tradeDeal;
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		CarOwner owner = owners.get(id);
		List<CarDto> ownerCars = new ArrayList<>();

		if (owner != null) {
			for (Car car : owner.getCars()) {
				ownerCars.add(car.build());
			}
		} else {
			log.error("Owner with id %d is not in the database", id);
			throw new IllegalArgumentException(String.format("Owner with id %d is not in the database", id));
		}

		return ownerCars;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		 Car car = cars.get(carNumber);

		    if (car != null && car.getOwner() != null) {
		        return car.getOwner().build();
		    } else {
		        log.error("Car with number %s or its owner not found in the database", carNumber);
		        throw new IllegalArgumentException(String.format("Car with number %s or its owner not found in the database", carNumber));
		    }
	}

}
