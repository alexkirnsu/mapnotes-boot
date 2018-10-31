package com.github.alexkirnsu.dao.impl;

import com.github.alexkirnsu.dao.CommentDao;
import com.github.alexkirnsu.entity.Comment;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Comment comment) {
        Session session = entityManager.unwrap(Session.class);
        session.save(comment);
    }

    @Override
    public Comment getById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Comment.class, id);
    }

    @Override
    public void updateText(int id, String text) {
        Session session = entityManager.unwrap(Session.class);
        Comment comment = session.find(Comment.class, id);
        comment.setText(text);
        session.update(comment);
    }

    @Override
    public List getByNoteId(int noteId) {
        return entityManager
                .createNamedQuery(Comment.QUERY_FIND_BY_NOTE_ID)
                .setParameter("id", noteId)
                .getResultList();
    }

    @Override
    public void deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Comment comment = getById(id);
        session.delete(comment);
    }
}
