package com.shashimadushan.dao.custom;

 // Assuming 'Student' is a class in the organization's package

import com.shashimadushan.dao.SuperDAO;
import com.shashimadushan.entitys.Student;

import java.util.List;

public interface StudentDAO extends SuperDAO {
    public void addStudent(Student student);
    public List<Student> getAllStudents();
    public void updateStudent(Student student);
    public void deleteStudent(Student student);
    public int getStudentCount();
    public List<Student> searchStudents(String query);
}