package org.lyancsie.config;

public class PropertyLoadFailureException extends RuntimeException {
    private static final String MESSAGE = "Failed to load properties";
    public PropertyLoadFailureException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
