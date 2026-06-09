package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.enums.Department;

import java.util.List;

public interface AgentDao {

    void save(Agent agent);

    Agent findById(Long id);

    List<Agent> findAll();

    void update(Agent agent);

    void delete(Long id);

    List<Agent> findByDepartment(
            Department department
    );
}