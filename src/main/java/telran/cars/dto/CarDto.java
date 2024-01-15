package telran.cars.dto;
import jakarta.validation.constraints.*;
import static telran.cars.api.ValidationConstants.*;

import java.util.Objects;

public record CarDto
(@NotEmpty(message=MISSING_CAR_NUMBER_MESSAGE)
@Pattern(regexp = CAR_NUMBER_REGEXP, message=WRONG_CAR_NUMBER_MESSAGE) String number,

@NotNull (message=MISSING_CAR_MODEL_MESSAGE) String model,
@NotNull(message = MISSING_YEAR_MESSAGE)
@Min(value = MINIMUM_YEAR, message = WRONG_MINIMUM_YEAR_MESSAGE) 
@Max(value = MAXIMUM_YEAR, message = WRONG_MAXIMUM_YEAR_MESSAGE) 
int year,

@Min(value=MIN_PERSON_ID_VALUE, message=WRONG_MIN_PERSON_ID_VALUE) 
@Max(value=MAX_PERSON_ID_VALUE, message=WRONG_MAX_PERSON_ID_VALUE )
Long id,

@NotNull (message=MISSING_CAR_COLOR_MESSAGE) 
CarColors color,

@NotNull(message = MISSING_KILOMETERS_MESSAGE) 
int kilometers,

@NotNull (message=MISSING_CAR_STATE_MESSAGE ) 
CarState state)

{
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarDto other = (CarDto) obj;
		return Objects.equals(number, other.number);
	}
	
}
