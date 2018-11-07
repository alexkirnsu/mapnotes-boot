package com.github.alexkirnsu.dao.impl;

import com.github.alexkirnsu.controller.CommentController;
import com.github.alexkirnsu.dao.AuthorityDao;
import com.github.alexkirnsu.entity.Authority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

    private static final Logger logger = LogManager.getLogger(AuthorityDaoImpl.class);

    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Authority authority) {
        Session session = entityManager.unwrap(Session.class);

        authority.setRole(ROLE_USER);
        session.save(authority);
        logger.info("Authority with role = " + authority.getRole() +
                " for user with login = " + authority.getUser().getLogin());
    }

    @Override
    public List getByUserLogin(String login) {
        return entityManager
                .createNamedQuery(Authority.QUERY_FIND_AUTHORITY_BY_USER_LOGIN)
                .setParameter(Authority.PARAM_USER_LOGIN, login)
                .getResultList();
    }
}
