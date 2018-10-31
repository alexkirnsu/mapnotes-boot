package com.github.alexkirnsu.dto;

import lombok.Data;

@Data
public class NoteDto {

    private int id;

    private String place;

    private String message;

    private double lat;

    private double lng;

    private String privacy;

    private String userLogin;

    private int commentNumber;

    private boolean alien;
}