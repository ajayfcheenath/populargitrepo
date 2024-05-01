package com.af.exception;

/**
 * Custom runtime exception indicating that a GitHub repository is not found.
 */
public class RepositoryNotFoundException extends RuntimeException {
    public RepositoryNotFoundException(String message) {
        super(message);
    }
}
