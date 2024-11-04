package com.shashimadushan.utils;

public class ExseptionHandeleUtil {
    // UserAlreadyExistsException.java
    public class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    // InvalidCredentialsException.java
    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    // InvalidInputException.java
    public class InvalidInputException extends RuntimeException {
        public InvalidInputException(String message) {
            super(message);
        }
    }

}
