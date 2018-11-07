package com.github.alexkirnsu.service.impl;

import com.github.alexkirnsu.dao.*;
import com.github.alexkirnsu.dto.CommentDto;
import com.github.alexkirnsu.entity.Comment;
import com.github.alexkirnsu.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void add(CommentDto comment) {
        commentDao.add(convertToEntity(comment));
    }

    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setNote(noteDao.getById(commentDto.getNoteId()));
        return comment;
    }

    @Override
    public CommentDto getById(int id) {
        Comment comment = commentDao.getById(id);
        return convertToDto(comment);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        commentDto.setNoteId(comment.getNote().getId());
        return commentDto;
    }

    @Override
    public void updateText(int id, String text) {
        commentDao.updateText(id, text);
    }

    @Override
    public List<CommentDto> getByNoteIdAndUserLogin(int noteId, String login) {
        return getByNoteId(noteId)
                .stream()
                .map(comment -> convertToDto(comment))
                .map(commentDto -> setAlienByOwner(commentDto, login))
                .collect(Collectors.toList());
    }

    private List<Comment> getByNoteId(int noteId) {
        return commentDao.getByNoteId(noteId);
    }

    private CommentDto setAlienByOwner(CommentDto commentDto, String login) {
        if (commentDto.getOwner().equals(login)) {
            commentDto.setAlien(false);
            return commentDto;
        }
        commentDto.setAlien(true);
        return commentDto;
    }

    @Override
    public void deleteById(int id) {
        commentDao.deleteById(id);
    }
}