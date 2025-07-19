package com.wh.mcp_demo.controller;

import com.wh.mcp_demo.entity.User;
import com.wh.mcp_demo.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceInterface userServiceInterface;

    public UserController(@Qualifier("userH2Service") UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userServiceInterface.save(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userServiceInterface.findById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        User existing = userServiceInterface.findById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userServiceInterface.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userServiceInterface.deleteById(id);
    }

}
