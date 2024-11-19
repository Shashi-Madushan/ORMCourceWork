package com.shashimadushan.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate registrationDate;
    private double payment;


    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_STUDENT"))
    private Student student;

    @ManyToOne
    @JoinColumn(name = "program_id", foreignKey = @ForeignKey(name = "FK_PROGRAM"))
    private Program program;


}
