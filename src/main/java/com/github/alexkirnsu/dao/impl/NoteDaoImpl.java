package com.github.alexkirnsu.dao.impl;

import com.github.alexkirnsu.dao.NoteDao;
import com.github.alexkirnsu.entity.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {

    private static final Logger logger = LogManager.getLogger(NoteDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Note note) {
        Session session = entityManager.unwrap(Session.class);
        session.save(note);
        logger.info("Note with id = " + note.getId() + " has saved.");
    }

    @Override
    public void deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Note note = getById(id);
        session.delete(note);
        logger.info("Note with id = " + note.getId() + " has deleted.");
    }

    @Override
    public Note getById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Note.class, id);
    }

    @Override
    public List getByUserLogin(String login) {
        return entityManager
                .createNamedQuery(Note.QUERY_FIND_NOTE_BY_USER_LOGIN)
                .setParameter(Note.PARAM_USER_LOGIN, login)
                .getResultList();
    }

    @Override
    public List getOpenedAlienForUserLogin(String login) {
        return entityManager
                .createNamedQuery(Note.QUERY_FIND_ALIEN_NOTE_BY_USER_LOGIN)
                .setParameter(Note.PARAM_USER_LOGIN, login)
                .getResultList();
    }
}
