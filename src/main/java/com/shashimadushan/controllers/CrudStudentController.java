package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.EnrollmentsBO;
import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.dto.EnrolmentDTO;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.dto.StudentDTO;
import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import com.shashimadushan.utils.Regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;
import javafx.scene.control.CheckBox;


import java.time.LocalDate;
import java.util.*;

import javafx.scene.control.TextInputDialog;

@Data
public class CrudStudentController {
    DashboardController dashboardController;
    ProgramBO programBO = (ProgramBO) BOFactory.getBO(BOFactory.BOType.PROGRAM);
    String mode = null;
    StudentDTO infoStudentDto;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updatebtn;

    @FXML
    private TextField addressTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private TextField fnameTxtField;

    @FXML
    private TextField lnameTxtField;

    @FXML
    private VBox programsVbox;

    @FXML
    private TextField studentIdTxtField;

    @FXML
    private TextField tpTxtField;

    EnrollmentsBO enrollmentsBO = (EnrollmentsBO) BOFactory.getBO(BOFactory.BOType.ENROLLMENT);
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    private final Map<String, Double> programPayments = new HashMap<>(); // Stores payment for each program
    private boolean isPopulating = false; // Flag to track if data is being loaded programmatically

    public void initialize() {
        loadPrograms();
        updatebtn.setVisible(false);
    }

