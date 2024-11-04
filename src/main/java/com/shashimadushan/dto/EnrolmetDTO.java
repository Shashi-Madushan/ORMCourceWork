package com.shashimadushan.dto;

import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;
import lombok.Data;


import java.time.LocalDate;
@Data
public class EnrolmetDTO {

    private int id;
    private LocalDate registrationDate;
    private double payment;
    private Student student;
    private Program program;
}
