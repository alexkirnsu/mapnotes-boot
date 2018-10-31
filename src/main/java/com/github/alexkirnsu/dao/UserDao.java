package com.github.alexkirnsu.dao;

import com.github.alexkirnsu.entity.User;

public interface UserDao {
    void add(User user);
    User getByLogin(String login);
}
