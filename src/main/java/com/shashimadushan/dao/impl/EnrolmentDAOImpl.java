package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.EnrolmetDAO;
import com.shashimadushan.entitys.Enrolment;
import org.hibernate.Transaction;
import org.hibernate.Session;

public class EnrolmentDAOImpl implements EnrolmetDAO {

    @Override
    public void save(Enrolment enrollment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(enrollment);

        transaction.commit();
        session.close();
    }


    @Override
    public void update(Enrolment enrollment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(enrollment);

        transaction.commit();
        session.close();
    }

    @Override
    public Enrolment getEnrollment(String studentId, String programName) {
        Enrolment enrollment = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT e " +
                "FROM Enrolment e " +
                "JOIN e.student s " +
                "JOIN e.program c " +
                "WHERE s.studentId = :studentId " +
                "AND c.programName = :programName";

        enrollment = session.createQuery(hql, Enrolment.class)
                .setParameter("studentId", studentId)
                .setParameter("programName", programName)
                .uniqueResult();

        transaction.commit();
        session.close();

        return enrollment;
    }

    @Override
    public void deleteEnrollment(String studentId, String programName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Retrieve the enrolment to be deleted
            String hql = "SELECT e " +
                    "FROM Enrolment e " +
                    "JOIN e.student s " +
                    "JOIN e.program c " +
                    "WHERE s.Id = :studentId " +
                    "AND c.name = :programName";

            Enrolment enrolment = session.createQuery(hql, Enrolment.class)
                    .setParameter("studentId", studentId)
                    .setParameter("programName", programName)
                    .uniqueResult();

            // If the enrolment exists, delete it
            if (enrolment != null) {
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
