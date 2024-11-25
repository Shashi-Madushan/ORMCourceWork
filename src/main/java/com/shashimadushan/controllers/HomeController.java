package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.bo.custom.StudentBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;

import static com.shashimadushan.utils.GlobelVars.userNameVar;
import static com.shashimadushan.utils.GlobelVars.userRole;

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
    private Label totalProgrmsLbl;

    @FXML
    private Label totalstudenLbl;

    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    ProgramBO programBO = (ProgramBO) BOFactory.getBO(BOFactory.BOType.PROGRAM);

    public void initialize() {
        if (userRole.equals("Admin")) {
           newProgramBtn.setVisible(true);
        }else{
            newProgramBtn.setVisible(false);
        }
        userNameLbel.setText(userNameVar);
       totalstudenLbl.setText(String.valueOf(studentBO.getStudentCount()));
       totalProgrmsLbl.setText(String.valueOf(programBO.getProgramCount()));
    }

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
