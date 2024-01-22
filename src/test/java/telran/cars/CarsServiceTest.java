package telran.cars;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import telran.cars.exceptions.PersonNotFoundException;
import telran.cars.dto.*;
import telran.cars.exceptions.CarNotFoundException;
import telran.cars.exceptions.IllegalCarsStateException;
import telran.cars.exceptions.IllegalPersonsStateException;
import telran.cars.exceptions.ModelIllegalStateException;
import telran.cars.exceptions.ModelNotFoundException;
import telran.cars.exceptions.NotFoundException;
import telran.cars.exceptions.TradeDealIllegalStateException;
import telran.cars.repo.CarOwnerRepo;
import telran.cars.repo.CarRepo;
import telran.cars.repo.TradeDealRepo;
import telran.cars.service.CarsService;
import telran.cars.service.model.CarOwner;
import telran.cars.service.model.TradeDeal;

@SpringBootTest
@Sql(scripts = { "classpath:test_data.sql" })
class CarsServiceTest {
	private static final String MODEL1 = "model1";
	private static final String MODEL2 = "model2";
	private static final String MODEL3 = "model3";
	private static final String MODEL4 = "model4";
	private static final String CAR_NUMBER_1 = "111-11-111";
	private static final String CAR_NUMBER_2 = "222-11-111";
	private static final String CAR_NUMBER_3 = "333-11-111";
	private static final String CAR_NUMBER_4 = "444-44-444";
	private static final String CAR_NUMBER_5 = "555-55-555";
	private static final Long PERSON_ID_1 = 123l;
	private static final String NAME1 = "name1";
	private static final String BIRTH_DATE_1 = "1990-10-10";
	private static final String EMAIL1 = "name1@gmail.com";
	private static final Long PERSON_ID_2 = 124l;
	private static final Long PERSON_ID_3 = 125l;
	private static final String NAME2 = "name2";
	private static final String NAME3 = "name3";
	private static final String BIRTH_DATE_2 = "2000-10-10";
	private static final String BIRTH_DATE_3 = "2005-10-10";
	private static final String EMAIL3 = "name3@gmail.com";
	private static final String EMAIL2 = "name2@gmail.com";
	private static final Long PERSON_ID_NOT_EXISTS = 1111111111L;

	private static final String NEW_EMAIL = "name1@tel-ran.co.il";
	private static final String DATE_TRADE_DEAL_1 = "2024-01-01";
	@Autowired
	CarOwnerRepo carOwnerRepo;
	@Autowired
	CarRepo carRepo;
	@Autowired
	TradeDealRepo tradeDealRepo;

	CarDto car1 = new CarDto(CAR_NUMBER_1, MODEL1, 2020, "red", 1000, CarState.GOOD);
	CarDto car2 = new CarDto(CAR_NUMBER_2, MODEL1, 2020, "silver", 10000, CarState.OLD);
	CarDto car3 = new CarDto(CAR_NUMBER_3, MODEL4, 2023, "white", 0, CarState.NEW);
	CarDto car4 = new CarDto(CAR_NUMBER_4, MODEL4, 2023, "black", 0, CarState.NEW);
	CarDto car5 = new CarDto(CAR_NUMBER_5, MODEL3, 2021, "silver", 5000, CarState.MIDDLE);
	PersonDto personDto = new PersonDto(PERSON_ID_NOT_EXISTS, NAME1, BIRTH_DATE_1, EMAIL1);
	PersonDto personDto1 = new PersonDto(PERSON_ID_1, NAME1, BIRTH_DATE_1, EMAIL1);
	PersonDto personDto2 = new PersonDto(PERSON_ID_2, NAME2, BIRTH_DATE_2, EMAIL2);
	PersonDto personDto3 = new PersonDto(PERSON_ID_3, NAME3, BIRTH_DATE_3, EMAIL3);
	@Autowired
	CarsService carsService;

	@Test
	void testAddPerson() {
		assertEquals(personDto, carsService.addPerson(personDto));
		assertThrowsExactly(IllegalPersonsStateException.class, () -> carsService.addPerson(personDto));
		CarOwner carOwner = carOwnerRepo.findById(personDto.id()).orElse(null);
		assertEquals(personDto, carOwner.build());

	}

	@Test
	void testAddCar() {
		assertEquals(car4, carsService.addCar(car4));
		CarDto carNoModel = new CarDto("11111111111", MODEL1, 2018, "green", 100000, CarState.OLD);
		assertThrowsExactly(IllegalCarsStateException.class, () -> carsService.addCar(car4));
		assertThrowsExactly(ModelNotFoundException.class, () -> carsService.addCar(carNoModel));

	}

