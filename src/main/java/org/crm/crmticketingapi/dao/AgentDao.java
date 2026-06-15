package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.dao.base.GenericDao;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.enums.Department;

import java.util.List;

public interface AgentDao
        extends GenericDao<Agent> {

    List<Agent> findByDepartment(
            Department department
    );
}