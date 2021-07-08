package com.example.webclinet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private Integer age;
    private String firstName;
    private String lastName;
    private String gender;
    private String roll;
}