	@Test
	void testAddModel() {
		ModelDto modelDtoNew = new ModelDto(MODEL4, 2024, "Company1", 100, 2000);
		assertEquals(modelDtoNew, carsService.addModel(modelDtoNew));
		assertThrowsExactly(ModelIllegalStateException.class, () -> carsService.addModel(modelDtoNew));
	}

	@Test
	void testUpdatePerson() {
		PersonDto personUpdated = new PersonDto(PERSON_ID_1, NAME1, BIRTH_DATE_1, NEW_EMAIL);
		assertEquals(personUpdated, carsService.updatePerson(personUpdated));
		assertEquals(NEW_EMAIL, carOwnerRepo.findById(PERSON_ID_1).get().getEmail());
		assertThrowsExactly(PersonNotFoundException.class, () -> carsService.updatePerson(personDto));
	}

	@Test

	void testDeletePerson() {

		assertEquals(personDto1, carsService.deletePerson(PERSON_ID_1));
		assertThrowsExactly(PersonNotFoundException.class, () -> carsService.deletePerson(PERSON_ID_1));

	}

	@Test
	void testDeleteCar() {

		assertEquals(car1, carsService.deleteCar(CAR_NUMBER_1));
		assertThrowsExactly(CarNotFoundException.class, () -> carsService.deleteCar(CAR_NUMBER_1));

	}

	@Test
	void testPurchaseNewCarOwnerWithOldOwner() {
		int countDeals = (int) tradeDealRepo.count();
		TradeDealDto tradeDealDto = new TradeDealDto(CAR_NUMBER_1, PERSON_ID_2, DATE_TRADE_DEAL_1);
		assertEquals(tradeDealDto, carsService.purchase(tradeDealDto));
		assertEquals(PERSON_ID_2, carRepo.findById(CAR_NUMBER_1).get().getCarOwner().getId());
		TradeDeal tradeDeal = tradeDealRepo.findAll().get(countDeals);
		assertEquals(CAR_NUMBER_1, tradeDeal.getCar().getNumber());
		assertEquals(PERSON_ID_2, tradeDeal.getCarOwner().getId());
		assertEquals(DATE_TRADE_DEAL_1, tradeDeal.getDate().toString());

	}

	@Test
	void testPurchaseNewCarOwnerWithoutOldOwner() {
		int countDeals = (int) tradeDealRepo.count();
		carsService.addCar(car4);
		TradeDealDto tradeDealDto = new TradeDealDto(CAR_NUMBER_4, PERSON_ID_2, DATE_TRADE_DEAL_1);
		assertEquals(tradeDealDto, carsService.purchase(tradeDealDto));
		assertEquals(PERSON_ID_2, carRepo.findById(CAR_NUMBER_4).get().getCarOwner().getId());
		TradeDeal tradeDeal = tradeDealRepo.findAll().get(countDeals);
		assertEquals(CAR_NUMBER_4, tradeDeal.getCar().getNumber());
		assertEquals(PERSON_ID_2, tradeDeal.getCarOwner().getId());
		assertEquals(DATE_TRADE_DEAL_1, tradeDeal.getDate().toString());

	}

	@Test
	void testPurchaseNotFound() {
		TradeDealDto tradeDealCarNotFound = new TradeDealDto(CAR_NUMBER_4, PERSON_ID_1, DATE_TRADE_DEAL_1);
		TradeDealDto tradeDealOwnerNotFound = new TradeDealDto(CAR_NUMBER_1, PERSON_ID_NOT_EXISTS, DATE_TRADE_DEAL_1);
		assertThrowsExactly(PersonNotFoundException.class, () -> carsService.purchase(tradeDealOwnerNotFound));
		assertThrowsExactly(CarNotFoundException.class, () -> carsService.purchase(tradeDealCarNotFound));

	}

	@Test
	void testPurchaseNoNewCarOwner() {
		int countDeals = (int) tradeDealRepo.count();
		TradeDealDto tradeDealDto = new TradeDealDto(CAR_NUMBER_1, null, DATE_TRADE_DEAL_1);
		assertEquals(tradeDealDto, carsService.purchase(tradeDealDto));
		assertNull(carRepo.findById(CAR_NUMBER_1).get().getCarOwner());
		TradeDeal tradeDeal = tradeDealRepo.findAll().get(countDeals);
		assertEquals(CAR_NUMBER_1, tradeDeal.getCar().getNumber());
		assertNull(tradeDeal.getCarOwner());
		assertEquals(DATE_TRADE_DEAL_1, tradeDeal.getDate().toString());
	}

