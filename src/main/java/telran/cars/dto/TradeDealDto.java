package telran.cars.dto;
import static telran.cars.api.ValidationConstants.*;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import telran.cars.service.model.Car;
import telran.cars.service.model.CarOwner;

public record TradeDealDto(@NotEmpty(message=MISSING_CAR_NUMBER_MESSAGE)
@Pattern(regexp=CAR_NUMBER_REGEXP, message=WRONG_CAR_NUMBER_MESSAGE) String carNumber, 
@Min(value=MIN_PERSON_ID_VALUE, message=WRONG_MIN_PERSON_ID_VALUE) @Max(value=MAX_PERSON_ID_VALUE,
message=WRONG_MAX_PERSON_ID_VALUE )Long personId,  @Pattern(regexp=BIRTH_DATE_REGEXP, message=WRONG_DATE_FORMAT)
String date) {

}