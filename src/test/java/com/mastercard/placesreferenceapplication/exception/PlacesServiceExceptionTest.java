package com.mastercard.placesreferenceapplication.exception;

import org.junit.jupiter.api.Test;

public class PlacesServiceExceptionTest {
    private static final String MESSAGE = "Something went wrong";

    @Test
    void testServiceExceptionMessage() {
        Exception exception = new Exception(MESSAGE);
        PlacesServiceException placesServiceException = new PlacesServiceException(exception);
        assert(placesServiceException.getMessage().contains(MESSAGE));
    }
}
