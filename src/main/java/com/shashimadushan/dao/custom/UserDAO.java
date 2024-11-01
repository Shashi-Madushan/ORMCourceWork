package com.shashimadushan.dao.custom;


import com.shashimadushan.dao.SuperDAO;
import com.shashimadushan.entitys.User;

import java.util.List;

public interface UserDAO extends SuperDAO {

    public void addUser(User user);

    public List<User> getAllUsers();

    public void updateUser(User user);

    public void deleteUser(User user);

    public List<User> searchUsers(String query);

}