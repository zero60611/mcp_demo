package com.wh.mcp_demo.service;

import com.wh.mcp_demo.entity.User;
import com.wh.mcp_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 標註那些工具是要註冊在MCP Server上，用意之後會透過MCP Client給LLM知道使用
 */
@Service
@RequiredArgsConstructor
public class UserToolService implements UserServiceInterface {
    private final UserRepository userRepository;

    @Override
    @Tool(name = "findUserById", description = "透過ID查找特定使用者")
    public User findById(@ToolParam(description = "被查詢的使用者ID") Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Tool(name = "createUser", description = "新增使用者")
    public User save(@ToolParam(description = "被新增的使用者") User user) {
        return userRepository.save(user);
    }

    @Override
    @Tool(name = "deleteUser", description = "刪除使用者")
    public void deleteById(@ToolParam(description = "被刪除的使用者ID") Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Tool(name = "findAllUser", description = "找全部的員工")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

}
