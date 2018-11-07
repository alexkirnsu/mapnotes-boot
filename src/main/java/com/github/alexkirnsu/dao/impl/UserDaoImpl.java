package com.github.alexkirnsu.dao.impl;

import com.github.alexkirnsu.dao.UserDao;
import com.github.alexkirnsu.entity.User;
import com.github.alexkirnsu.entity.User_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(User user) {
        Session session = entityManager.unwrap(Session.class);

        user.setPassword(encodePassword(user.getPassword()));
        session.save(user);
        logger.info("User with login " + user.getLogin() + " has saved.");
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User getByLogin(String login) {
        Session session = entityManager.unwrap(Session.class);

        return session
                .byNaturalId(User.class)
                .using(User_.login.getName(), login)
                .load();
    }
}
