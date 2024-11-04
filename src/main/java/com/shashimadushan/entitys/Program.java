package com.shashimadushan.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Program {
    @Id
    private String programId;
    private String name;
    private int duration;
    private double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Enrolment> enrollments;

    // Getters and setters

}
