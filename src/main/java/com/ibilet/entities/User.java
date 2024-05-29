package com.ibilet.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class User {
    public enum Role{
        CLIENT, AIRLINE
    }

    private String id;
    private String username;
    private String password;
    private Role role;
}
