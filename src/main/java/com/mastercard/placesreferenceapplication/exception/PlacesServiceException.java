package com.mastercard.placesreferenceapplication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlacesServiceException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(PlacesServiceException.class);

    public PlacesServiceException(Exception e) {
        super(e);
        log.error("Client side error has occurred.", e);
    }
}
