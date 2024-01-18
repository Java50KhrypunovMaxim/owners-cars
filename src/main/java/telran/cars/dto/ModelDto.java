package telran.cars.dto;

import static telran.cars.api.ValidationConstants.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModelDto {
	@NotEmpty(message=MISSING_MODEL_NAME_MESSAGE)
	String modelName;
	@NotEmpty(message=MISSING_MODEL_YEAR_MESSAGE)
	@Min(value=MIN_MODEL_YEAR, message=WRONG_MIN_YEAR)
	Integer modelYear;
	@NotEmpty(message=MISSING_COMPANY_MESSAGE)
	String company;
	Integer enginePower;
	Integer engineCapacity;
}