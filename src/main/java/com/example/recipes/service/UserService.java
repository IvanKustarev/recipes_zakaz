package com.example.recipes.service;

import com.example.recipes.model.User;

public interface UserService {

    void saveUser(User user);

    User findByUsername(String username);

    User findById(Long id);
}
