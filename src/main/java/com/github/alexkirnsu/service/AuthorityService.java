package com.github.alexkirnsu.service;

import com.github.alexkirnsu.entity.Authority;

import java.util.List;

public interface AuthorityService {
    /**
     * Adds {@code Authority} object.
     *
     * @param authority - {@code Autority} object for adding
     */
    void add(Authority authority);

    /**
     * Returns list of {@code Authority} objects
     * for the specified {@code User} with login.
     *
     * @param login - login of {@code User}
     * @return list of {@code Authority} objects
     */
    List<Authority> getAll(String login);
}