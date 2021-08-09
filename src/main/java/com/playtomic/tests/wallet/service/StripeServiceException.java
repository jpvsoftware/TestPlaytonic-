package com.playtomic.tests.wallet.service;

public class StripeServiceException extends Exception {
    public StripeServiceException(String errorMessage) {
        super(errorMessage);
    }
}
