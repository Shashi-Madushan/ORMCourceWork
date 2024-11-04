package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;

@Data
public class HomeController {
    DashboardController dashboardController;

    @FXML
    private JFXButton StudentsBtn;

    @FXML
    private JFXButton newProgramBtn;

    @FXML
    private JFXButton newStudentBtn;

    @FXML
    private Label userNameLbel;

    @FXML
    void StudentsBtnOnAction(ActionEvent event) {
        dashboardController.loadView("students");

    }

    @FXML
    void newProgramBtnOnAction(ActionEvent event) {
        dashboardController.loadView("crudpro");
    }

    @FXML
    void newStudentBtnOnACcion(ActionEvent event) {
        dashboardController.loadView("crudStd");
    }

}
