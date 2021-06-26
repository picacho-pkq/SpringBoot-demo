package com.pikacho.fileStorage.service;

import com.pikacho.fileStorage.entity.User;

public interface UserService {
    User login(User user);

    void register(User user);
}

