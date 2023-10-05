package com.bol.kalah.exception;

/**
 * This Exception is thrown by this application if runtime problem occurs
 *
 * @author revathik
 */
public class KalahRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2687764222713082249L;

    /**
     * Instantiates a new KalahRuntimeException.
     */
    public KalahRuntimeException() {
        // Default Constructor
    }

    /**
     * Instantiates a new KalahRuntimeException.
     *
     * @param message - String
     */
    public KalahRuntimeException(String message) {
        super(message);
    }

    /**
     * Instantiates a new KalahRuntimeException.
     *
     * @param cause - Throwable
     */
    public KalahRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new KalahRuntimeException.
     *
     * @param message - String
     * @param cause - Throwable
     */
    public KalahRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}

