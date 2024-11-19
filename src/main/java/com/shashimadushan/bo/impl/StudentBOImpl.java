package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.dao.DAOFactory;
import com.shashimadushan.dao.custom.StudentDAO;
import com.shashimadushan.dto.EnrolmentDTO;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.dto.StudentDTO;
import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public void addStudent(StudentDTO studentDTO) {
        studentDAO.addStudent(new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),toEntity(studentDTO.getEnrollments())));

    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> allStudent = studentDAO.getAllStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : allStudent) {
            studentDTOS.add(new StudentDTO(student.getId(),student.getFirstName(),student.getLastName(),student.getAddress(),student.getEmail(),student.getPhone(),toDto(student.getEnrollments())));
        }
        return studentDTOS;
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),toEntity(studentDTO.getEnrollments()));
        studentDAO.updateStudent(student);
    }

    @Override
    public void deleteStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),toEntity(studentDTO.getEnrollments()));
        studentDAO.deleteStudent(student);
    }

    @Override
    public List<StudentDTO> searchStudents(String query) {
        return List.of();
    }

    @Override
    public List<StudentDTO> getStudentsEnrolledInProgram(String programId) {
      List<Student> enrolledstudentList =  studentDAO.getStudentsEnrolledInProgram(programId);
      List<StudentDTO> studentDTOS = new ArrayList<>();
      for (Student student : enrolledstudentList) {
          studentDTOS.add(new StudentDTO(student.getId(),student.getFirstName(),student.getLastName(),student.getAddress(),student.getEmail(),student.getPhone(),toDto(student.getEnrollments())));
      }
      return studentDTOS;
    }
    public int getStudentCount(){
     return    studentDAO.getStudentCount();
    }
    public List<StudentDTO> getStudentsEnrolledInAllPrograms(){
        List<Student> enrolledstudentList =  studentDAO.getStudentsEnrolledInAllPrograms();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : enrolledstudentList) {
            studentDTOS.add(new StudentDTO(student.getId(),student.getFirstName(),student.getLastName(),student.getAddress(),student.getEmail(),student.getPhone(),toDto(student.getEnrollments())));
        }
        return studentDTOS;
    }

private List<EnrolmentDTO> toDto(List<Enrolment> enrolments) {
    List<EnrolmentDTO> enrolmentDTOs = new ArrayList<>();

    for (Enrolment enrolment : enrolments) {
        EnrolmentDTO enrolmentDTO = new EnrolmentDTO();
        enrolmentDTO.setId(enrolment.getId());
        enrolmentDTO.setRegistrationDate(enrolment.getRegistrationDate());
        enrolmentDTO.setPayment(enrolment.getPayment());

        ProgramDTO programDTO = new ProgramDTO(
            enrolment.getProgram().getProgramId(),
            enrolment.getProgram().getName(),
            enrolment.getProgram().getDuration(),
            enrolment.getProgram().getFee(),
                null
        );
        enrolmentDTO.setProgram(programDTO);

        enrolmentDTOs.add(enrolmentDTO);
    }

    return enrolmentDTOs;
}
private List<Enrolment> toEntity(List<EnrolmentDTO> enrolmentDTOs) {
    List<Enrolment> enrolments = new ArrayList<>();

    for (EnrolmentDTO enrolmentDTO : enrolmentDTOs) {
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentDTO.getId());
        enrolment.setRegistrationDate(enrolmentDTO.getRegistrationDate());
        enrolment.setPayment(enrolmentDTO.getPayment());


        Program program = new Program(
            enrolmentDTO.getProgram().getProgramId(),
            enrolmentDTO.getProgram().getName(),
            enrolmentDTO.getProgram().getDurationMonths(),
            enrolmentDTO.getProgram().getFee(), null
        );
        enrolment.setProgram(program);

        enrolments.add(enrolment);
    }

    return enrolments;
}
}
