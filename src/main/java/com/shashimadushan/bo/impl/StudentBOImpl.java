package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.StudentBO;
import com.shashimadushan.dao.DAOFactory;
import com.shashimadushan.dao.custom.StudentDAO;
import com.shashimadushan.dto.StudentDTO;
import com.shashimadushan.entitys.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public void addStudent(StudentDTO studentDTO) {
        studentDAO.addStudent(new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),studentDTO.getEnrollments()));

    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> allStudent = studentDAO.getAllStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : allStudent) {
            studentDTOS.add(new StudentDTO(student.getId(),student.getFirstName(),student.getLastName(),student.getAddress(),student.getEmail(),student.getPhone(),student.getEnrollments()));
        }
        return studentDTOS;
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),studentDTO.getEnrollments());
        studentDAO.updateStudent(student);
    }

    @Override
    public void deleteStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(),studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getPhone(),studentDTO.getEnrollments());
        studentDAO.deleteStudent(student);
    }

    @Override
    public List<StudentDTO> searchStudents(String query) {
        return List.of();
    }
}
