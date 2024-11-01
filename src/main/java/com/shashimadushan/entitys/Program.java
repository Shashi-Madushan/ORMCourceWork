package com.shashimadushan.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String programId;


    private String name;


    private int durationMonths;


    private double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Enrolment> enrollments = new HashSet<>();

    // Getters and setters

}