	@Test
	void testPurchaseSameOwner() {
		TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER_1, PERSON_ID_1, DATE_TRADE_DEAL_1);
		assertThrowsExactly(TradeDealIllegalStateException.class, () -> carsService.purchase(tradeDeal));
		carsService.addCar(car4);
		TradeDealDto tradeDealNoOwners = new TradeDealDto(CAR_NUMBER_4, null, DATE_TRADE_DEAL_1);
		assertThrowsExactly(TradeDealIllegalStateException.class, () -> carsService.purchase(tradeDealNoOwners));
	}

	@Test
	/**
	 * test of the method getOwnerCars the method has been written at CW #64
	 */
	void testGetOwnerCars() {
		List<CarDto> cars = carsService.getOwnerCars(PERSON_ID_1);
		assertEquals(1, cars.size());
		assertEquals(car1, cars.get(0));
	}

	@Test
	/**
	 * test of the method getCarOwner the method has been written at CW #64
	 */
	void testGetCarOwner() {
		PersonDto ownerActual = carsService.getCarOwner(CAR_NUMBER_1);
		assertEquals(personDto1, ownerActual);
		assertThrowsExactly(CarNotFoundException.class, () -> carsService.getCarOwner(CAR_NUMBER_4));
	}

	@Test
	/**
	 * test of the method mostSoldModelNames the method has been written at CW #64
	 */
	void testMostSoldModelNames() {
		List<String> mostSoldModelNames = carsService.mostSoldModelNames();
		assertEquals("model1", mostSoldModelNames.get(0));

	}

	@Test
	/**
	 * test of the method mostPopularModelNames the method has been written at CW
	 * #64
	 */
	void testMostPopularModelNames() {
		List<ModelNameAmount> modelNameAmounts = carsService.mostPopularModelNames(3);
		assertEquals("model1", modelNameAmounts.get(0).getName());
		assertEquals(3, modelNameAmounts.get(0).getAmount());
		assertEquals("model4", modelNameAmounts.get(1).getName());
		assertEquals(2, modelNameAmounts.get(1).getAmount());
		assertEquals("model3", modelNameAmounts.get(2).getName());
		assertEquals(1, modelNameAmounts.get(2).getAmount());
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() -> carsService.mostPopularModelNames(2).get(2).getAmount());

	}

	// tests for the methods of the HW #64
	@Test
	void testCountTradeDealAtMonthModel() {
		long count1 = carsService.countTradeDealAtMonthModel(MODEL4, 4, 2023);
		assertEquals(1, count1);
		long count2 = carsService.countTradeDealAtMonthModel(MODEL1, 3, 2023);
		assertEquals(3, count2);
		long count3 = carsService.countTradeDealAtMonthModel(MODEL1, 9, 2021);
		assertEquals(0, count3);
	}

	@Test
	void testMostPopularModelNameByOwnerAges() {
		List<ModelNameAmount> modelNameAmounts = carsService.mostPopularModelNameByOwnerAges(3, 10, 100);
		assertEquals("model1", modelNameAmounts.get(0).getName());
		assertEquals(3, modelNameAmounts.get(0).getAmount());
		assertEquals("model4", modelNameAmounts.get(1).getName());
		assertEquals(2, modelNameAmounts.get(1).getAmount());
		assertEquals("model3", modelNameAmounts.get(2).getName());
		assertEquals(1, modelNameAmounts.get(2).getAmount());
		modelNameAmounts = carsService.mostPopularModelNameByOwnerAges(3, 10, 40);
		assertEquals("model1", modelNameAmounts.get(0).getName());
		assertEquals(3, modelNameAmounts.get(0).getAmount());
		assertEquals("model3", modelNameAmounts.get(1).getName());
		assertEquals(1, modelNameAmounts.get(1).getAmount());
		modelNameAmounts = carsService.mostPopularModelNameByOwnerAges(3, 40, 100);
		assertEquals("model4", modelNameAmounts.get(0).getName());
		assertEquals(2, modelNameAmounts.get(0).getAmount());
	}

	@Test
	void testOneMostPopularColorModel() {
		String color = carsService.oneMostPopularColorModel(MODEL1);
		assertEquals("silver", color);
		color = carsService.oneMostPopularColorModel(MODEL3);
		assertEquals("red", color);
		color = carsService.oneMostPopularColorModel(MODEL4);
		assertEquals("white", color);
		color = carsService.oneMostPopularColorModel(MODEL2);
		assertEquals(null, color);
	}

	@Test
	void testMinEnginePowerCapacityByOwnerAges() {
		EnginePowerCapacity enginePowerCapacity = carsService.minEnginePowerCapacityByOwnerAges(10, 100);
		assertEquals(1300, enginePowerCapacity.getEngineCapacity());
		assertEquals(84, enginePowerCapacity.getEnginePower());
		enginePowerCapacity = carsService.minEnginePowerCapacityByOwnerAges(40, 100);
		assertEquals(2500, enginePowerCapacity.getEngineCapacity());
		assertEquals(160, enginePowerCapacity.getEnginePower());
	}

}