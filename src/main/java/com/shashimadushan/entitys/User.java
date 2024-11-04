package com.shashimadushan.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String roleName;



    public User(String userName, String password, String roleName) {
        this.password = password;
        this.userName = userName;
        this.roleName = roleName;
    }
}
