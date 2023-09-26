package com.xcale.xcaletest.exception;

/**
 * Exception thrown for validation-related errors.
 * This exception is typically thrown when a validation process fails, such as input validation.
 * It extends {@link BaseException}, providing error code, message, and optional arguments.
 */
public class ValidationException extends BaseException {
    /**
     * Constructs an {@code ValidationException} with the specified error code,
     * message, and optional message arguments.
     *
     * @param code      The error code associated with the exception.
     * @param message   The detailed error message.
     * @param arguments Optional arguments to be formatted into the error message.
     */
    public ValidationException(final String code, final String message, final Object... arguments) {
        super(code, message, arguments);
    }
}
