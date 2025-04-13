package com.github.sbahmani.jalcal.util;

/**
 * Custom exception class for handling date-related errors in the JalCal library.
 * This exception is thrown when invalid date operations or calculations occur.
 *
 * @author sjb
 */
public class DateException extends Exception {

    /**
     * Constructs a new DateException with null as its detail message.
     * The cause is not initialized.
     */
    public DateException() {
        super();
    }
}