package com.xcale.xcaletest.exception;

/**
 * Exception thrown when an entity is not found.
 * This exception is typically thrown when attempting to retrieve an entity
 * from the database, but the entity with the specified identifier is not found.
 * It extends {@link BaseException}, providing error code, message, and optional arguments.
 */
public class EntityNotFoundException extends BaseException {
    /**
     * Constructs an {@code EntityNotFoundException} with the specified error code,
     * message, and optional message arguments.
     *
     * @param code      The error code associated with the exception.
     * @param message   The detailed error message.
     * @param arguments Optional arguments to be formatted into the error message.
     */
    public EntityNotFoundException(final String code, final String message, final Object... arguments) {
        super(code, message, arguments);
    }
}
