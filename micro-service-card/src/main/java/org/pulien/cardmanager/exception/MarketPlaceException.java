package org.pulien.cardmanager.exception;

public class MarketPlaceException extends Exception {
    public MarketPlaceException(String message, Exception exception) {
        super(message, exception);
    }
}
