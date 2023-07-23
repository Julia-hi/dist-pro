package com.stpg.distrinet.models;

import lombok.Getter;

import java.util.List;

@Getter
public class UserDTO {
    private final Long id;

    private final String username;

    private final String name;

    private final String email;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
