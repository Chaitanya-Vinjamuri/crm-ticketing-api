package org.crm.crmticketingapi.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {

        super(message);
    }
}