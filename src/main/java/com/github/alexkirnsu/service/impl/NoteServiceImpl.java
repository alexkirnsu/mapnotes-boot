package com.github.alexkirnsu.service.impl;

import com.github.alexkirnsu.dao.*;
import com.github.alexkirnsu.dto.NoteDto;
import com.github.alexkirnsu.entity.*;
import com.github.alexkirnsu.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void add(NoteDto noteDto) {
        noteDao.add(convertToEntity(noteDto));
    }

    private Note convertToEntity(NoteDto noteDto) {
        Comment comment = new Comment();
        Note note = modelMapper.map(noteDto, Note.class);

        note.setUser(userDao.getByLogin(noteDto.getUserLogin()));
        comment.setNote(note);
        saveFirstComment(noteDto, comment);

        return note;
    }

    private void saveFirstComment(NoteDto noteDto, Comment comment) {
        comment.setText(noteDto.getMessage());
        comment.setOwner(noteDto.getUserLogin());
        commentDao.add(comment);
    }

    @Override
    public boolean deleteById(int id, String login) {
        Note note = noteDao.getById(id);
        String ownerLogin = note.getUser().getLogin();
        List<Comment> comments = note.getComments();

        if (noteOwnerIsNotEqualToCurrentUser(ownerLogin, login)) {
            return false;
        }

        if (thereIsNotOwnerCommentInNote(comments, ownerLogin)) {
            return false;
        }

        noteDao.deleteById(id);
        return true;
    }

    private boolean noteOwnerIsNotEqualToCurrentUser(String ownerLogin, String currentUserLogin) {
        return !ownerLogin.equals(currentUserLogin);
    }

    private boolean thereIsNotOwnerCommentInNote(List<Comment> comments, String ownerLogin) {
        return comments.stream().anyMatch(comment -> !comment.getOwner().equals(ownerLogin));
    }

    @Override
    public List<NoteDto> getAvailableByUserLogin(String login) {
        List<NoteDto> noteDTOs = getOwn(login);

        noteDTOs.addAll(getOpenedAlien(login));
        return noteDTOs;
    }

    private List<NoteDto> getOwn(String login) {
        return noteDao.getByUserLogin(login).stream()
                .map(note -> convertToDto(note, false))
                .collect(Collectors.toList());
    }

    private List<NoteDto> getOpenedAlien(String login) {
        return noteDao.getOpenedAlienForUserLogin(login).stream()
                .map(note -> convertToDto(note, true))
                .collect(Collectors.toList());
    }

    private NoteDto convertToDto(Note note, boolean alien) {
        NoteDto noteDto = modelMapper.map(note, NoteDto.class);

        noteDto.setUserLogin(note.getUser().getLogin());
        noteDto.setMessage(extractCommentOfNoteOwner(note).getText());
        noteDto.setCommentNumber(note.getComments().size());
        noteDto.setAlien(alien);
        return noteDto;
    }

    private Comment extractCommentOfNoteOwner(Note note) {
        String noteOwner = note.getUser().getLogin();
        Optional<Comment> commentOwnerComment = note.getComments()
                .stream()
                .filter(comment -> noteOwner.equals(comment.getOwner()))
                .findAny();

        return commentOwnerComment.orElse(new Comment());
    }
}