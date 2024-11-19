package com.shashimadushan.dto;

import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import lombok.Data;


import java.time.LocalDate;
@Data
public class EnrolmentDTO {

    private int id;
    private LocalDate registrationDate;
    private double payment;
    private StudentDTO student;
    private ProgramDTO program;
}
