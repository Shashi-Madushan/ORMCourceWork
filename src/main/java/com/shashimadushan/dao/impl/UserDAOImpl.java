package com.shashimadushan.dao.impl;

import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dao.custom.UserDAO;
import com.shashimadushan.entitys.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, userName);
        transaction.commit();
        session.close();
        return user;

    }
}
