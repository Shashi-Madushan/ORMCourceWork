package com.shashimadushan.config;

import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import com.shashimadushan.entitys.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;

    private SessionFactory sessionFactory;

//    private FactoryConfiguration() {
//        Configuration config = new Configuration().configure()
//
//                .addAnnotatedClass(Student.class)
//                .addAnnotatedClass(Enrolment.class)
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Program.class);
//        sessionFactory = config.buildSessionFactory();
//    }

    private FactoryConfiguration() {
        try {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("src/main/resources/hibernate.properties");
            properties.load(input);
            Configuration configuration = new Configuration();
            configuration.setProperties(properties)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Enrolment.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Program.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
        }
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
