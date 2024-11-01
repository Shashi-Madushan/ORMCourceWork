package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.shashimadushan.utils.ButtonUtils.setupButtonAnimation;
import static com.shashimadushan.utils.ButtonUtils.setupButtonClick;

public class DashboardController {

    @FXML
    private JFXButton dashbordBtn;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane navPane;

    @FXML
    private JFXButton programsBtn;

    @FXML
    private JFXButton repotsBtn;

    @FXML
    private JFXButton studBtn;

    @FXML
    private JFXButton usersBtn;

    public void initialize() {
        initButtonAnimation();
    }


    private void initButtonAnimation() {
        setupButtonAnimation(dashbordBtn);
        setupButtonAnimation(logoutBtn);
        setupButtonAnimation(programsBtn);
        setupButtonAnimation(repotsBtn);
        setupButtonAnimation(studBtn);
        setupButtonAnimation(usersBtn);

    }



    @FXML
    void dashBtnOnAction(ActionEvent event) {

    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) {

    }

    @FXML
    void programBtnOnACtion(ActionEvent event) {
        loadProgramsView();
    }

    @FXML
    void reportsBtnOnAction(ActionEvent event) {

    }

    @FXML
    void studentBtnOnAction(ActionEvent event) {
       loadStudentsView();
        setupButtonClick(studBtn);
    }

    @FXML
    void usersBtnOnAction(ActionEvent event) {

    }

    private void loadStudentsView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentsHome.fxml"));
            AnchorPane anchorPane = loader.load();


            AnchorPane.setTopAnchor(anchorPane, 3.0);
            AnchorPane.setBottomAnchor(anchorPane, 3.0);
            AnchorPane.setLeftAnchor(anchorPane, 3.0);
            AnchorPane.setRightAnchor(anchorPane, 3.0);

            mainPane.getChildren().setAll(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProgramsView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/programsHome.fxml"));
            AnchorPane anchorPane = loader.load();


            AnchorPane.setTopAnchor(anchorPane, 3.0);
            AnchorPane.setBottomAnchor(anchorPane, 3.0);
            AnchorPane.setLeftAnchor(anchorPane, 3.0);
            AnchorPane.setRightAnchor(anchorPane, 3.0);

            mainPane.getChildren().setAll(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
