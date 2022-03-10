package com.example.grocerystoreowner.model.brand;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String username;
}
