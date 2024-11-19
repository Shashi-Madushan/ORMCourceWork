package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.EnrolmetDAO;
import com.shashimadushan.entitys.Enrolment;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.List;


public class EnrolmentDAOImpl implements EnrolmetDAO {

    @Override
    public void save(List<Enrolment> enrolments) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            for (Enrolment enrolment : enrolments) {
                session.save(enrolment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(List<Enrolment> enrolments) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            for (Enrolment enrolment : enrolments) {
                session.merge(enrolment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Enrolment> getEnrollments(String studentId, String programName) {
        List<Enrolment> enrolments = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "SELECT e " +
                    "FROM Enrolment e " +
                    "JOIN e.student s " +
                    "JOIN e.program c " +
                    "WHERE s.studentId = :studentId " +
                    "AND c.programName = :programName";

            enrolments = session.createQuery(hql, Enrolment.class)
                    .setParameter("studentId", studentId)
                    .setParameter("programName", programName)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return enrolments;
    }

    @Override
    public void deleteEnrollments(String studentId, String programName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "SELECT e " +
                    "FROM Enrolment e " +
                    "JOIN e.student s " +
                    "JOIN e.program c " +
                    "WHERE s.studentId = :studentId " +
                    "AND c.programName = :programName";

            List<Enrolment> enrolments = session.createQuery(hql, Enrolment.class)
                    .setParameter("studentId", studentId)
                    .setParameter("programName", programName)
                    .getResultList();

            for (Enrolment enrolment : enrolments) {
                session.delete(enrolment);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}