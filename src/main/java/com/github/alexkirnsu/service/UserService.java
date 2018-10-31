package com.github.alexkirnsu.service;

import com.github.alexkirnsu.dto.UserDto;
import com.github.alexkirnsu.entity.User;

public interface UserService {
    /**
     * Returns true if {@code User} has been added
     * otherwise returns false.
     *
     * @param user - {@code User} object with personal data
     * @return true if user has been added, otherwise false
     */
    boolean add(UserDto userDto);

    /**
     * Returns {@code User} object by the login.
     *
     * @param login - field in {@code User}
     * @return {@code User} object
     */
    User getByLogin(String login);
}