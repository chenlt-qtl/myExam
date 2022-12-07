package com.exam.retry;

public class CustomerRetryException extends Exception {

    public CustomerRetryException(String message) {
        super(message);
    }
}
