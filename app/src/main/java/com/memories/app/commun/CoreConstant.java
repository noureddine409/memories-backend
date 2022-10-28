package com.memories.app.commun;

import java.util.List;

import org.springframework.data.domain.Sort;



public class CoreConstant {
	
	public static class Exception {

        public static final String DEFAULT = "message.exception.default";
        public static final String NOT_FOUND = "message.exception.not.found.element";
        public static final String ALREADY_EXISTS = "message.exception.already.exists.element";
        public static final String DELETE_ELEMENT = "message.exception.delete.element";
        public static final String FIND_ELEMENTS = "message.exception.find.elements";
        public static final String FILE_UNAUTHORIZED_FORMAT = "message.exception.unauthorized-format";
        public static final String UNAUTHORIZIED_FILE_NUMBER = "message.exception.file-number";
        public static final String AUTHENTICATION_BAD_CREDENTIALS = "message.exception.authentication.bad.credentials";
        public static final String AUTHORIZATION_INVALID_TOKEN = "message.exception.authorization.invalid.token";
        public static final String AUTHORIZATION_MISSING_TOKEN = "message.exception.authorization.missing.token";
        public static final String AUTHORIZATION_MISSING_HEADER = "message.exception.authorization.missing.header";
        public static final String AUTHORIZATION_INVALID_HEADER = "message.exception.authorization.invalid.header";
        public static final String AUTHENTICATION_NULL_PRINCIPAL  = "message.exception.authentication.null.pricipal";
        public static final String VALIDATION_NOT_BLANK  = "message.exception.validation.NotBlank";
        public static final String VALIDATION_NOT_NULL  = "message.exception.validation.NotNull";
        public static final String VALIDATION_POSITIVE_OR_ZERO  = "message.exception.validation.PositiveOrZero";
        public static final String VALIDATION_FUTURE  = "message.exception.validation.Future";
        public static final String VALIDATION_MIN  = "message.exception.validation.Min";
        public static final String VALIDATION_MAX  = "message.exception.validation.Max";
        public static final String VALIDATION_EMAIL  = "message.exception.validation.Email";
        public static final String VALIDATION_SIZE  = "message.exception.validation.Size";
        public static final String VALIDATION_PHONE_NUMBER  = "message.exception.validation.PhoneNumber";
        public static final String VALIDATION_NO_DIGITS  = "message.exception.validation.NoDigits";
        public static final String VALIDATION_POSTAL_CODE  = "message.exception.validation.PostalCode";
        public static final String VALIDATION_ASSERT_TRUE  = "message.exception.validation.AssertTrue";
        public static final String PAGINATION_PAGE_NUMBER = "message.exception.pagination.page.min";
        public static final String PAGINATION_PAGE_SIZE_MIN = "message.exception.pagination.size.min";
        public static final String PAGINATION_PAGE_SIZE_MAX = "message.exception.pagination.size.max";
        public static final String VALIDATION_FILE_SIZE_MAX = "message.exception.validation.file-size";
        
	}
	
	public static class Pagination {
        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MAX_PAGE_SIZE = 20;
        public static final String DEFAULT_SORT_PROPERTY = "createdAt";
        public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    }
	
	public static class Validation {
        public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        public static final int PASSWORD_SIZE_MIN = 8;
        public static final int PASSWORD_SIZE_MAX = 72;
        public static final String POSTAL_CODE_REGEX = "^\\d{5}$";
        public static final List<String> PHONE_NUMBER_REGEX = List.of(
                "^\\d{10}$",
                "^(\\d{3}[- .]?){2}\\d{4}$",
                "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
                "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
        );
        public static final float LOCATION_LONGITUDE_MIN = -90.0f;
        public static final float LOCATION_LONGITUDE_MAX = 90.0f;
        public static final float LOCATION_LATITUDE_MIN = -180.0f;
        public static final float LOCATION_LATITUDE_MAX = 180.0f;
    }
}
