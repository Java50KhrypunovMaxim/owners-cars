package telran.cars;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.cars.dto.*;
import telran.cars.exceptions.NotFoundException;
import telran.cars.service.CarsService;


@SpringBootTest
	class CarsServiceTest {
	private static final String MODEL = "model";
	private static final String CAR_NUMBER_1 = "111-11-111";
	private static final String CAR_NUMBER_2 = "222-22-222";
	private static final Long PERSON_ID_1 = 123l;
	private static final String NAME1 = "name1";
	private static final String BIRTH_DATE_1 = "2000-10-10";
	private static final String EMAIL1 = "name1@gmail.com";
	private static final Long PERSON_ID_2 = 124l;
	private static final String NAME2 = "name2";
	private static final String BIRTH_DATE_2 = "2000-10-10";
	private static final String EMAIL2 = "name2@gmail.com";
	private static final Long PERSON_ID_NOT_EXISTS = 9999L;
	CarDto car1 = new CarDto(CAR_NUMBER_1, MODEL);
	CarDto car2 = new CarDto(CAR_NUMBER_2, MODEL);
	PersonDto personDto = new PersonDto(PERSON_ID_NOT_EXISTS, NAME1, BIRTH_DATE_1, EMAIL1);
	PersonDto personDto1 = new PersonDto(PERSON_ID_1, NAME1, BIRTH_DATE_1, EMAIL1);
	TradeDealDto deal1 = new TradeDealDto(CAR_NUMBER_1, PERSON_ID_1);
	TradeDealDto deal2 = new TradeDealDto(CAR_NUMBER_2, PERSON_ID_2);
    @Autowired
	CarsService carsService;
    
	@BeforeEach
	void setUp() {
		carsService.removeAll();
		carsService.addCar(car1);
		carsService.addPerson(personDto1);
		carsService.addPerson(new PersonDto(PERSON_ID_2, NAME2, BIRTH_DATE_2, EMAIL2));
		carsService.purchase(new TradeDealDto(CAR_NUMBER_1, PERSON_ID_1));
	}
	
    
	@Test
	void testAddPerson() {
		assertEquals(personDto, carsService.addPerson(personDto));
		assertThrowsExactly(IllegalStateException.class,
				()->carsService.addPerson(personDto1));
	}

	@Test
	void testAddCar() {
		assertEquals(car2, carsService.addCar(car2));
	}

	@Test
	void testDeletePerson() {
		 carsService.deletePerson(123l);
		 assertEquals(personDto1, carsService.addPerson(personDto1));
	}

	@Test
	void testDeleteCar() {
		 carsService.addCar(car2);
		 carsService.deleteCar("222-22-222");
		 assertEquals(car2, carsService.addCar(car2));
	}

	@Test
	void testPurchase() {
		 assertEquals(deal1, carsService.purchase(deal1));
       
	}

	@Test
	void testGetOwnerCars() {
	     List<CarDto> ownerCars = carsService.getOwnerCars(PERSON_ID_1);
	        assertNotNull(ownerCars);
	        List<CarDto> expectedCars = Collections.singletonList(car1);
	        assertEquals(expectedCars.size(), ownerCars.size());
	        assertEquals(expectedCars.get(0), ownerCars.get(0));
	}

	@Test
	void testGetCarOwner() {
		assertEquals(personDto1, carsService.getCarOwner(CAR_NUMBER_1));
	}

}