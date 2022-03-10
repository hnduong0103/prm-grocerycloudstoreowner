package com.example.grocerystoreowner.model.login;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserInformation {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String username;
}