package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.dto.ProgramDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lombok.Data;

import java.util.List;

@Data
public class ProgramsHomeController {
    DashboardController dashboardController;
    @FXML
    private JFXButton addProgramBtn;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;


    @FXML
    private TableView<ProgramDTO> programsTabel;

    @FXML
    private TextField searchField;

    ProgramBO programBO = (ProgramBO) BOFactory.getBO(BOFactory.BOType.PROGRAM);
    String css = getClass().getResource("/styles/buttonStyles.css").toExternalForm();

    public void initialize() {

        loadData();
        addButtonToTable();
        addTooltipToTable();
        addDoubleClickEventToTable();
        setupProgramSearchFilter();
    }

    private void addTooltipToTable() {
        Tooltip tooltip = new Tooltip("Double click for more info");
        Tooltip.install(programsTabel, tooltip);
    }

    private void addDoubleClickEventToTable() {
        programsTabel.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                ProgramDTO selectedProgram = programsTabel.getSelectionModel().getSelectedItem();
                if (selectedProgram != null) {
                    System.out.println("2click on: " + selectedProgram.getProgramId());
                    loadProgramInfo(selectedProgram);
                }
            }
        });
    }

    private void loadProgramInfo(ProgramDTO selectedProgram) {
        AnchorPane mainPane = dashboardController.getMainPane();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/programInfo.fxml"));
            AnchorPane anchorPane = loader.load();

            // Get the controller associated with the FXML
            ProgramsInfoController controller = loader.getController();

            controller.setDashboardController(this.dashboardController);
            controller.setProgramDTO(selectedProgram);
            controller.loadData();


            // Set the loaded AnchorPane into the mainPane
            AnchorPane.setTopAnchor(anchorPane, 0.0);
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            AnchorPane.setRightAnchor(anchorPane, 0.0);

            mainPane.getChildren().setAll(anchorPane);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupProgramSearchFilter() {
        List<ProgramDTO> programs = programBO.getAllPrograms();
        ObservableList<ProgramDTO> programList = FXCollections.observableArrayList(programs);

        if (programList != null) {
            FilteredList<ProgramDTO> filteredList = new FilteredList<>(programList, program -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(program -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all programs if the search box is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return program.getName().toLowerCase().contains(lowerCaseFilter); // Filter by program name
                });
            });

            programsTabel.setItems(filteredList);
        }
    }

    private void addButtonToTable() {
        TableColumn<ProgramDTO, Void> colAction = new TableColumn<>("Action");
        Callback<TableColumn<ProgramDTO, Void>, TableCell<ProgramDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProgramDTO, Void> call(final TableColumn<ProgramDTO, Void> param) {
                final TableCell<ProgramDTO, Void> cell = new TableCell<>() {
                    JFXButton updateButton = new JFXButton("Update");
                    JFXButton deleteButton = new JFXButton("Delete");


                    {

                        updateButton.getStylesheets().add(css);
                        deleteButton.getStylesheets().add(css);
                        updateButton.getStyleClass().add("update-button");
                        deleteButton.getStyleClass().add("delete-button");
                        updateButton.setOnAction((ActionEvent event) -> {
                            ProgramDTO program = getTableView().getItems().get(getIndex());
                            showUpdatePopup(program);

                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            ProgramDTO program = getTableView().getItems().get(getIndex());

                            // Create a confirmation alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this program?");
                            alert.setContentText("Program ID: " + program.getProgramId() + "\nName: " + program.getName());

                            // Show the alert and wait for user response
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    // If user confirms, delete the program
                                    programBO.deleteProgram(program);
                                    loadData();
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
        programsTabel.getColumns().add(colAction);
    }


    private void loadData() {
        List<ProgramDTO> programs = programBO.getAllPrograms();
        ObservableList<ProgramDTO> programList = FXCollections.observableArrayList(programs);
        colId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationMonths"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        programsTabel.setItems(programList);
    }

    @FXML
    void addProgramBtnOnAction(ActionEvent event) {
        dashboardController.loadView("crudpro");
    }


    private void showUpdatePopup(ProgramDTO program) {
        AnchorPane mainPane = dashboardController.getMainPane();
        // Create the popup pane
        AnchorPane popupPane = new AnchorPane();
        popupPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-padding: 20;");
        popupPane.setPrefSize(300, 250);

        // Center the popup on the screen in an AnchorPane
        AnchorPane.setTopAnchor(popupPane, (mainPane.getHeight() - 200) / 2); // 200 is popup height
        AnchorPane.setLeftAnchor(popupPane, (mainPane.getWidth() - 300) / 2); // 300 is popup width

        // Add text fields to popup for editing fields
        TextField idField = new TextField(program.getProgramId());
        TextField nameField = new TextField(program.getName());
        TextField durationField = new TextField(String.valueOf(program.getDurationMonths()));
        TextField feeField = new TextField(String.valueOf(program.getFee()));

        idField.setPromptText("ID");
        nameField.setPromptText("Name");
        durationField.setPromptText("Duration");
        feeField.setPromptText("Fee");

        // Position the text fields inside the popup pane
        AnchorPane.setTopAnchor(idField, 20.0);
        AnchorPane.setLeftAnchor(idField, 20.0);
        AnchorPane.setTopAnchor(nameField, 60.0);
        AnchorPane.setLeftAnchor(nameField, 20.0);
        AnchorPane.setTopAnchor(durationField, 100.0);
        AnchorPane.setLeftAnchor(durationField, 20.0);
        AnchorPane.setTopAnchor(feeField, 140.0);
        AnchorPane.setLeftAnchor(feeField, 20.0);

        // Add a button inside popup to confirm the update
        JFXButton confirmUpdateButton = new JFXButton("Confirm Update");
        confirmUpdateButton.getStylesheets().add(css);
        confirmUpdateButton.getStyleClass().add("update-button");
        AnchorPane.setBottomAnchor(confirmUpdateButton, 1.0);
        AnchorPane.setRightAnchor(confirmUpdateButton, 10.0);

        // Add a button inside popup to cancel the update
        JFXButton cancelUpdateButton = new JFXButton("Cancel");
        cancelUpdateButton.getStylesheets().add(css);
        cancelUpdateButton.getStyleClass().add("delete-button");
        AnchorPane.setBottomAnchor(cancelUpdateButton, 1.0);
        AnchorPane.setLeftAnchor(cancelUpdateButton, 10.0);

        popupPane.getChildren().addAll(idField, nameField, durationField, feeField, cancelUpdateButton, confirmUpdateButton);

        // Cancel update button action
        cancelUpdateButton.setOnAction((ActionEvent event) -> {
            popupPane.setVisible(false); // Hide the popup without updating
        });

        // Confirm update button action
        confirmUpdateButton.setOnAction((ActionEvent event) -> {
            // Update the program with new values from text fields
            program.setProgramId(idField.getText());
            program.setName(nameField.getText());
            program.setDurationMonths(Integer.parseInt(durationField.getText()));
            program.setFee(Double.parseDouble(feeField.getText()));

            // Save updated program to database
            programBO.updateProgram(program);

            popupPane.setVisible(false);// Hide the popup after update
            loadData();
        });

        mainPane.getChildren().add(popupPane);
        popupPane.setVisible(true);
    }


}
