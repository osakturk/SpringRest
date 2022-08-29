package com.example.springboot.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "USER_TABLE")
public class User extends BaseObject {
    private String fullName;
    private String gender;
    private String email;
    private String password;
}
