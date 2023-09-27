package com.xcale.xcaletest.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Base exception class for custom exceptions in the application.
 * This exception class provides a foundation for custom exceptions,
 * including an error code and a formatted error message with optional arguments.
 * It extends {@link RuntimeException} for unchecked exception behavior.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    private final String code;

    /**
     * Constructs a {@code BaseException} with the specified error code,
     * message, and optional message arguments.
     *
     * @param code      The error code associated with the exception.
     * @param message   The detailed error message with optional placeholders for arguments.
     * @param arguments Optional arguments to be formatted into the error message.
     */
    public BaseException(final String code, final String message, final Object... arguments) {
        super(formatMessage(message, arguments));
        this.code = code;
    }

    /**
     * Formats the error message with the provided arguments.
     *
     * @param message The error message with optional placeholders for arguments.
     * @param args    Optional arguments to be formatted into the error message.
     * @return The formatted error message.
     */
    private static String formatMessage(final String message, final Object... args) {
        String formattedMessage = message;
        for (Object arg : args) {
            formattedMessage = formattedMessage.replaceFirst("\\{(\\w*)\\}", String.valueOf(arg));
        }
        return formattedMessage;
    }
}

