package com.xcale.xcaletest.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an error response containing status, error code, and error message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorResponse {
    /**
     * HTTP status code representing the error.
     */
    private Integer status;
    /**
     * Unique error code associated with the error.
     */
    private String errorCode;
    /**
     * Error message describing the error.
     */
    private String errorMessage;
}
