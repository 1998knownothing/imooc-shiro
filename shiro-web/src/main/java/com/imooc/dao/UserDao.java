package com.imooc.dao;

import com.imooc.entity.User;

import java.util.List;

public interface UserDao {
    public User getUserByUserName(String userName);

    List<String> getRolesByUsername(String username);

    List<String> getPermissionsByUsername(String username);
}
