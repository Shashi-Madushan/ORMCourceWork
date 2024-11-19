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
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = null;
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            students = session.createQuery("from Student", Student.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(Student student) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public int getStudentCount() {
        int count = 0;
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT COUNT(s) FROM Student s";
            Query<Long> query = session.createQuery(hql, Long.class);
            count = Math.toIntExact(query.uniqueResult());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Student> getStudentsEnrolledInAllPrograms() {
        List<Student> students = null;
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            String hql = """
            SELECT s 
            FROM Student s
            WHERE (
                SELECT COUNT(DISTINCT e.program.id) 
                FROM Enrollment e
                WHERE e.student.id = s.id
            ) = (
                SELECT COUNT(p.id) 
                FROM Program p
            )
        """;

            Query<Student> query = session.createQuery(hql, Student.class);
            students = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return students;
    }


    @Override
    public List<Student> getStudentsEnrolledInProgram(String programId) {
        Transaction transaction = null;
        List<Student> students = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
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