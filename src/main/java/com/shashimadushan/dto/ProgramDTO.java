package com.shashimadushan.dto;

import com.shashimadushan.entitys.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramDTO {
    private Long id;


    private String programId;


    private String name;


    private int durationMonths;


    private double fee;


    private Set<Enrolment> enrollments = new HashSet<>();
}
