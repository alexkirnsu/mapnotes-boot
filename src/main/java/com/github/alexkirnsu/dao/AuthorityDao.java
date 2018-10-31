package com.github.alexkirnsu.dao;

import com.github.alexkirnsu.entity.Authority;

import java.util.List;

public interface AuthorityDao {
    void add(Authority authority);
    List<Authority> getByUserLogin(String login);
}
