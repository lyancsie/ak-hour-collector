package org.lyancsie.exception;

public class PropertyLoadFailureException extends RuntimeException {
    static final String MESSAGE = "Failed to load properties";
    public PropertyLoadFailureException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
