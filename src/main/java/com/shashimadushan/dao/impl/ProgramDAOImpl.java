package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.ProgramDAO;
import com.shashimadushan.entitys.Program;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {
    @Override
    public void addProgram(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(program);

        transaction.commit();
        session.close();
    }

    @Override
    public List<Program> getAllPrograms() {
        List<Program> programs;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        programs = session.createQuery("FROM Program", Program.class).list();

        transaction.commit();
        session.close();

        return programs;
    }

    @Override
    public void updateProgram(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(program);

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteProgram(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(program);

        transaction.commit();
        session.close();
    }

    @Override
    public Program getProgram(String programId){
        Program culinaryProgram = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        culinaryProgram = session.get(Program.class, programId);

        transaction.commit();
        session.close();

        return culinaryProgram;
    }
    @Override
    public Long getProgramCount(){
        Long count = 0L;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(c) FROM Program c";
        Query<Long> query = session.createQuery(hql, Long.class);

        count = query.uniqueResult();

        transaction.commit();
        session.close();

        return count;
    }


    @Override
    public List<Program> searchPrograms(String query) {
        return List.of();
    }

}
