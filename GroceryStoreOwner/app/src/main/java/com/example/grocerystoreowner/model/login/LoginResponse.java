package com.example.grocerystoreowner.model.login;

import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.common.Pagination;

import java.util.List;

import kotlin.text.UStringsKt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class LoginResponse{
    private String token;
    private UserInformation information;
}
