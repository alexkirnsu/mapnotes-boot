package com.github.alexkirnsu.dto;

import lombok.Data;

@Data
public class CommentDto {

    private int id;

    private int noteId;

    private String text;

    private String owner;

    private boolean alien;
}