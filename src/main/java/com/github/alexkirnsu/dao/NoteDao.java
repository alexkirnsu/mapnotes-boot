package com.github.alexkirnsu.dao;

import com.github.alexkirnsu.entity.Note;

import java.util.List;

public interface NoteDao {
    void add(Note note);
    Note getById(int id);
    List<Note> getByUserLogin(String login);
    List<Note> getOpenedAlienForUserLogin(String login);
    void deleteById(int id);
}
