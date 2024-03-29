package com.pikacho.fileStorage.dao;

import com.pikacho.fileStorage.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserFileDao {
    // 根据userId查询获取用户的文件列表
    List<UserFile> queryByUserId(Integer id, Integer begin, int offset);

    // 保存文件到数据库
    void save(UserFile userFile);

    UserFile queryByUserFileId(Integer id);

    void update(UserFile userFile);

    void delete(Integer id);

    int queryFileCounts(Integer id);
}
