package com.github.alexkirnsu.service.impl;

import com.github.alexkirnsu.dao.*;
import com.github.alexkirnsu.dto.UserDto;
import com.github.alexkirnsu.entity.*;
import com.github.alexkirnsu.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean add(UserDto userDto) {
        if (exists(userDto.getLogin())) {
            return false;
        }

        User user = convertToEntity(userDto);
        userDao.add(user);
        saveAuthority(user);
        return true;
    }

    private boolean exists(String login) {
        return getByLogin(login) != null;
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private void saveAuthority(User user) {
        Authority authority = new Authority();
        authority.setUser(user);
        authorityDao.add(authority);
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }
}