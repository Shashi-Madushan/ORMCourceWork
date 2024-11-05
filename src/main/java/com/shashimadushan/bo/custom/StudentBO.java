package com.shashimadushan.bo.custom;

import com.shashimadushan.bo.SuperBO;

import com.shashimadushan.dto.StudentDTO;


import java.util.List;

public interface StudentBO extends SuperBO {
    public void addStudent(StudentDTO studentDTO);

    public List<StudentDTO> getAllStudents();

    public void updateStudent(StudentDTO studentDTO);

    public void deleteStudent(StudentDTO studentDTO);

    public List<StudentDTO> searchStudents(String query);
    public List<StudentDTO> getStudentsEnrolledInProgram(String programId);
    public int getStudentCount();
    public List<StudentDTO> getStudentsEnrolledInAllPrograms();

}