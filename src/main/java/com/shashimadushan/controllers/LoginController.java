package com.shashimadushan.controllers;

import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dto.UserDTO;
import com.shashimadushan.utils.PasswordUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    Stage stage;
    @FXML
    private PasswordField pwdTextField;

    @FXML
    private TextField usernameTextField;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    UserBO userBO = (UserBO) BOFactory.getBO(BOFactory.BOType.USER);
    @FXML
    void loginBtnOnAction(ActionEvent event) {
        String usename = usernameTextField.getText();
        String pwd = pwdTextField.getText();



        boolean isAuth = userBO.authenticateUser(new UserDTO(usename, pwd));

        if (isAuth) {
           loadDashboarView();
        }
    }

    private void loadDashboarView() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));

            Scene scene = new Scene(loader.load());

            DashboardController dashboardController = loader.getController();
            dashboardController.setStage(this.stage);

            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.setResizable(true);


            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void regiterOnClick(ActionEvent event) {
        loadRegisterView();
    }


    private void loadRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));

            Scene scene = new Scene(loader.load());
            RegisterController registerController =loader.getController();
            registerController.setStage(stage);

            stage.setScene(scene);
            stage.setTitle("Register");
            stage.setResizable(false);


            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
