package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.base.GenericDaoImpl;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.enums.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgentDaoImpl
        extends GenericDaoImpl<Agent>
        implements AgentDao {

    private final SessionFactory sessionFactory;

    public AgentDaoImpl(
            SessionFactory sessionFactory) {

        super(
                sessionFactory,
                Agent.class
        );

        this.sessionFactory =
                sessionFactory;
    }

    @Override
    public List<Agent> findByDepartment(
            Department department) {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Agent WHERE department = :department",
                            Agent.class
                    )
                    .setParameter(
                            "department",
                            department
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }
}