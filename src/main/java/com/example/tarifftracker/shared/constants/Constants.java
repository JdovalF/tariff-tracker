package com.example.tarifftracker.shared.constants;

public class Constants {

    private Constants() {
    }

    public static final String NOT_FOUND_WITH_ID_MESSAGE = "Entity %s not Found with id %s";
    public static final String NOT_FOUND_WITH_PARAMETERS_MESSAGE = "Entity %s not Found with parameters %s";
    public static final String PRODUCT_ENTITY_NAME = "PRODUCT";
    public static final String BRAND_ENTITY_NAME = "BRAND";
    public static final String PRICES_ENTITY_NAME = "PRICES";
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.\\d{2}\\.\\d{2}$";
    public static final String DATE_REGEX_ERROR_MESSAGE = "Application Date must be in the format YYYY-MM-DD-HH.MM.SS";
}
