package telran.cars.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.cars.dto.*;
import telran.cars.exceptions.NotFoundException;
import telran.cars.service.model.*;
@Slf4j
@Service("carsService")
@Scope("prototype")
public class CarsServiceImpl implements CarsService {@Override
	public PersonDto addPerson(PersonDto personDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarDto addCar(CarDto carDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto updatePerson(PersonDto personDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto deletePerson(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarDto deleteCar(String carNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradeDealDto purchase(TradeDealDto tradeDeal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> mostPopularModels() {
		// TODO Auto-generated method stub
		return null;
	}

}