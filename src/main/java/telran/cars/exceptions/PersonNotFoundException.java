package telran.cars.exceptions;

import telran.cars.api.ServiceExceptionMessages;
import telran.cars.exceptions.NotFoundException;

public class PersonNotFoundException extends NotFoundException {

	public PersonNotFoundException() {
		super(ServiceExceptionMessages.PERSON_NOT_FOUND);

	}

}
