package com.github.alexkirnsu.service;

import com.github.alexkirnsu.dto.CommentDto;

import java.util.List;

public interface CommentService {

    /**
     * Adds {@code CommentDto} object.
     *
     * @param comment - {@code CommentDto} object for adding
     */
    void add(CommentDto comment);

    /**
     * Returns {@code CommentDto} object by {@code id}.
     *
     * @param id - unique identifier for {@code Comment}
     * @return {@code CommentDto} object
     */
    CommentDto getById(int id);

    /**
     * Returns list of {@code CommentDto} objects by {@code noteId} and
     * {@code User} login.
     *
     * @param noteId - unique identifier for {@code Note}
     * @param login  - field of {@code User}
     * @return list of {@code CommentDto} objects
     */
    List<CommentDto> getByNoteIdAndUserLogin(int noteId, String login);

    /**
     * Update text field in {@cide CommentDto} with {@code id}.
     *
     * @param id - unique identifier for {@code CommentDto}
     * @param text - new value of {@code CommentDto} text field
     */
    void updateText(int id, String text);

    /**
     * Deletes {@code Comment} object by {@code id}
     *
     * @param id - unique identifier for {@code Comment}
     */
    void deleteById(int id);
}