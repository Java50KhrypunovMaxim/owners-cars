package telran.cars.exceptions.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import telran.cars.exceptions.NotFoundException;

@ControllerAdvice
public class CarsExeptionsController {
	
@ExceptionHandler(NotFoundException.class)
ResponseEntity<String> notFoundHandler(NotFoundException e)
{
	return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
}

@ExceptionHandler(IllegalStateException.class)
ResponseEntity<String> illegalStateException(IllegalStateException e)
{
	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
}
}
