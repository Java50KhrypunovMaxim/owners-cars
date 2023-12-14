package telran.cars;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.cars.dto.CarDto;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.service.CarsService;
@WebMvcTest 
class CarsControllerTest {
	@MockBean 
	CarsService carsService;
	@Autowired 
	MockMvc mockMvc;
	CarDto carDto = new CarDto("car", "model");
	CarDto carDto1 = new CarDto("car123", "mode123");
	PersonDto personDto = new PersonDto(12345678, "Maxim", "17.02.1989","khrip@ukr.net" );
	PersonDto personDto1 = new PersonDto(12345278, "Maxim", "17.02.1989","khrip@ukr.net" );
	String carNumber = "BH1702";
	long idPerson =  12345566;
	List<CarDto> ownerCars = new ArrayList<>();
	TradeDealDto tradeDealDto = new TradeDealDto(carNumber, idPerson); 
	
	@Autowired 
	ObjectMapper mapper;

	@Test
	void testAddCar() throws Exception {
		when(carsService.addCar(carDto)).thenReturn(carDto);
		String jsonCarDto = mapper.writeValueAsString(carDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON)
				.content(jsonCarDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonCarDto, actualJSON );
		
	}

	@Test
	void testAddPerson()throws Exception {
		when(carsService.addPerson(personDto)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto);
		String actualJSONforPerson = mockMvc.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
		.content(jsonPersonDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonPersonDto, actualJSONforPerson );
	}

	@Test
	void testUpdatePerson()throws Exception {
		when(carsService.updatePerson(personDto)).thenReturn(personDto);
        String jsonPersonDto = mapper.writeValueAsString(personDto);
        String actualJSONUpdatePerson = mockMvc.perform(put("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
                .content(jsonPersonDto))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(jsonPersonDto,  actualJSONUpdatePerson);
	}

	@Test
	void testPurchase()throws Exception{
	        when(carsService.purchase(tradeDealDto)).thenReturn(tradeDealDto);
	        String jsonTradeDealDto = mapper.writeValueAsString(tradeDealDto);
	        String actualJSONPurchase = mockMvc.perform(put("http://localhost:8080/cars/trade").contentType(MediaType.APPLICATION_JSON)
	                .content(jsonTradeDealDto))
	                .andExpect(status().isOk())
	                .andReturn()
	                .getResponse()
	                .getContentAsString();
	        assertEquals(jsonTradeDealDto, actualJSONPurchase);
	}

	@Test
	void testDeletePerson() throws Exception {
		when(carsService.deletePerson(idPerson)).thenReturn(personDto);
		String actualJSONforDelPer = mockMvc.perform(delete("http://localhost:8080/cars/person/{idPerson}", idPerson))
				 	.andExpect(status().isOk())
	                .andReturn()
	                .getResponse()
	                .getContentAsString();
		 assertNotNull(actualJSONforDelPer);
	}

	@Test
	void testDeleteCar()throws Exception {
		when(carsService.deleteCar(carNumber)).thenReturn(carDto1);
		String actualJSONforDelCar = mockMvc.perform(delete("http://localhost:8080/cars/{carNumber}", carNumber))
			 	.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
	 assertNotNull(actualJSONforDelCar);
}
	

	@Test
	void testGetOwnerCars()  throws Exception {
		when(carsService.getOwnerCars(idPerson)).thenReturn(ownerCars);
        mockMvc.perform(get("http://localhost:8080/cars/person/{id}", idPerson))
                .andExpect(status().isOk());
	}

	@Test
	void testGetCarOwner () throws Exception {
		when(carsService.getCarOwner(carNumber)).thenReturn(personDto1);
		
		 String actualJSON = mockMvc.perform(get("http://localhost:8080/cars/{carNumber}", carNumber))
	                .andExpect(status().isOk())
	                .andReturn()
	                .getResponse()
	                .getContentAsString();
		 assertNotNull(actualJSON);
	}

}