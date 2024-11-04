package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;

import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

import static com.shashimadushan.utils.ButtonUtils.setupButtonAnimation;
import static com.shashimadushan.utils.ButtonUtils.setupButtonClick;
import static com.shashimadushan.utils.GlobelVars.userRole;

@Data
public class DashboardController {
  UserBO userBO = (UserBO) BOFactory.getBO(BOFactory.BOType.USER);

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
    Stage stage;

    public void initialize() {
        loadView("home");

        if (userRole.equals("Admin")) {
            usersBtn.setVisible(true);
        }else {
            usersBtn.setVisible(false);
        }
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
        setupButtonClick(dashbordBtn);
        loadView("home");
    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) {
        loardLoginPage();
    }



    @FXML
    void programBtnOnACtion(ActionEvent event) {
        loadView("programs");
        setupButtonClick(programsBtn);
    }

    @FXML
    void reportsBtnOnAction(ActionEvent event) {
        setupButtonClick(repotsBtn);
    }

    @FXML
    void studentBtnOnAction(ActionEvent event) {
        loadView("students");
        setupButtonClick(studBtn);
    }

    @FXML
    void usersBtnOnAction(ActionEvent event) {
        setupButtonClick(usersBtn);
        loadView("user");
    }

    private void loardLoginPage() {
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
    public void loadView(String viewType) {
    try {
        String resourcePath = "/view/";
        switch (viewType) {
            case "home":
                resourcePath += "home.fxml";
                break;
            case "students":
                resourcePath += "studentsHome.fxml";
                break;
            case "programs":
                resourcePath += "programsHome.fxml";
                break;
            case "crudpro":
                resourcePath += "crudPrograms.fxml";
                break;
            case "user":
                resourcePath += "crudUser.fxml";
                break;
            case "crudStd":
                resourcePath += "crudStudent.fxml";
                break;
            default:
                System.out.println("Invalid view type");
                return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        AnchorPane anchorPane = loader.load();

        Object controller = loader.getController();

        if (controller instanceof StudentsHomeController) {
            StudentsHomeController fxmlController = (StudentsHomeController) controller;
            fxmlController.setDashboardController(this);
        } else if (controller instanceof CrudStudentController) {
            CrudStudentController fxmlController = (CrudStudentController) controller;
            fxmlController.setDashboardController(this);
        } else if (controller instanceof ProgramsHomeController) {
            ProgramsHomeController fxmlController = (ProgramsHomeController) controller;
            fxmlController.setDashboardController(this);
        }  else if (controller instanceof CrudProgramsController) {
            CrudProgramsController fxmlController = (CrudProgramsController) controller;
            fxmlController.setDashboardController(this);
        } else if (controller instanceof CrudUserController) {
            CrudUserController fxmlController = (CrudUserController) controller;
            fxmlController.setDashboardController(this);
        } else if (controller instanceof HomeController) {
            HomeController fxmlController = (HomeController) controller;
            fxmlController.setDashboardController(this);
        } else {
            System.out.println("Controller is not an instance of YourFXMLControllerClass");
        }

        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);

        mainPane.getChildren().setAll(anchorPane);

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
