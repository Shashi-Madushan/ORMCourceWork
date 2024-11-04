package com.shashimadushan.dto;

import com.shashimadushan.entitys.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramDTO {


    private String programId;


    private String name;


    private int durationMonths;


    private double fee;


    private List<Enrolment> enrollments;

    public ProgramDTO(String id, String name, String duration, String fee) {
        this.programId = id;
        this.name = name;
        this.durationMonths = Integer.parseInt(duration);
        this.fee = Double.parseDouble(fee);
    }
}
