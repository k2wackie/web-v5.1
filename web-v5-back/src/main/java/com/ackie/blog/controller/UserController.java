package com.ackie.blog.controller;

import com.ackie.blog.model.User;
import com.ackie.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/register")
    public User newUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/user")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }
}
