package com.shashimadushan.controllers;

import com.jfoenix.controls.JFXButton;
import com.shashimadushan.bo.BOFactory;
import com.shashimadushan.bo.custom.EnrolmentBO;
import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.config.FactoryConfiguration;
import com.shashimadushan.dto.EnrolmetDTO;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.dto.StudentDTO;
import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import com.shashimadushan.utils.Regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import lombok.Data;
import javafx.scene.control.CheckBox;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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


    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    EnrolmentBO enrolmentBO = (EnrolmentBO) BOFactory.getBO(BOFactory.BOType.ENROLMENT);

    public void initialize() {
        loadPrograms();
        updatebtn.setVisible(false);

    }

    public void lodeDatamode(){
        System.out.println("maual");
        populateStudentInfo();
        updatebtn.setVisible(true);
        addBtn.setVisible(false);
    }

    private void populateStudentInfo() {
        if (infoStudentDto != null) {
            studentIdTxtField.setText(infoStudentDto.getId());
            fnameTxtField.setText(infoStudentDto.getFirstName());
            lnameTxtField.setText(infoStudentDto.getLastName());
            addressTxtField.setText(infoStudentDto.getAddress());
            emailTxtField.setText(infoStudentDto.getEmail());
            tpTxtField.setText(infoStudentDto.getPhone());

            // Mark the checkboxes for the enrolled programs
            List<Enrolment> enrollments = infoStudentDto.getEnrollments();
            if (enrollments != null) {
                for (Enrolment enrolment : enrollments) {
                    Program program = enrolment.getProgram();
                    if (program != null) {
                        programsVbox.getChildren().stream()
                                .filter(node -> node instanceof CheckBox)
                                .map(node -> (CheckBox) node)
                                .filter(checkBox -> checkBox.getId().equals(program.getProgramId()))
                                .forEach(checkBox -> checkBox.setSelected(true));
                    }
                }
            }
        }
    }
    @FXML
    void addBtnOnAction(ActionEvent event) {
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

        // Gather selected programs
        List<ProgramDTO> selectedPrograms = programsVbox.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .map(node -> (CheckBox) node)
                .filter(CheckBox::isSelected)
                .map(checkBox -> (ProgramDTO) checkBox.getUserData())
                .toList();



        // Create a new student object (assuming you have a StudentDTO class)
        StudentDTO student = new StudentDTO();
        student.setId(studentIdTxtField.getText());
        student.setFirstName(fnameTxtField.getText());
        student.setLastName(lnameTxtField.getText());
        student.setAddress(addressTxtField.getText());
        student.setEmail(emailTxtField.getText());
        student.setPhone(tpTxtField.getText());

        List<Enrolment> enrolmentDTOList =createEnrollmentDTOs(student,selectedPrograms);

        student.setEnrollments(enrolmentDTOList);

        // Add the student using the appropriate BO method (assuming you have a StudentBO)

        studentBO.addStudent(student);

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Student added successfully!");
        alert.showAndWait();

        // Clear fields after adding
        clearFields();
    }

  @FXML
void updateBtnOnAction(ActionEvent actionEvent) {
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

    // Gather selected programs
    List<ProgramDTO> selectedPrograms = programsVbox.getChildren().stream()
            .filter(node -> node instanceof CheckBox)
            .map(node -> (CheckBox) node)
            .filter(CheckBox::isSelected)
            .map(checkBox -> (ProgramDTO) checkBox.getUserData())
            .toList();

    // Update the student object
    StudentDTO student = new StudentDTO();
    student.setId(studentIdTxtField.getText());
    student.setFirstName(fnameTxtField.getText());
    student.setLastName(lnameTxtField.getText());
    student.setAddress(addressTxtField.getText());
    student.setEmail(emailTxtField.getText());
    student.setPhone(tpTxtField.getText());

    List<Enrolment> enrolmentDTOList = createEnrollmentDTOs(student, selectedPrograms);
    student.setEnrollments(enrolmentDTOList);

    // Update the student using the appropriate BO method
    studentBO.updateStudent(student);

    // Show success message
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("Student updated successfully!");
    alert.showAndWait();

    // Clear fields after updating
    clearFields();
    dashboardController.loadView("students");
}

    private List<Enrolment> createEnrollmentDTOs(StudentDTO student, List<ProgramDTO> selectedPrograms) {
        List<Enrolment> enrolmentDTOList = new ArrayList<>();

        for (ProgramDTO program : selectedPrograms) {
            Enrolment enrolmentDTO = new Enrolment();
            enrolmentDTO.setId(0); // Assuming ID is auto-generated
            enrolmentDTO.setRegistrationDate(LocalDate.now()); // Set current date as registration date
            enrolmentDTO.setPayment(0.0); // Set default payment, modify as needed
            enrolmentDTO.setStudent(new Student(student.getId(), student.getFirstName(), student.getLastName(), student.getAddress(),  student.getEmail(), student.getPhone(),student.getEnrollments()));
            enrolmentDTO.setProgram(new Program(program.getProgramId(), program.getName(), program.getDurationMonths(), program.getFee(), program.getEnrollments()));
            enrolmentDTOList.add(enrolmentDTO);
        }

        return enrolmentDTOList;
    }


    private void clearFields() {
        studentIdTxtField.clear();
        fnameTxtField.clear();
        lnameTxtField.clear();
        addressTxtField.clear();
        emailTxtField.clear();
        tpTxtField.clear();
        programsVbox.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .forEach(node -> ((CheckBox) node).setSelected(false));
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        dashboardController.loadView("students");
    }


    private void loadPrograms() {

        List<ProgramDTO> programDTOS = programBO.getAllPrograms();


        programsVbox.getChildren().clear();


        for (ProgramDTO programDTO : programDTOS) {

            CheckBox checkBox = new CheckBox(programDTO.getName());


            checkBox.setId(programDTO.getProgramId());
            checkBox.setUserData(programDTO);


            programsVbox.getChildren().add(checkBox);
        }
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


}

