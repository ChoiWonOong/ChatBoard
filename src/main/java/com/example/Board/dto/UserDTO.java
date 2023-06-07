package com.example.Board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private String username;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;
}
