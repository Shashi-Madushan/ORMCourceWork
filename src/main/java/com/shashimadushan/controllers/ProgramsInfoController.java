package com.shashimadushan.controllers;

import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.awt.event.ActionEvent;
import java.util.List;

@Data
public class ProgramsInfoController {
    DashboardController dashboardController;
    ProgramDTO programDTO;
    List<StudentDTO> studentDTOList;

    @FXML
    private Label IDLbl;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<StudentDTO> enrolledStudents;

    @FXML
    private Label feeLbl;

    @FXML
    private Label programNameLbl;

    @FXML
    private Label stduntCount;
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    public void initialize() {
        // Initialize the table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));



    }

    public void loadData() {
        programNameLbl.setText(programDTO.getName());
        IDLbl.setText(programDTO.getProgramId());
        feeLbl.setText(String.valueOf(programDTO.getFee()));
        studentDTOList = studentBO.getStudentsEnrolledInProgram(programDTO.getProgramId());
        ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(studentDTOList);

        enrolledStudents.setItems(studentList);


        stduntCount.setText(String.valueOf(studentDTOList.size()));
    }


    public void backBtnOnAction(javafx.event.ActionEvent actionEvent) {
        dashboardController.loadView("programs");
    }
}
