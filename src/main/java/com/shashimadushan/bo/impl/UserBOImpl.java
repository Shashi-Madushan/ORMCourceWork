package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dao.DAOFactory;
import com.shashimadushan.dao.custom.UserDAO;
import com.shashimadushan.dao.impl.UserDAOImpl;
import com.shashimadushan.dto.UserDTO;
import com.shashimadushan.entitys.User;
import com.shashimadushan.utils.ExseptionHandeleUtil;
import javafx.scene.control.Alert;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.shashimadushan.utils.GlobelVars.userRole;
import static com.shashimadushan.utils.PasswordUtil.checkPassword;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public void addUser(UserDTO userDTO) {

        try {
            userDAO.addUser(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getRoleName()));
            showMessage("Registration successful!");

        } catch (ExseptionHandeleUtil.UserAlreadyExistsException e) {
            showError("Error: " + e.getMessage());
        } catch (ExseptionHandeleUtil.InvalidInputException e) {
            showError("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error during registration: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getUserName(), user.getPassword(), user.getRoleName());
            userDTO.toString();
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        userDAO.updateUser(new User(userDTO.getId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getRoleName()));
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        userDAO.deleteUser(new User(userDTO.getId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getRoleName()));
    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        return List.of();
    }

    @Override
    public boolean authenticateUser(UserDTO userDTO) {


        try {
            User user = userDAO.getUser(userDTO.getUserName());
            System.out.println(user.getUserName());
            System.out.println(userRole = user.getRoleName());

            showMessage("Login successful! Welcome " + user.getUserName());
            if (checkPassword(userDTO.getPassword(),user.getPassword())) {
                return true;
            }

        } catch (ExseptionHandeleUtil.InvalidCredentialsException e) {
            showError("Error: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error during login: " + e.getMessage());
        }
        return false;
    }

}
