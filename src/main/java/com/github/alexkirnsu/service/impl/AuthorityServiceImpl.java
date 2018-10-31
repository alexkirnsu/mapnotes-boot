package com.github.alexkirnsu.service.impl;

import com.github.alexkirnsu.dao.AuthorityDao;
import com.github.alexkirnsu.entity.Authority;
import com.github.alexkirnsu.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public void add(Authority authority) {
        authorityDao.add(authority);
    }

    @Override
    public List<Authority> getAll(String login) {
        return authorityDao.getByUserLogin(login);
    }
}