package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.UserDAO;
import com.shashimadushan.entitys.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public void addUser(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(user);

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public void updateUser(User user) {
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();
    session.update(user);
    transaction.commit();
    session.close();

    }

    @Override
    public void deleteUser(User user) {
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();
    session.remove(user);
    transaction.commit();
    session.close();
    }

    @Override
    public List<User> searchUsers(String query) {
        return List.of();
    }

    @Override


public User getUser(String userName) {
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = null;
    User user = null;
    try {
        transaction = session.beginTransaction();

        // Create a query to fetch the User by userName
        Query<User> query = session.createQuery("FROM User WHERE userName = :userName", User.class);
        query.setParameter("userName", userName);

        // Get the single result or null if no user is found
        user = query.uniqueResult();

        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        session.close();
    }
    return user;
}
}
