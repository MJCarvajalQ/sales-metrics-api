package com.mjcarvajalq.sales_metrics_api.exceptions;

/**
 * Custom exception thrown when a user is not found in the system.
 * This provides more specific error handling than generic EntityNotFoundException.
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }
}
