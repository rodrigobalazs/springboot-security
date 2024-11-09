package com.rbalazs.securityapi.enums;

/**
 * Enum which contains some application constants.
 */
public enum AppConstants {;

    /** Due to a spring security naming convention the Roles should be stored into the database as ROLE_<name> */
    public static final String  ROLE_ADMIN = "ROLE_ADMIN";
    public static final String  ROLE_CUSTOMER = "ROLE_CUSTOMER";

    public static final String  PRODUCT_OFFICE_CHAIR_NAME = "Office Chair Flexi Seat";
    public static final Integer PRODUCT_OFFICE_CHAIR_AVAILABLE_QUANTITY = 20;

    public static final String PRODUCT_SOFA_NAME = "Rustic Sofa";
    public static final Integer PRODUCT_SOFA_AVAILABLE_QUANTITY = 10;

    public static final String NEW_USER_REGISTRATION = "a new User Account has been registered with email: ";

    public static final String SWAGGER_UI_URL = "/swagger-ui/**";
    public static final String SWAGGER_UI_URL_2 = "/v3/api-docs/**";
}