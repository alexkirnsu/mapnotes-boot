package com.github.alexkirnsu.service.impl;

import com.github.alexkirnsu.entity.Authority;
import com.github.alexkirnsu.entity.User;
import com.github.alexkirnsu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userService.getByLogin(login);
        String[] authorities = authorityService.getAll(login).stream()
                .map(Authority::getRole).toArray(String[]::new);

        org.springframework.security.core.userdetails.User.UserBuilder builder;

        if (user != null) {

            builder = org.springframework.security.core.userdetails.User.withUsername(login);

            builder.password(user.getPassword());

            builder.authorities(authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}