package telran.cars.api;

public interface ValidationConstants {
	String MISSING_CAR_NUMBER_MESSAGE = "Missing car number";
	String CAR_NUMBER_REGEXP = "(\\d{3}-\\d{2}-\\d{3})|(\\d{2}-\\d{3}-\\d{2})";
	String WRONG_CAR_NUMBER_MESSAGE = "Incorrect Car Number";
	String MISSING_CAR_MODEL_MESSAGE = "Missing car model";
	String MISSING_CAR_COLOR_MESSAGE = "Missing car color";
	String MISSING_PERSON_ID_MESSAGE = "Missing person ID";
	String MISSING_YEAR_MESSAGE = "Missing car yaer";
	String MISSING_CAR_COMPANY_MESSAGE = "Missing car company";
	String MISSING_CAR_STATE_MESSAGE = "Missing car state";
	String MISSING_ENGINE_CAMPACITY_MESSAGE = "Missing engine campacity";
	String MISSING_ENGINE_POWER_MESSAGE = "Missing engine power";
	int MIN_ENGINE_POWER = 70;
	int MAX_ENGINE_POWER = 250;
	int MAXIMUM_YEAR = 2024;
	int MINIMUM_YEAR = 1970;
	String WRONG_MAXIMUM_YEAR_MESSAGE = "Year must be less or equal " + MAXIMUM_YEAR;
	String WRONG_MINIMUM_YEAR_MESSAGE = "Year must be greater or equal " + MINIMUM_YEAR;
	String WRONG_MIN_ENGINE_POWER = "Engine power must be greater or equal" +  MIN_ENGINE_POWER;
	String MAX_ENGINE_POWE = "Engine power must be less or equal " + MAX_ENGINE_POWER;
	long MIN_PERSON_ID_VALUE = 100000l;
	long MAX_PERSON_ID_VALUE = 999999l;
	String WRONG_MIN_PERSON_ID_VALUE = "Person ID must be greater or equal " + MIN_PERSON_ID_VALUE;
	String WRONG_MAX_PERSON_ID_VALUE = "Person ID must be less or equal " + MAX_PERSON_ID_VALUE;
	String MISSING_PERSON_NAME_MESSAGE = "Missing person name";
	String MISSING_BIRTH_DATE_MESSAGE = "Missing person's birth date";
	String BIRTH_DATE_REGEXP = "\\d{4}-\\d{2}-\\d{2}";
	String WRONG_DATE_FORMAT = "Wrong date format, must be YYYY-MM-dd";
	String MISSING_PERSON_EMAIL = "Missing email address";
	String WRONG_EMAIL_FORMAT = "Wrong email format";
	String MISSING_CAR_INFORMATION = "Missing information about car";
	String MISSING_CAR_OWNER_INFORMATION = "Missing information about car owner";
	String MISSING_KILOMETERS_MESSAGE = "Missing information about KILOMETERS";
	String MISSING_DATE_MESSAGE = "Missing date";
	
	
}
