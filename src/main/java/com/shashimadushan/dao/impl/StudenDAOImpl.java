package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.StudentDAO;
import com.shashimadushan.entitys.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudenDAOImpl implements StudentDAO {
    @Override
    public void addStudent(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(student);

        transaction.commit();
        session.close();
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        students = session.createQuery("from Student", Student.class).list();

        transaction.commit();
        session.close();

        return students;
    }

    @Override
    public void updateStudent(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteStudent(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(student);

        transaction.commit();
        session.close();
    }

    @Override
    public int getStudentCount() {
        int count = 0;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(s) FROM Student s";
        Query<Long> query = session.createQuery(hql, Long.class);

        count = Math.toIntExact(query.uniqueResult());

        transaction.commit();
        session.close();

        return count;
    }


    @Override
    public List<Student> getStudentsEnrolledInAllPrograms() {
        List<Student> students;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT s " +
                "FROM Student s " +
                "JOIN s.enrollments e " +
                "GROUP BY s.id " +
                "HAVING COUNT(DISTINCT e.program.id) = " +
                " (SELECT COUNT(p.id) FROM Program p)";

        Query<Student> query = session.createQuery(hql, Student.class);
        students = query.getResultList();

        transaction.commit();
        session.close();

        return students;
    }

    public List<Student> getStudentsEnrolledInProgram(String programId) {
        Transaction transaction = null;
        List<Student> students = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // HQL query to fetch students enrolled in a specific program
            String hql = "SELECT s FROM Student s JOIN FETCH s.enrollments e JOIN FETCH e.program p WHERE p.id = :programId";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("programId", programId);
            students = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return students;
    }
}
