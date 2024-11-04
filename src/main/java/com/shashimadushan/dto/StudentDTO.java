package com.shashimadushan.dto;

import com.shashimadushan.entitys.Enrolment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;

    private List<Enrolment> enrollments;
}
