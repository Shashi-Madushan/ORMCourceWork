package com.shashimadushan.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id

    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Enrolment> enrollments;
}

    // Getters and setters