    public void lodeDatamode() {
        System.out.println("manual");
        populateStudentInfo();
        updatebtn.setVisible(true);
        addBtn.setVisible(false);
    }

private void loadPrograms() {
    List<ProgramDTO> programDTOS = programBO.getAllPrograms();
    programsVbox.getChildren().clear();
    programsVbox.getStyleClass().add("vbox-container"); // Apply VBox style

    for (ProgramDTO programDTO : programDTOS) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getStyleClass().add("hbox-container"); // Apply HBox style

        CheckBox checkBox = new CheckBox(programDTO.getName());
        checkBox.getStyleClass().add("check-box"); // Apply CheckBox style

        Label paymentLabel = new Label("Payed: 0.0"); // Default payment
        paymentLabel.getStyleClass().add("label"); // Apply Label style

        Label remainingLabel = new Label("Remaining: " + programDTO.getFee());
        remainingLabel.getStyleClass().add("label"); // Apply Label style

        checkBox.setId(programDTO.getProgramId());
        checkBox.setUserData(programDTO);

        // Event listener to handle checkbox selection
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && !isPopulating) {
                double payment = popupForPayment(programDTO);
                programPayments.put(programDTO.getProgramId(), payment);
                paymentLabel.setText("Payed: " + payment);
                double remaining = programDTO.getFee() - payment;
                remainingLabel.setText("Remaining: " + remaining);
            } else {
                programPayments.remove(programDTO.getProgramId());
                paymentLabel.setText("Payed: 0.0");
                remainingLabel.setText("Remaining: " + programDTO.getFee());
            }
        });

        hbox.getChildren().addAll(checkBox, paymentLabel, remainingLabel);
        programsVbox.getChildren().add(hbox);
    }
}

    private void populateStudentInfo() {
        if (infoStudentDto != null) {
            isPopulating = true;
            studentIdTxtField.setText(infoStudentDto.getId());
            fnameTxtField.setText(infoStudentDto.getFirstName());
            lnameTxtField.setText(infoStudentDto.getLastName());
            addressTxtField.setText(infoStudentDto.getAddress());
            emailTxtField.setText(infoStudentDto.getEmail());
            tpTxtField.setText(infoStudentDto.getPhone());

            List<EnrolmentDTO> enrollments = infoStudentDto.getEnrollments();
            if (enrollments != null) {
                for (EnrolmentDTO enrolment : enrollments) {
                    ProgramDTO program = enrolment.getProgram();
                    if (program != null) {
                        for (Node node : programsVbox.getChildren()) {
                            if (node instanceof HBox hbox) {
                                CheckBox checkBox = null;
                                Label paymentLabel = null;
                                Label remainingLabel = null;

                                for (Node childNode : hbox.getChildren()) {
                                    if (childNode instanceof CheckBox cb && cb.getId().equals(program.getProgramId())) {
                                        checkBox = cb;
                                    }
                                    if (childNode instanceof Label label) {
                                        if (label.getText().startsWith("Payed:")) {
                                            paymentLabel = label;
                                        } else if (label.getText().startsWith("Remaining:")) {
                                            remainingLabel = label;
                                        }
                                    }
                                }

                                if (checkBox != null && paymentLabel != null && remainingLabel != null) {
                                    checkBox.setSelected(true);
                                    double payment = enrolment.getPayment();
                                    programPayments.put(program.getProgramId(), payment);
                                    paymentLabel.setText("Payed: " + payment);
                                    double remaining = program.getFee() - payment;
                                    remainingLabel.setText("Remaining: " + remaining);
                                }
                            }
                        }
                    }
                }
            }
            isPopulating = false; // End programmatic update
        }
    }

    private double popupForPayment(ProgramDTO program) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Payment");
        dialog.setHeaderText("Enter payment for the program: " + program.getName());
        dialog.setContentText("Current Fee: " + program.getFee() + "\nPayment:");

        while (true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String input = result.get().trim();
                try {
                    return Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid number.");
                    alert.showAndWait();
                }
            } else {
                // User cancelled the dialog
                return 0.0;
            }
        }
    }

    @FXML
    void addBtnOnAction(ActionEvent event) {
        if (!validateFields()) return;

        List<ProgramDTO> selectedPrograms = programsVbox.getChildren().stream()
                .filter(node -> node instanceof HBox hbox && hbox.getChildren().get(0) instanceof CheckBox checkBox && checkBox.isSelected())
                .map(hbox -> (ProgramDTO) ((CheckBox) ((HBox) hbox).getChildren().get(0)).getUserData())
                .toList();

        StudentDTO student = createStudentDTO();
        List<EnrolmentDTO> enrolmentDTOList = createEnrollmentDTOs(student, selectedPrograms);

        student.setEnrollments(enrolmentDTOList);
        studentBO.addStudent(student);
        enrollmentsBO.saveEnrolments(enrolmentDTOList);


        showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
        clearFields();
    }

    private StudentDTO createStudentDTO() {
        StudentDTO student = new StudentDTO();
        student.setId(studentIdTxtField.getText());
        student.setFirstName(fnameTxtField.getText());
        student.setLastName(lnameTxtField.getText());
        student.setAddress(addressTxtField.getText());
        student.setEmail(emailTxtField.getText());
        student.setPhone(tpTxtField.getText());
        return student;
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (!Regex.setTextColor(Regex.FieldType.NAME, fnameTxtField)) isValid = false;
        if (!Regex.setTextColor(Regex.FieldType.NAME, lnameTxtField)) isValid = false;
        if (!Regex.setTextColor(Regex.FieldType.ADDRESS, addressTxtField)) isValid = false;
        if (!Regex.setTextColor(Regex.FieldType.EMAIL, emailTxtField)) isValid = false;
        if (!Regex.setTextColor(Regex.FieldType.TEL, tpTxtField)) isValid = false;
        if (!Regex.setTextColor(Regex.FieldType.STUDENTID, studentIdTxtField)) isValid = false;

        if (!isValid) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please correct the highlighted fields.");
        }
        return isValid;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<EnrolmentDTO> createEnrollmentDTOs(StudentDTO student, List<ProgramDTO> selectedPrograms) {
        return selectedPrograms.stream().map(program -> {
            EnrolmentDTO enrolmentDTO = new EnrolmentDTO();
            enrolmentDTO.setRegistrationDate(LocalDate.now());
            enrolmentDTO.setPayment(programPayments.getOrDefault(program.getProgramId(), 0.0));
            enrolmentDTO.setStudent(new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getAddress(), student.getEmail(), student.getPhone(), student.getEnrollments()));
            enrolmentDTO.setProgram(new ProgramDTO(program.getProgramId(), program.getName(), program.getDurationMonths(), program.getFee(), program.getEnrollments()));
            return enrolmentDTO;
        }).toList();
    }

    private void clearFields() {
        studentIdTxtField.clear();
        fnameTxtField.clear();
        lnameTxtField.clear();
        addressTxtField.clear();
        emailTxtField.clear();
        tpTxtField.clear();
        programsVbox.getChildren().stream()
                .filter(node -> node instanceof HBox hbox && hbox.getChildren().get(0) instanceof CheckBox checkBox)
                .forEach(node -> {
                    CheckBox checkBox = (CheckBox) ((HBox) node).getChildren().get(0);
                    checkBox.setSelected(false);
                    ((Label) ((HBox) node).getChildren().get(1)).setText("Payment: 0.0");
                });
        programPayments.clear();
    }

    @FXML
    void addresValidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.ADDRESS, addressTxtField);
    }


    @FXML
    void emailValidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.EMAIL, emailTxtField);
    }

    @FXML
    void nameVaidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.NAME, fnameTxtField);
    }

    @FXML
    void nameValidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.NAME, lnameTxtField);
    }

    @FXML
    void phoneValidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.TEL, tpTxtField);
    }

    @FXML
    void studentIdValidation(KeyEvent event) {
        Regex.setTextColor(Regex.FieldType.STUDENTID, studentIdTxtField);
    }

    @FXML
    public void updateBtnOnAction(ActionEvent actionEvent) {
        // Validate input fields
        boolean isValid = true;

        if (!Regex.setTextColor(Regex.FieldType.NAME, fnameTxtField)) {
            isValid = false;
        }
        if (!Regex.setTextColor(Regex.FieldType.NAME, lnameTxtField)) {
            isValid = false;
        }
        if (!Regex.setTextColor(Regex.FieldType.ADDRESS, addressTxtField)) {
            isValid = false;
        }
        if (!Regex.setTextColor(Regex.FieldType.EMAIL, emailTxtField)) {
            isValid = false;
        }
        if (!Regex.setTextColor(Regex.FieldType.TEL, tpTxtField)) {
            isValid = false;
        }
        if (!Regex.setTextColor(Regex.FieldType.STUDENTID, studentIdTxtField)) {
            isValid = false;
        }

        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please correct the highlighted fields.");
            alert.showAndWait();
            return;
        }
        StudentDTO student = new StudentDTO();
        student.setId(studentIdTxtField.getText());
        student.setFirstName(fnameTxtField.getText());
        student.setLastName(lnameTxtField.getText());
        student.setAddress(addressTxtField.getText());
        student.setEmail(emailTxtField.getText());
        student.setPhone(tpTxtField.getText());


        // Gather selected programs and their payment details
        List<EnrolmentDTO> enrolmentDTOList = new ArrayList<>();
        for (Node node : programsVbox.getChildren()) {
            if (node instanceof HBox hbox) {
                CheckBox checkBox = (CheckBox) hbox.getChildren().get(0);
                Label paymentLabel = (Label) hbox.getChildren().get(1);

                if (checkBox.isSelected()) {
                    ProgramDTO program = (ProgramDTO) checkBox.getUserData();
                    EnrolmentDTO enrolment = new EnrolmentDTO();
                    enrolment.setProgram(new ProgramDTO(program.getProgramId(), program.getName(), program.getDurationMonths(), program.getFee(), program.getEnrollments()));
                    try {
                        String paymentText = paymentLabel.getText().replace("Payed: ", "").trim();
                        enrolment.setPayment(Double.parseDouble(paymentText));
                    } catch (NumberFormatException e) {
                        // Handle the error, e.g., log it or show an alert to the user
                        showAlert(Alert.AlertType.ERROR, "Invalid Payment", "The payment amount is not a valid number.");
                    }
                    enrolment.setRegistrationDate(LocalDate.now());
                    enrolment.setStudent(new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getAddress(), student.getEmail(), student.getPhone(), student.getEnrollments()));
                    enrolmentDTOList.add(enrolment);
                }
            }
        }

        // Update the student object

        student.setEnrollments(enrolmentDTOList);
        studentBO.updateStudent(student);
        enrollmentsBO.updateEnrolments(enrolmentDTOList);
        // Update student via BO


        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Student updated successfully!");
        alert.showAndWait();

        // Clear fields
        clearFields();

        // Redirect to the student list view
        dashboardController.loadView("students");
    }

    @FXML
    public void backBtnOnAction(ActionEvent actionEvent) {
        dashboardController.loadView("students");
    }

}

