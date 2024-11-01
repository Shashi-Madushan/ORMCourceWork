package com.shashimadushan.config;

import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static  FactoryConfiguration factoryConfiguration ;

    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration config = new Configuration();
        config.configure();
        config.addAnnotatedClass(Student.class);
        config.addAnnotatedClass(Enrolment.class);
        config.addAnnotatedClass(Enrolment.class);
        sessionFactory = config.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }
    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }
}
