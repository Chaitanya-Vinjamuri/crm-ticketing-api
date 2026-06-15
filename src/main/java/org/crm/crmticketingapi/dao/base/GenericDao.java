package org.crm.crmticketingapi.dao.base;

import java.util.List;

public interface GenericDao<T> {

    void save(T entity);

    T findById(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}