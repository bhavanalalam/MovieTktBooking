package com.sat.tmf.movietkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sat.tmf.movietkt.dao.UserDao;
import com.sat.tmf.movietkt.entities.User;
import com.sat.tmf.movietkt.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null)
            throw new RuntimeException("Username already exists!");
        if (userDao.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("Email already exists!");

        
        userDao.save(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User updateUserProfile(String username, User updatedUser) {
        User existing = userDao.findByUsername(username);
        existing.setFullName(updatedUser.getFullName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());

        
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(updatedUser.getPassword());
        }

        userDao.save(existing);
        return existing;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}
