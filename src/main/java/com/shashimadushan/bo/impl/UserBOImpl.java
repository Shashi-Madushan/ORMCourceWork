package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dto.UserDTO;

import java.util.List;

public class UserBOImpl implements UserBO {
    @Override
    public void addUser(UserDTO userDTO) {

    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        return List.of();
    }
}
