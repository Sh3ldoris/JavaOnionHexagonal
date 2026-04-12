package com.waterconnect.domain.exception;

public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String rule) {
        super("Business rule violated: " + rule);
    }
}
