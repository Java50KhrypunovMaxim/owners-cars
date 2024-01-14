package telran.cars.dto;

import static telran.cars.api.ValidationConstants.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ModelDto(
		@NotEmpty (message=MISSING_CAR_MODEL_MESSAGE) String model,
		@Max(value = MAXIMUM_YEAR, message = WRONG_MAXIMUM_YEAR_MESSAGE) 
		int year,
		@NotNull (message = MISSING_CAR_COMPANY_MESSAGE)
		String company,
		@NotNull(message = MISSING_YEAR_MESSAGE) 
	    @NotEmpty(message = MISSING_ENGINE_POWER_MESSAGE)
	    @Min(value=MIN_ENGINE_POWER, message=WRONG_MIN_PERSON_ID_VALUE)
		@Max(value=MAX_ENGINE_POWER,
	    message=WRONG_MAX_PERSON_ID_VALUE )
		int enginePower,
		@NotNull(message = MISSING_ENGINE_CAMPACITY_MESSAGE)
		int engineCapacity
	) {}