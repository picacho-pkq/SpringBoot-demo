package com.pikacho.fileStorage.service.impl;

import com.pikacho.fileStorage.dao.UserDao;
import com.pikacho.fileStorage.entity.User;
import com.pikacho.fileStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public void register(User user) {
        System.out.println(user);
        userDao.register(user);
    }


}
