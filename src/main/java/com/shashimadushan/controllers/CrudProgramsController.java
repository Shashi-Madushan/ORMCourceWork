package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.dto.ProgramDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Data;

@Data
public class CrudProgramsController {
    DashboardController dashboardController;
    ProgramBO programBO = (ProgramBO) BOFactory.getBO(BOFactory.BOType.PROGRAM);

    @FXML
    private JFXButton addBtn;

    @FXML
    private TextField durationTxtField;

    @FXML
    private TextField feeTxtField;

    @FXML
    private TextField idTxtField;

    @FXML
    private TextField nameTxtField;

    @FXML
    void addBtnOnAction(ActionEvent event) {
        String duration = durationTxtField.getText();
        String fee = feeTxtField.getText();
        String id = idTxtField.getText();
        String name = nameTxtField.getText();

        programBO.addProgram(new ProgramDTO(id,name,duration,fee));
        clearFields();
    }

    private void clearFields() {
        durationTxtField.clear();
        feeTxtField.clear();
        idTxtField.clear();
        nameTxtField.clear();

    }

    @FXML
    void bacBtnOnAction(ActionEvent event) {
        dashboardController.loadView("programs");
    }

}
