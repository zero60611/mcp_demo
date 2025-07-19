package com.wh.mcp_demo.service;

import com.wh.mcp_demo.entity.User;

import java.util.List;

public interface UserServiceInterface {
    User findById(Long id);

    User save(User user);

    void deleteById(Long id);

    List<User> findAllUser();
}
