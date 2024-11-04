package com.shashimadushan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;

    private String userName;
    private String password;
    private String roleName;

    public UserDTO(String usename, String pwd ,String roleName) {
        this.userName = usename;
        this.password = pwd;
        this.roleName = roleName;
    }
    public UserDTO(int id ,String usename, String pwd ,String roleName) {
        this.id = id;
        this.userName = usename;
        this.password = pwd;
        this.roleName = roleName;
    }

}
