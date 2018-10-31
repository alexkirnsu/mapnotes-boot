package com.github.alexkirnsu.dao;

import com.github.alexkirnsu.entity.Comment;

import java.util.List;

public interface CommentDao {
    void add(Comment comment);
    Comment getById(int id);
    List<Comment> getByNoteId(int noteId);
    void updateText(int id, String text);
    void deleteById(int id);
}
