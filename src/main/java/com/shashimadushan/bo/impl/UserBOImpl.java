package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dao.DAOFactory;
import com.shashimadushan.dao.custom.UserDAO;
import com.shashimadushan.dao.impl.UserDAOImpl;
import com.shashimadushan.dto.UserDTO;
import com.shashimadushan.entitys.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public void addUser(UserDTO userDTO) {
        userDAO.addUser(new User(userDTO.getUserName(),userDTO.getPassword(),userDTO.getRoleName()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
    List<User> users =  userDAO.getAllUsers();
    List<UserDTO> userDTOs = new ArrayList<>();
    for (User user : users) {
        UserDTO userDTO = new UserDTO(user.getId(),user.getUserName(),user.getPassword(),user.getRoleName());
        userDTO.toString();
        userDTOs.add(userDTO);
    }
    return userDTOs;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        userDAO.updateUser(new User(userDTO.getId(),userDTO.getUserName(),userDTO.getPassword(),userDTO.getRoleName()));
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        userDAO.deleteUser(new User(userDTO.getId(),userDTO.getUserName(),userDTO.getPassword(),userDTO.getRoleName()));
    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        return List.of();
    }

    @Override
    public boolean authenticateUser(UserDTO userDTO) {

        User user = userDAO.getUser(userDTO.getUserName());
        return false;
    }
}
