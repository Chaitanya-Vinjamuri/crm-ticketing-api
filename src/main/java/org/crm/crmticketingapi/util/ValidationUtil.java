package org.crm.crmticketingapi.util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void validateId(
            Long id,
            String entityName) {

        if (id == null) {

            throw new IllegalArgumentException(
                    entityName + " id cannot be null"
            );
        }
    }
}