package org.crm.crmticketingapi.dao.base;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericDaoImpl<T>
        implements GenericDao<T> {

    private final SessionFactory sessionFactory;

    private final Class<T> entityClass;

    @Override
    public void save(T entity) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction =
                    session.beginTransaction();

            session.persist(entity);

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
    public T findById(Long id) {

        Session session =
                sessionFactory.openSession();

        try {

            return session.get(
                    entityClass,
                    id
            );

        } finally {

            session.close();
        }
    }

    @Override
    public List<T> findAll() {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM " +
                                    entityClass.getSimpleName(),
                            entityClass
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }

    @Override
    public void update(T entity) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction =
                    session.beginTransaction();

            session.merge(entity);

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
    public void delete(Long id) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction =
                    session.beginTransaction();

            T entity =
                    session.get(
                            entityClass,
                            id
                    );

            if (entity != null) {

                session.remove(entity);
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
}