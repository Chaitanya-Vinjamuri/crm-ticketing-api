package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.enums.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Transaction;
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

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.persist(agent);

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
    }
    @Override
    public Agent findById(
            Long id) {

        Session session =
                sessionFactory.openSession();

        try {

            return session.get(
                    Agent.class,
                    id
            );

        } finally {

            session.close();
        }
    }

    @Override
    public List<Agent> findAll() {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Agent",
                            Agent.class
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }

    @Override
    public void update(
            Agent agent) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.merge(agent);

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
    }

    @Override
    public void delete(
            Long id) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            Agent agent =
                    session.get(
                            Agent.class,
                            id
                    );

            if (agent != null) {

                session.remove(agent);
            }

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
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