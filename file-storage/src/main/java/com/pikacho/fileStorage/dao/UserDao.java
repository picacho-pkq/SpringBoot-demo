package com.pikacho.fileStorage.dao;

import com.pikacho.fileStorage.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    public User login(User user);

    public void register(User user);
}
