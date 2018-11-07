package com.github.alexkirnsu.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Profile {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String email;
}