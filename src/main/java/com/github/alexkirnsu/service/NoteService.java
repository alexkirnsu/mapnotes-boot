package com.github.alexkirnsu.service;

import com.github.alexkirnsu.dto.NoteDto;

import java.util.List;

public interface NoteService {

    /**
     * Adds {@code Note} object.
     *
     * @param note - {@code Note} object for adding
     */
    void add(NoteDto note);

    /**
     * Returns all available notes for {@code User} by its login.
     *
     * @param login - field of {@code User}
     * @return list of available {@code Note} objects
     */
    List<NoteDto> getAvailableByUserLogin(String login);

    /**
     * Returns true if note with {@code id} has been deleted
     * by {@code User} with {@code login},
     * otherwise return true.
     *
     * @param id    - unique identifier for {@code Note}
     * @param login - field of {@code User}
     * @return true if note has been added, otherwise false
     */
    boolean deleteById(int id, String login);
}