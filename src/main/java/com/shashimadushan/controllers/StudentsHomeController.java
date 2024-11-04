package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class StudentsHomeController {

    DashboardController dashboardController;
    @FXML
    private JFXButton addStudentBtn;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colTp;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<StudentDTO> studentTabel;

    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    String css = getClass().getResource("/styles/buttonStyles.css").toExternalForm();

    public void initialize() {
        initTabel();
        addButtonToStudentTable();
        loadDataToTabel();
        addDoubleClickEventToTable();
        addTooltipToTable();
        setupStudentSearchFilter();
    }

    private void addTooltipToTable() {
        Tooltip tooltip = new Tooltip("Double click for more info");
        Tooltip.install(studentTabel, tooltip);
    }

    private void addDoubleClickEventToTable() {
        studentTabel.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                StudentDTO selectedStudent = studentTabel.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    System.out.println("2click on: " + selectedStudent.getId());
                    loadCrudStudentViewWithStudentData(selectedStudent);
                }
            }
        });
    }

    private void setupStudentSearchFilter() {
        List<StudentDTO> students = studentBO.getAllStudents();
        ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(students);

        if (studentList != null) {
            FilteredList<StudentDTO> filteredList = new FilteredList<>(studentList, student -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all students if the search box is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return student.getId().toLowerCase().contains(lowerCaseFilter); // Filter by student ID
                });
            });

            studentTabel.setItems(filteredList);
        }
    }

    private void loadCrudStudentViewWithStudentData(StudentDTO student) {
        AnchorPane mainPane = dashboardController.getMainPane();
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crudStudent.fxml"));
            AnchorPane anchorPane = loader.load();

            // Get the controller associated with the FXML
            CrudStudentController controller = loader.getController();
            controller.setInfoStudentDto(student);
            controller.setDashboardController(this.dashboardController);
            controller.lodeDatamode();


            // Set the loaded AnchorPane into the mainPane
            AnchorPane.setTopAnchor(anchorPane, 0.0);
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            AnchorPane.setRightAnchor(anchorPane, 0.0);

            mainPane.getChildren().setAll(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTabel() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTp.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void loadDataToTabel() {
        List<StudentDTO> students = studentBO.getAllStudents();
        ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(students);
        studentTabel.setItems(studentList);
    }

    @FXML
    void addStudentBtnOnAction(ActionEvent event) {
        dashboardController.loadView("crudStd");
    }

    private void addButtonToStudentTable() {
        TableColumn<StudentDTO, Void> colAction = new TableColumn<>("Action");
        Callback<TableColumn<StudentDTO, Void>, TableCell<StudentDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<StudentDTO, Void> call(final TableColumn<StudentDTO, Void> param) {
                final TableCell<StudentDTO, Void> cell = new TableCell<>() {
                    JFXButton updateButton = new JFXButton("Update");
                    JFXButton deleteButton = new JFXButton("Delete");

                    {
                        updateButton.getStylesheets().add(css);
                        deleteButton.getStylesheets().add(css);
                        updateButton.getStyleClass().add("update-button");
                        deleteButton.getStyleClass().add("delete-button");

                        updateButton.setOnAction((ActionEvent event) -> {
                            StudentDTO student = getTableView().getItems().get(getIndex());
                            //showUpdatePopup(student);
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            StudentDTO student = getTableView().getItems().get(getIndex());

                            // Create a confirmation alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this student?");
                            alert.setContentText("Student ID: " + student.getId() + "\nName: " + student.getFirstName() + " " + student.getLastName());

                            // Show the alert and wait for user response
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    // If user confirms, delete the student
                                    studentBO.deleteStudent(student);
                                    loadDataToTabel();
                                }
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(updateButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };

        colAction.setCellFactory(cellFactory);
        studentTabel.getColumns().add(colAction);
    }

}
