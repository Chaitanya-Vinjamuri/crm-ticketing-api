package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.enums.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class AgentDaoImpl
        implements AgentDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(
            Agent agent) {

        sessionFactory
                .getCurrentSession()
                .persist(agent);
    }

    @Override
    public Agent findById(
            Long id) {

        return sessionFactory
                .getCurrentSession()
                .get(
                        Agent.class,
                        id
                );
    }

    @Override
    public List<Agent> findAll() {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Agent",
                        Agent.class
                )
                .getResultList();
    }

    @Override
    public void update(
            Agent agent) {

        sessionFactory
                .getCurrentSession()
                .merge(agent);
    }

    @Override
    public void delete(
            Long id) {

        Session session =
                sessionFactory
                        .getCurrentSession();

        Agent agent =
                session.get(
                        Agent.class,
                        id
                );

        if (agent != null) {

            session.remove(agent);
        }
    }

    @Override
    public List<Agent> findByDepartment(
            Department department) {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Agent WHERE department = :department",
                        Agent.class
                )
                .setParameter(
                        "department",
                        department
                )
                .getResultList();
    }
}