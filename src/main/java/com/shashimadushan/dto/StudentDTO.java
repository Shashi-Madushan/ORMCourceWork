package com.shashimadushan.dto;

import com.shashimadushan.entitys.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;


    private String firstName;


    private String lastName;


    private String email;


    private String phone;


    private Set<Enrolment> enrollments = new HashSet<>();
}
