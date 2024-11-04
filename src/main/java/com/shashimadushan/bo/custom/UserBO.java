package com.shashimadushan.bo.custom;

import com.shashimadushan.bo.SuperBO;
import com.shashimadushan.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    public void addUser(UserDTO userDTO);

    public List<UserDTO> getAllUsers();

    public void updateUser(UserDTO userDTO);

    public void deleteUser(UserDTO userDTO);

    public List<UserDTO> searchUsers(String query);

    public boolean authenticateUser(UserDTO userDTO);
}
