package com.shashimadushan.config;

import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import com.shashimadushan.entitys.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static  FactoryConfiguration factoryConfiguration ;

    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration config = new Configuration().configure()

                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Enrolment.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Program.class);
        sessionFactory = config.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
