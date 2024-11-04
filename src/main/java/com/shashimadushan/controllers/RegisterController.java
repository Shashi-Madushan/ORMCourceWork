package com.shashimadushan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

    private Stage stage;
    @FXML
    private PasswordField pwdTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void loginOnClick(ActionEvent event) {
       loardLoginView();
    }



    @FXML
    void regiterBtnOnClick(ActionEvent event) {

    }
    private void loardLoginView() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Scene scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);


            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
