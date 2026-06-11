package org.crm.crmticketingapi.service;

public interface RedisService {

    void save(
            String key,
            Object value
    );

    Object get(
            String key
    );

    void delete(
            String key
    );
}