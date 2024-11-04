package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.UserBO;
import com.shashimadushan.dto.UserDTO;
import com.shashimadushan.utils.PasswordUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lombok.Data;

import java.util.List;

@Data
public class CrudUserController {
    DashboardController dashboardController;

    @FXML
    private TableColumn<?, ?> coPwd;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colUname;
    @FXML
    private TableColumn<?, ?> colRole;


    @FXML
    private JFXButton addBtn;

    @FXML
    private TextField pwnTxtField;

    @FXML
    private JFXComboBox<String> roleComboBox;

    @FXML
    private TextField unameTxtField;


    @FXML
    private TableView<UserDTO> usersTbel;

    UserBO userBO = (UserBO) BOFactory.getBO(BOFactory.BOType.USER);
    String css = getClass().getResource("/styles/buttonStyles.css").toExternalForm();

    public void initialize() {
        addDataToTable();
        addButtonToUserTable();
        roleComboBox.getItems().addAll("Admin", "Coordinator");
    }

    private void addDataToTable() {
        List<UserDTO> users = userBO.getAllUsers();

        // Assuming the TableView usersTable is of type UserDTO
        ObservableList<UserDTO> userList = FXCollections.observableArrayList(users);

        // Assuming colId, colUname, and coPwd are TableColumn<UserDTO, ?> columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUname.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        coPwd.setCellValueFactory(new PropertyValueFactory<>("password"));

        usersTbel.setItems(userList);
    }

    @FXML
    void addBtnOnAction(ActionEvent event) {
        String username = unameTxtField.getText();
        String password = pwnTxtField.getText();
        String role = roleComboBox.getSelectionModel().getSelectedItem();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty() || role == null) {
            // Show warning alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all fields before adding a user.");
            alert.showAndWait();
            return;
        }

        // Hash the password
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Add user
        userBO.addUser(new UserDTO(username, hashedPassword, role));
        clearFields();
    }

    private void clearFields() {
        pwnTxtField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        unameTxtField.clear();

    }


    private void addButtonToUserTable() {
        TableColumn<UserDTO, Void> colAction = new TableColumn<>("Action");
        Callback<TableColumn<UserDTO, Void>, TableCell<UserDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<UserDTO, Void> call(final TableColumn<UserDTO, Void> param) {
                final TableCell<UserDTO, Void> cell = new TableCell<>() {
                    JFXButton updateButton = new JFXButton("Update");
                    JFXButton deleteButton = new JFXButton("Delete");

                    {
                        updateButton.getStylesheets().add(css);
                        deleteButton.getStylesheets().add(css);
                        updateButton.getStyleClass().add("update-button");
                        deleteButton.getStyleClass().add("delete-button");

                        updateButton.setOnAction((ActionEvent event) -> {
                            UserDTO user = getTableView().getItems().get(getIndex());
                            showUpdatePopup(user);
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            UserDTO user = getTableView().getItems().get(getIndex());


                            // Create a confirmation alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this user?");
                            alert.setContentText("User ID: " + user.getId() + "\nUsername: " + user.getUserName());

                            // Show the alert and wait for user response
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    // If user confirms, delete the user
                                    userBO.deleteUser(user);
                                    addDataToTable();
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
        usersTbel.getColumns().add(colAction);
    }

    private void showUpdatePopup(UserDTO user) {
        // Create the popup pane
        AnchorPane popupPane = new AnchorPane();
        popupPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-padding: 20;");
        popupPane.setPrefSize(300, 250);

        // Center the popup on the screen in an AnchorPane
        AnchorPane.setTopAnchor(popupPane, (usersTbel.getHeight() - 250) / 2); // 200 is popup height
        AnchorPane.setLeftAnchor(popupPane, (usersTbel.getWidth() - 300) / 2); // 300 is popup width

        // Add text fields to popup for editing fields
        TextField idField = new TextField(String.valueOf(user.getId()));
        TextField nameField = new TextField(user.getUserName());
        TextField passwordField = new TextField(user.getPassword());
        TextField roleField = new TextField(user.getRoleName());

        idField.setPromptText("ID");
        nameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        roleField.setPromptText("Role");

        // Position the text fields inside the popup pane
        AnchorPane.setTopAnchor(idField, 20.0);
        AnchorPane.setLeftAnchor(idField, 20.0);
        AnchorPane.setTopAnchor(nameField, 60.0);
        AnchorPane.setLeftAnchor(nameField, 20.0);
        AnchorPane.setTopAnchor(passwordField, 100.0);
        AnchorPane.setLeftAnchor(passwordField, 20.0);
        AnchorPane.setTopAnchor(roleField, 140.0);
        AnchorPane.setLeftAnchor(roleField, 20.0);

        // Add buttons inside popup to confirm or cancel the update
        JFXButton confirmUpdateButton = new JFXButton("Confirm");
        confirmUpdateButton.getStylesheets().add(css);
        confirmUpdateButton.getStyleClass().add("update-button");
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.getStylesheets().add(css);
        cancelButton.getStyleClass().add("delete-button");

        AnchorPane.setBottomAnchor(confirmUpdateButton, 1.0);
        AnchorPane.setRightAnchor(confirmUpdateButton, 20.0);
        AnchorPane.setBottomAnchor(cancelButton, 1.0);
        AnchorPane.setLeftAnchor(cancelButton, 20.0);

        popupPane.getChildren().addAll(idField, nameField, passwordField, roleField, confirmUpdateButton, cancelButton);

        // Confirm update button action
        confirmUpdateButton.setOnAction((ActionEvent event) -> {
            // Update the user with new values from text fields
            user.setUserName(nameField.getText());
            user.setPassword(PasswordUtil.hashPassword(passwordField.getText())); // Hash the password
            user.setRoleName(roleField.getText());

            // Save updated user to database
            userBO.updateUser(user);
            usersTbel.refresh(); // Refresh the table
            popupPane.setVisible(false); // Hide the popup after update
        });

        // Cancel button action
        cancelButton.setOnAction((ActionEvent event) -> {
            popupPane.setVisible(false); // Hide the popup without making changes
        });

        // Assuming rootPane is an AnchorPane or similar container
        dashboardController.getMainPane().getChildren().add(popupPane);
        popupPane.setVisible(true);
    }

}
